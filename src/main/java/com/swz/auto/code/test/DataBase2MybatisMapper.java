package com.swz.auto.code.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.library.config.Configuration;
import org.mybatis.library.config.Mapper;
import org.mybatis.library.config.Mappers;
import org.mybatis.library.config.TypeAlias;
import org.mybatis.library.config.TypeAliases;

import com.swz.auto.code.model.MybatisConfig;

/**
 * 生成Mybatis基本操作xml的类
 * 
 * @author Charsp
 * @version 2015年7月7日下午5:02:06
 */
public class DataBase2MybatisMapper {
	private StringBuffer sbMybatis = new StringBuffer();
	private List<Mapper> mapperlist = new ArrayList<>();
	private List<TypeAlias> typeAlias = new ArrayList<>();

	public void AddMappers(Mapper mapper) {
		mapperlist.add(mapper);
	}

	public void AddTypeAlias(TypeAlias typeAlia) {
		typeAlias.add(typeAlia);
	}

	public TypeAlias createTypeAlias(String tabName) {
		TypeAlias typeAlia = new TypeAlias();
		typeAlia.setAlias(tabName);
		typeAlia.setType(DBUtils.getClassPath() + "." + DBUtils.initCap(tabName));
		return typeAlia;
	}

	public Mapper CreateMapper(String mappername) {
		Mapper mapper = new Mapper();
		mapper.setResource(
				DBUtils.getMybatispath().replace(".", "/") + "/" + DBUtils.getDatabase() + "/" + mappername + ".xml");
		return mapper;
	}

	public StringBuffer getSbMybatis() {
		return sbMybatis;
	}

	public DataBase2MybatisMapper() {

		// 预处理Mybatis
		// sbMybatis = new StringBuffer();
		// // 引用 Mybatis 头部文件
		// sbMybatis.append("<?xml version=\"1.0\"
		// encoding=\"UTF-8\"?>\r\n<!DOCTYPE mapper PUBLIC \""
		// + "-//mybatis.org//DTD Mapper 3.0//EN\"\r\n" + "
		// \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n"
		// // 库文件
		// + "<mapper namespace=\"" + DBUtils.getMybatispath() + "." +
		// DBUtils.getDatabase() + "\">\r\n\r\n");
		// sbMybatis.append("<cache />\r\n");
	}

	public String addThreeRowNote(String note) {
		StringBuffer mybatis = new StringBuffer();
		mybatis.append("\t<!-- ++++++++++++++++++++++++++++");
		int plusLength = note.length() + 8;
		for (int i = 0; i < plusLength; i++)
			mybatis.append("+");
		mybatis.append("++++++++++++++++++++++++++++ -->\r\n");
		mybatis.append("\t<!-- +++++++++++++++++++++++++++ " + note + "数据操作 +++++++++++++++++++++++++++ -->\r\n");
		mybatis.append("\t<!-- ++++++++++++++++++++++++++++");
		for (int i = 0; i < plusLength; i++)
			mybatis.append("+");
		mybatis.append("++++++++++++++++++++++++++++ -->\r\n\r\n");
		return mybatis.toString();
	};

	public String addRowNote(String note) {
		return "\t<!-- +++++++++++++++++++++++++++ " + note + " ++++++++++++++++++++++++++++++++++ -->\r\n";
	};

	public void parseMybatis(String tabName, String[] colNames, String[] colType, List<Integer> primaryKey,
			List<Integer> uniqueKey, Integer auto_increment) {
		StringBuffer mybatis = new StringBuffer();
		mybatis.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE mapper PUBLIC \""
				+ "-//mybatis.org//DTD Mapper 3.0//EN\"\r\n" + " \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n"
				// 库文件
				+ "<mapper namespace=\"" + DBUtils.getMybatispath() + "." + DBUtils.getDatabase() + "." + tabName
				+ "\">\r\n\r\n");
		mybatis.append(addRowNote("二级缓存"));
		mybatis.append("\t<cache />\r\n\r\n");
		mybatis.append(addThreeRowNote(tabName));
		// 类开始tableSql
		mybatis.append(tableSql(tabName, colNames, primaryKey, uniqueKey));
		// 增
		mybatis.append(add(tabName, colNames, colType, auto_increment));
		// 删主键作为索引，没有主键不执行删除操作
		mybatis.append(del(tabName, colNames, primaryKey, uniqueKey));
		// 查
		mybatis.append(get(tabName));
		// 改,基于主键或唯一键识别更新项
		if (primaryKey != null || uniqueKey != null) {
			mybatis.append(update(tabName));
		}
		// 回车换行
		mybatis.append("\r\n");
		mybatis.append("</mapper>");
		// 添加Dao.java 文件(依赖于Mybatis文件)
		new DataBase2JavaDao(tabName, colNames, auto_increment);
		// sbMybatis.append(mybatis);
		createFile(tabName, mybatis);
		// 每创建一个文件，添加一个mapper
		AddMappers(CreateMapper(tabName));
		// 创建一个javaclass说明
		AddTypeAlias(createTypeAlias(tabName));

	}

	private void createFile(String tabName, StringBuffer mybatis) {
		FileWriter fwMybatis;
		try {
			// 目录检测 dir+ main/resources+ mysql+dao
			String myBatisPath = DBUtils.getMavenPath().replace("java", "resources")
					+ DBUtils.getMybatispath().replace(".", File.separator) + File.separator + DBUtils.getDatabase()
					+ File.separator;
			File dirXml = new File(myBatisPath);
			if (!dirXml.exists()) {
				dirXml.mkdirs();
				System.out.println("创建目录" + myBatisPath + "成功！");
			}
			// 文件生成 com.swz.dao
			fwMybatis = new FileWriter(myBatisPath + tabName + ".xml");
			System.out.println("目标文件" + myBatisPath + tabName + ".xml" + "生成！");
			PrintWriter pwMybatis = new PrintWriter(fwMybatis);
			// 打印输出
			pwMybatis.println(mybatis);
			pwMybatis.flush();
			pwMybatis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建配置文件
	 * 
	 * @param typeAlias
	 */
	public void CreateFileConfig() {
		// 添加结束代码
		Mappers mappers = new Mappers();
		mappers.setMapper(mapperlist);
		TypeAliases aliases = new TypeAliases();
		aliases.setTypeAlias(typeAlias);
		Configuration configuration = new Configuration();
		configuration.setMappers(mappers);
		configuration.setTypeAliases(aliases);
		MybatisConfig config = new MybatisConfig();
		config.setConfiguration(configuration);
		FileWriter fwMybatis;
		try {
			// 目录检测 dir+ main/resources+ mysql+dao
			String myBatisPath = DBUtils.getMavenPath().replace("java", "resources")
					+ DBUtils.getMybatispath().replace(".", File.separator) + File.separator;
			File dirXml = new File(myBatisPath);
			if (!dirXml.exists()) {
				dirXml.mkdirs();
				System.out.println("创建目录" + myBatisPath + "成功！");
			}
			// 文件生成 com.swz.dao
			fwMybatis = new FileWriter(myBatisPath + DBUtils.getDatabase() + ".xml");
			System.out.println("目标文件" + myBatisPath + DBUtils.getDatabase() + ".xml" + "生成！");
			PrintWriter pwMybatis = new PrintWriter(fwMybatis);
			// 打印输出
			pwMybatis.println(config.crerateConfig());
			pwMybatis.flush();
			pwMybatis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 预处理的Mybatis的xml
	 * 
	 * @param tabName
	 * @param colNames
	 * @return
	 */
	private String tableSql(String tabName, String[] colNames, List<Integer> primaryKey, List<Integer> uniqueKey) {
		StringBuffer sb = new StringBuffer();
		// 当前所有键值
		List<Integer> tempkey = new ArrayList<Integer>();
		if (!uniqueKey.isEmpty()) {
			tempkey.addAll(uniqueKey);
		}
		// 主键放到最后一位
		if (!primaryKey.isEmpty()) {
			tempkey.addAll(primaryKey);
		}
		// 查询设置
		sb.append("\t<!-- " + tabName + "WhereSql预处理(自动生成代码) -->\r\n");
		// id = tabName+WhereSql
		sb.append("\t<sql id=\"" + tabName + "WhereSql\">\r\n");
		sb.append("\t\tfrom " + tabName + "\r\n");
		sb.append("\t\t<trim prefix=\"where\" prefixOverrides=\"AND |OR \">\r\n");

		// 为避免与关键字重复使用过滤标识Mysql使用两个`,MSSql使用[]
		for (Integer i : tempkey) {
			sb.append("\t\t\t<if test=\"" + colNames[i] + " != null \"> AND `" + colNames[i] + "` = #{" + colNames[i]
					+ "} </if>\r\n");
		}
		// 添加必须选项
		sb.append("\t\t\tAND 1=1\r\n");
		sb.append("\t\t</trim>\r\n");
		sb.append("\t</sql>\r\n");

		// 更新设置
		if (!tempkey.isEmpty()) {
			// id = tabName+SetSql
			sb.append("\t<!-- " + tabName + "SetSql预处理(自动生成代码) -->\r\n");
			sb.append("\t<sql id=\"" + tabName + "SetSql\">\r\n");
			sb.append("\t\t" + tabName + "\r\n");
			sb.append("\t\t<trim prefix=\"set\" suffixOverrides=\",\">\r\n");
			// 使用唯一键作为判断条件，没有唯一键则不生成代码(所有项都可以更新！)
			for (int i = 0; i < colNames.length; i++) {
				sb.append("\t\t\t<if test=\"" + colNames[i] + " != null \"> `" + colNames[i] + "` = #{" + colNames[i]
						+ "}, </if>\r\n");
			}
			sb.append("\t\t</trim>\r\n");
			sb.append("\t\t<trim prefix=\"where\" prefixOverrides=\"AND |OR \">\r\n");
			// 唯一键且不能为NULL才可以作为唯一键的判断
			sb.append("\t\t\t<choose>\r\n");
			// 唯一键是OR的关系，但是在when中，不用表现出
			for (int i = 0; i < uniqueKey.size(); i++)
				sb.append("\t\t\t\t<when test=\"" + colNames[uniqueKey.get(i)] + " != null\">"
						+ colNames[uniqueKey.get(i)] + " = #{" + colNames[uniqueKey.get(i)] + "}</when>\r\n");
			sb.append("\t\t\t\t<otherwise>\r\n");
			// 主键用AND 必须唯一 (联合主键)
			for (int i = 0; i < primaryKey.size(); i++) {
				sb.append("\t\t\t\t\tAND " + colNames[primaryKey.get(i)] + " = #{" + colNames[primaryKey.get(i)]
						+ "}\r\n");
			}
			sb.append("\t\t\t\t</otherwise>\r\n");
			sb.append("\t\t\t</choose>\r\n");
			sb.append("\t\t</trim>\r\n");
			sb.append("\t</sql>\r\n");
		}
		return sb.toString();
	}

	/**
	 * 自动生成 insert语句
	 * 
	 * @param tabName
	 * @param colNames
	 * @return
	 */
	private String add(String tabName, String[] colNames, String[] colType, Integer auto_increment) {
		StringBuffer sb = new StringBuffer();
		sb.append("\t<!-- " + tabName + "预处理(自动生成代码) -->\r\n");
		sb.append("\t<insert id=\"insert_" + tabName + "\" parameterType=\"" + tabName + "\">\r\n");
		// 默认不跳过任何项，在插入的时候
		int keyLocation = -1;
		// 自增量不为null的时候
		if (auto_increment != null) {
			sb.append("\t\t<selectKey keyProperty=\"" + colNames[auto_increment] + "\" resultType=\"java.lang."
					+ colType[auto_increment] + "\">\r\n");
			sb.append("\t\t\tSELECT\r\n");
			sb.append("\t\t\tLAST_INSERT_ID()\r\n");
			sb.append("\t\t</selectKey>\r\n");
			// 跳过id自增量
			keyLocation = auto_increment;
		}
		sb.append("\t\tinsert into " + tabName + "\r\n");
		String values = "";

		// 插入一条记录，为避免与关键字重复使用过滤标识Mysql使用两个`,MSSql使用[]
		for (int j = 0; j < colNames.length; j++) {
			if (j != keyLocation)
				values += "`" + colNames[j] + "`,";
		}
		sb.append("\t\t(" + values.substring(0, values.length() - 1) + ")\r\n");
		sb.append("\t\tvalues\r\n");
		values = "";
		for (int j = 0; j < colNames.length; j++) {
			if (j != keyLocation)
				values += "#{" + colNames[j] + "},";
		}
		sb.append("\t\t(" + values.substring(0, values.length() - 1) + ")\r\n");
		sb.append("\t</insert>\r\n");
		return sb.toString();
	}

	private String del(String tabName, String[] colNames, List<Integer> primaryKey, List<Integer> uniqueKey) {
		StringBuffer sb = new StringBuffer();
		if (!primaryKey.isEmpty() || !uniqueKey.isEmpty()) {
			sb.append("\t<!-- 删除" + tabName + "行  -->\r\n");
			sb.append("\t<delete id=\"delete_" + tabName + "\" parameterType=\"" + tabName + "\" >\r\n");
			sb.append("\t\tdelete from\r\n\t\t" + tabName + "\r\n");
			// 主键，唯一键都可删除 主键也必须唯一
			sb.append("\t\t<trim prefix=\"where\" prefixOverrides=\"AND |OR \">\r\n");
			// 唯一键且不能为NULL才可以作为唯一键的判断
			sb.append("\t\t\t<choose>\r\n");
			// 唯一键是OR的关系，但是在when中，不用表现出
			for (int i = 0; i < uniqueKey.size(); i++) {
				sb.append("\t\t\t\t<when test=\"" + colNames[uniqueKey.get(i)] + " != null\">"
						+ colNames[uniqueKey.get(i)] + " = #{" + colNames[uniqueKey.get(i)] + "}</when>\r\n");
			}
			// 主键用AND 必须唯一 (联合主键)
			String whenStr = "";
			String ifStr = "";
			for (int i = 0; i < primaryKey.size(); i++) {
				if (i == 0) {
					sb.append("\t\t\t\t<when test=\"");
				}
				whenStr += colNames[primaryKey.get(i)] + " !=null or ";
				ifStr += "\t\t\t\t\t<if test=\"" + colNames[primaryKey.get(i)] + " !=null\">AND "
						+ colNames[primaryKey.get(i)] + " = #{" + colNames[primaryKey.get(i)] + "}</if>\r\n";
				if (i == primaryKey.size() - 1) {
					whenStr = whenStr.substring(0, whenStr.length() - 4) + "\">\r\n";
					sb.append(whenStr);
					sb.append(ifStr);
					sb.append("\t\t\t\t</when>\r\n");
				}
			}
			sb.append("\t\t\t\t<otherwise>\r\n");
			sb.append("\t\t\t\t\t1=2\r\n");
			sb.append("\t\t\t\t</otherwise>\r\n");
			sb.append("\t\t\t</choose>\r\n");
			sb.append("\t\t</trim>\r\n");
			sb.append("\t</delete>\r\n");
		}
		return sb.toString();
	}

	private String update(String tabName) {
		StringBuffer sb = new StringBuffer();
		sb.append("\t<!-- 更新" + tabName + "行  -->\r\n");
		sb.append("\t<update id=\"update_" + tabName + "\" parameterType=\"" + tabName + "\" >\r\n");
		sb.append("\t\tupdate\r\n");
		sb.append("\t\t<include refid=\"" + tabName + "SetSql\" />\r\n");
		sb.append("\t</update>\r\n");
		return sb.toString();
	}

	/**
	 * 查询数据
	 * 
	 * @param tabName
	 * @return
	 */
	private String get(String tabName) {
		StringBuffer sb = new StringBuffer();
		sb.append("\t<!-- 选择" + tabName + "by id(无id则返回所有) -->\r\n");
		sb.append("\t<select id=\"select_" + tabName + "\" parameterType=\"" + tabName + "\" resultType=\"" + tabName
				+ "\">\r\n");
		sb.append("\t\tselect *\r\n");
		sb.append("\t\t<include refid=\"" + tabName + "WhereSql\" />\r\n");
		sb.append("\t</select>\r\n");
		return sb.toString();
	}
}
