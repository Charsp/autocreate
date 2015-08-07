package com.swz.auto.code.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 生成JavaDao的类
 * 
 * @author Charsp
 * @version 2015年7月7日下午5:01:40
 */
public class DataBase2JavaDao {
	private static boolean createBaseFile = false;
	private String namespace = DBUtils.getMybatispath() + "." + DBUtils.getDatabase() + ".";

	private void createBaseSqlFile(String myDaoPath, FileWriter fwMybatis) {
		if (!createBaseFile) {
			// 生成基础模板类
			String BaseSqlClass = "package " + DBUtils.getDaopath() + ";\r\n\r\n"
					+ "import org.mybatis.spring.SqlSessionTemplate;\r\n"
					+ "import org.springframework.beans.factory.annotation.Autowired;\r\n " + "\r\n"
					+ "public class BaseSqlDao {\r\n " + "\tprotected SqlSessionTemplate sqlTemplate;\r\n" + "\r\n"
					+ "\t@Autowired\r\n" + "\tpublic void setSqlTemplate(SqlSessionTemplate sqlTemplate) {\r\n"
					+ "\t\tthis.sqlTemplate = sqlTemplate;\r\n" + "\t}\r\n" + "}";
			// 生成Java文件
			// 判断dao层数据文件夹存在与否 com.swz.wechat.dao
			File dirXml = new File(myDaoPath);
			if (!dirXml.exists()) {
				dirXml.mkdirs();
				System.out.println("创建目录" + myDaoPath + "成功！");
			}
			try {
				fwMybatis = new FileWriter(myDaoPath + "BaseSqlDao.java");
				System.out.println("目标文件" + myDaoPath + "BaseSqlDao.java" + "生成！");
				PrintWriter pwMybatis = new PrintWriter(fwMybatis);
				pwMybatis.println(BaseSqlClass);
				pwMybatis.flush();
				pwMybatis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.err.println("创建BaseSqlDao.java完成！！！！！");
		}
		createBaseFile = true;
	}

	public DataBase2JavaDao(String tabName, String[] colNames, Integer auto_increment) {
		String myDaoPath = DBUtils.getMavenPath() + DBUtils.getDaopath().replace(".", File.separator) + File.separator;
		FileWriter fwMybatis = null;
		createBaseSqlFile(myDaoPath, fwMybatis);
		// model + xml + dao 代码层汇总
		StringBuffer stringBuffer = new StringBuffer();
		// page包信息 package
		stringBuffer.append("package " + DBUtils.getDaopath() + ";\r\n\r\n");
		// import导入信息
		stringBuffer.append("import java.util.List;\r\n\r\n");
		stringBuffer.append("import org.springframework.stereotype.Repository;\r\n\r\n");
		stringBuffer.append("import " + DBUtils.getClassPath() + "." + DBUtils.initCap(tabName) + ";\r\n\r\n\r\n");
		stringBuffer.append("/**\r\n");
		stringBuffer.append(" * 数据库" + DBUtils.getDatabase() + "的表" + DBUtils.initCap(tabName) + "的Dao（自动生成）\r\n");
		stringBuffer.append(" * \r\n");
		stringBuffer.append(" * @author Charsp\r\n");
		stringBuffer.append(" * @version 2015-08-03 16:05:12\r\n");
		stringBuffer.append(" */\r\n\r\n");
		// class头信息@Repository
		stringBuffer.append("@Repository\r\n");
		stringBuffer.append("public class " + DBUtils.initCap(tabName) + "Dao extends BaseSqlDao {\r\n");
		// 查询函数
		stringBuffer.append(get(tabName));
		// 保存函数
		stringBuffer.append(save(tabName, colNames, auto_increment));
		// 强制保存，即使带id也要保存
		// 强制更新，即使没有此项
		stringBuffer.append(saveOne(tabName));
		stringBuffer.append(saveList(tabName));
		stringBuffer.append(updateOne(tabName));
		stringBuffer.append(updateList(tabName));
		// 删除函数
		stringBuffer.append(delete(tabName, auto_increment));
		// 文件结束
		stringBuffer.append("}");

		try {
			fwMybatis = new FileWriter(myDaoPath + DBUtils.initCap(tabName) + "Dao.java");
			System.out.println("目标文件" + myDaoPath + DBUtils.initCap(tabName) + "Dao.java" + "生成！");
			PrintWriter pwMybatis = new PrintWriter(fwMybatis);
			pwMybatis.println(stringBuffer);
			pwMybatis.flush();
			pwMybatis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 又是保存，查，删除数据项
	private String save(String tabName, String[] colNames, Integer auto_increment) {
		StringBuffer sb = new StringBuffer();
		sb.append("\t/** 保存表" + tabName + "数据 **/\r\n");
		sb.append("\tpublic " + DBUtils.initCap(tabName) + " save" + DBUtils.initCap(tabName) + "("
				+ DBUtils.initCap(tabName) + " " + tabName + ") {\r\n");
		sb.append("\t\t// 操作改变的行数\r\n");

		if (auto_increment == null) {
			sb.append("\t\t// 强制插入\r\n");
			sb.append("\t\ttry {\r\n");
			sb.append("\t\t\tsqlTemplate.insert(\"" + namespace + tabName + ".insert_" + tabName + "\"," + tabName
					+ ");\r\n");
			sb.append("\t\t} catch (Exception e) {\r\n");
			sb.append("\t\t\t// 插入异常\r\n");
			sb.append("\t\t\tSystem.out.println(\"插入数据异常insert_" + tabName + "，尝试更新数据\");\r\n");
			sb.append("\t\t\t// 更新数据\r\n");
			sb.append("\t\t\tsqlTemplate.update(\"" + namespace + tabName + ".update_" + tabName + "\"," + tabName
					+ ");\r\n");
			sb.append("\t\t\t// 返回插入的数据\r\n");
			sb.append("\t\t\t" + tabName + " = get" + DBUtils.initCap(tabName) + "(" + tabName + ");\r\n");
			sb.append("\t}\r\n");
		}
		// 有主键的操作，首先要判断主键为空，有主键一定是更新数据
		else {
			sb.append("\t\tboolean except = false;\r\n\r\n");
			sb.append("\t\t// 判断主键id是否存在\r\n");
			sb.append("\t\tif (" + tabName + ".get" + DBUtils.initCap(colNames[auto_increment]) + "() == null) {\r\n");
			sb.append("\t\t\t// 直接插入\r\n");
			sb.append("\t\t\ttry {\r\n");
			sb.append("\t\t\t\tsqlTemplate.insert(\"" + namespace + tabName + ".insert_" + tabName + "\"," + tabName
					+ ");\r\n");
			sb.append("\t\t\t} catch (Exception e) {\r\n");
			sb.append("\t\t\t\t// 插入异常\r\n");
			sb.append("\t\t\tSystem.out.println(\"插入数据异常insert_" + tabName + "，尝试更新数据\");\r\n");
			sb.append("\t\t\t\texcept = true;\r\n");
			sb.append("\t\t\t}\r\n");
			sb.append("\t\t} else {\r\n");
			sb.append("\t\t\t// 直接更新\r\n");
			sb.append("\t\t\texcept = true;\r\n");
			sb.append("\t\t}\r\n");
			sb.append("\t\t// 更新数据\r\n");
			sb.append("\t\tif (except) {\r\n");
			sb.append("\t\t\tsqlTemplate.update(\"" + namespace + tabName + ".update_" + tabName + "\"," + tabName
					+ ");\r\n");
			sb.append("\t\t\t// 返回插入的数据\r\n");
			sb.append("\t\t\t" + tabName + " = get" + DBUtils.initCap(tabName) + "(" + tabName + ");\r\n");
			sb.append("\t\t}\r\n");
		}
		sb.append("\t\treturn " + tabName + ";\r\n");
		sb.append("\t}\r\n");
		return sb.toString();
	}

	/**
	 * save one row
	 * 
	 * @param tabName
	 * @param colNames
	 * @param auto_increment
	 * @return
	 */
	private String saveOne(String tabName) {
		String insertHeader = namespace + tabName + ".insert_" + tabName;
		StringBuffer sb = new StringBuffer();
		sb.append("\t/** saveOne表activeshare数据 **/\r\n");
		sb.append("\tpublic " + DBUtils.initCap(tabName) + " saveOne" + DBUtils.initCap(tabName) + "("
				+ DBUtils.initCap(tabName) + " " + tabName + ") {\r\n");
		sb.append("\t\ttry {\r\n");
		sb.append("\t\t\tsqlTemplate.insert(\"" + insertHeader + "\", " + tabName + ");\r\n");
		sb.append("\t\t} catch (Exception e) {\r\n");
		sb.append("\t\t\t// 插入异常\r\n");
		sb.append("\t\t\t" + tabName + " = null;\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\t// Activeshare\r\n");
		sb.append("\t\treturn " + tabName + ";\r\n");
		sb.append("\t}\r\n");
		return sb.toString();
	}

	private String updateOne(String tabName) {
		String updateHeader = namespace + tabName + ".update_" + tabName;
		StringBuffer sb = new StringBuffer();
		sb.append("\t/** updateOne表activeshare数据 **/\r\n");
		sb.append("\tpublic " + DBUtils.initCap(tabName) + " updateOne" + DBUtils.initCap(tabName) + "("
				+ DBUtils.initCap(tabName) + " " + tabName + ") {\r\n");
		sb.append("\t\tsqlTemplate.update(\"" + updateHeader + "\", " + tabName + ");\r\n");
		sb.append("\t\t" + tabName + " = get" + DBUtils.initCap(tabName) + "(" + tabName + ");\r\n");
		sb.append("\t\treturn " + tabName + ";\r\n");
		sb.append("\t}\r\n");
		return sb.toString();
	}

	/**
	 * save one row
	 * 
	 * @param tabName
	 * @param colNames
	 * @param auto_increment
	 * @return
	 */
	private String saveList(String tabName) {
		String insert = "save";
		StringBuffer sb = new StringBuffer();
		sb.append("\t/** " + insert + "All表activeshare数据 **/\r\n");
		sb.append("\tpublic List<" + DBUtils.initCap(tabName) + "> " + insert + "All" + DBUtils.initCap(tabName)
				+ "(List<" + DBUtils.initCap(tabName) + "> " + tabName + "s) {\r\n");
		sb.append("\t\tfor (int i = 0; i < " + tabName + "s.size(); i++) {\r\n");
		sb.append("\t\t\t" + DBUtils.initCap(tabName) + " " + tabName + " = " + tabName + "s.get(i);\r\n");
		sb.append("\t\t\t" + tabName + " = " + insert + "One" + DBUtils.initCap(tabName) + "(" + tabName + ");\r\n");
		sb.append("\t\t\tif (" + tabName + " == null) {\r\n");
		sb.append("\t\t\t\treturn null;\r\n");
		sb.append("\t\t\t}\r\n");
		sb.append("\t\t\t" + tabName + "s.set(i, " + tabName + ");\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\treturn " + tabName + "s;\r\n");
		sb.append("\t}\r\n");
		return sb.toString();
	}

	private String updateList(String tabName) {
		String update = "update";
		StringBuffer sb = new StringBuffer();
		sb.append("\t/** " + update + "All表activeshare数据 **/\r\n");
		sb.append("\tpublic List<" + DBUtils.initCap(tabName) + "> " + update + "All" + DBUtils.initCap(tabName)
				+ "(List<" + DBUtils.initCap(tabName) + "> " + tabName + "s) {\r\n");
		sb.append("\t\tfor (int i = 0; i < " + tabName + "s.size(); i++) {\r\n");
		sb.append("\t\t\t" + DBUtils.initCap(tabName) + " " + tabName + " = " + tabName + "s.get(i);\r\n");
		sb.append("\t\t\t" + tabName + " = " + update + "One" + DBUtils.initCap(tabName) + "(" + tabName + ");\r\n");
		sb.append("\t\t\tif (" + tabName + " == null) {\r\n");
		sb.append("\t\t\t\treturn null;\r\n");
		sb.append("\t\t\t}\r\n");
		sb.append("\t\t\t" + tabName + "s.set(i, " + tabName + ");\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\treturn " + tabName + "s;\r\n");
		sb.append("\t}\r\n");
		return sb.toString();
	}

	// 查询数据（like 查询得手动修改）
	private String get(String tabName) {
		StringBuffer sb = new StringBuffer();
		sb.append("\t/** 获取表" + tabName + "数据 **/\r\n");
		sb.append("\tpublic " + DBUtils.initCap(tabName) + " get" + DBUtils.initCap(tabName) + "("
				+ DBUtils.initCap(tabName) + " " + tabName + ") {\r\n");
		sb.append("\t\treturn (" + DBUtils.initCap(tabName) + ")sqlTemplate.selectOne(\"" + namespace + tabName
				+ ".select_" + tabName + "\"," + tabName + ");\r\n");
		sb.append("\t}\r\n");
		sb.append("\t/** 获取表" + tabName + "All数据 **/\r\n");
		sb.append("\tpublic List<" + DBUtils.initCap(tabName) + "> get" + DBUtils.initCap(tabName) + "All("
				+ DBUtils.initCap(tabName) + " " + tabName + ") {\r\n");
		sb.append("\t\treturn sqlTemplate.selectList(\"" + namespace + tabName + ".select_" + tabName + "\"," + tabName
				+ ");\r\n");
		sb.append("\t}\r\n");
		return sb.toString();
	}

	// 删除数据
	private String delete(String tabName, Integer auto_increment) {
		StringBuffer sb = new StringBuffer();
		if (auto_increment != null) {
			sb.append("\t/** 删除表" + tabName + "数据（提供主键索引） **/\r\n");
			sb.append("\tpublic int delete" + DBUtils.initCap(tabName) + "(" + DBUtils.initCap(tabName) + " " + tabName
					+ ") {\r\n");
			sb.append("\t\treturn sqlTemplate.delete(\"" + namespace + tabName + ".delete_" + tabName + "\"," + tabName
					+ ");\r\n");
			sb.append("\t}\r\n");
		}
		return sb.toString();
	}
}
