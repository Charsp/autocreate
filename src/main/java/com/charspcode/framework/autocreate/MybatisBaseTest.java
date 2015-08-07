package com.charspcode.framework.autocreate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.charsboll.data.model.Active;
import com.common.mybatis.page.PageParameter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MybatisBaseTest {
	private static SqlSessionFactory factory;

	public static SqlSessionFactory getFactory() {
		if (factory == null) {
			InputStream inputStream;
			try {
				inputStream = Resources.getResourceAsStream("mysql/dao/WeiXinApp.xml");
				SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
				factory = builder.build(inputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return factory;
	}

	/**
	 * 基础缓存(二级)机制 测试
	 */
	public void querySessionTest() {
		factory = getFactory();
		SqlSession sqlSession = factory.openSession(true);
		SqlSession sqlSession2 = factory.openSession(true);
		Map<String, Object> map = new HashMap<>();
		PageParameter pageParameter = new PageParameter(1, 2);
		pageParameter.setCurrentPage(2);
		pageParameter.setPageSize(3);
		map.put("page", pageParameter);
		map.put("active", new Active());
		List<Active> actives = sqlSession.selectList("mysql.dao.WeiXinApp.active.select_active", map);
		System.out.println("第1项查询出数据总数为" + actives.size() + "项");
		actives = sqlSession2.selectList("mysql.dao.WeiXinApp.active.select_active", map);
		System.out.println("第2项查询出数据总数为" + actives.size() + "项");
		Active active = new Active();
		active.setActived(false);
		active.setChance(0.12);
		active.setMaxaward(100);
		active.setAddress("AAAA");
		active.setDepartment("BBBB");
		System.out.println("第1项插入数据一条：返回" + sqlSession.insert("mysql.dao.WeiXinApp.active.insert_active", active));
		actives = sqlSession.selectList("mysql.dao.WeiXinApp.active.select_active", map);
		System.out.println("第1项查询出数据总数为" + actives.size() + "项");
		actives = sqlSession2.selectList("mysql.dao.WeiXinApp.active.select_active", map);
		System.out.println("第2项查询出数据总数为" + actives.size() + "项");
		// System.err.println("!!!!!!!delete All!!!!!!!!");
		// sqlSession.delete("mysql.dao.WeiXinApp.active.delete_active",
		// active);
		System.out.println("MyBatis的缓存机制：");
		System.out.println("\t同一session可以保证缓存被插入，更新等操作打破，不同session间的这些操作互不影响");
		System.out.println("\tspring在单例模式下，可以保证数据的准确性");
		System.out.println("\t当然还得在同一mapper下，请看下面");
		sqlSession.close();
	}

	/**
	 * 返回map查看数据格式，多表返回格式
	 */
	public void returnMapTest() {
		factory = getFactory();
		SqlSession sqlSession = factory.openSession(true);
		Active active = new Active();
		active.setId(35);
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("active", active);
		returnMap = sqlSession.selectOne("mysql.dao.WeiXinApp.active.select_active", returnMap);
		System.out.println(returnMap);
		System.out.println(JSONObject.fromObject(returnMap));
		List<Map<String, Object>> returnMapList = sqlSession.selectList("mysql.dao.WeiXinApp.active.select_active");
		System.out.println(returnMapList);
		System.out.println(JSONArray.fromObject(returnMapList));
		// 插入一行查看map影响
		sqlSession.insert("mysql.dao.WeiXinApp.active.insert_active", returnMap);
		System.out.println(returnMap);
		System.out.println(JSONObject.fromObject(returnMap));
		// 测试分页代码
		PageParameter pageParameter = new PageParameter();
		pageParameter.setCurrentPage(1);
		pageParameter.setPageSize(3);
		returnMap.clear();
		returnMap.put("page", pageParameter);// 添加分页信息
		returnMapList = sqlSession.selectList("mysql.dao.WeiXinApp.active.select_active", returnMap);
		System.out.println(returnMapList);
		System.out.println(JSONArray.fromObject(returnMapList));
		pageParameter.setCurrentPage(2);
		returnMap.clear();
		returnMap.put("page", pageParameter);// 添加分页信息
		returnMapList = sqlSession.selectList("mysql.dao.WeiXinApp.active.select_active", returnMap);
		System.out.println(returnMapList);
		System.out.println(JSONArray.fromObject(returnMapList));
		returnMapList = sqlSession.selectList("mysql.dao.WeiXinApp.active.select_active", returnMap);
		System.out.println(returnMapList);
		System.out.println(JSONArray.fromObject(returnMapList)); 
		pageParameter.setCurrentPage(1);
		returnMapList = sqlSession.selectList("mysql.dao.WeiXinApp.active.select_active", returnMap);
		System.out.println(returnMapList);
		System.out.println(JSONArray.fromObject(returnMapList));

	}
}
