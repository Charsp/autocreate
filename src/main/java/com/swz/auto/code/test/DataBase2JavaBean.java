package com.swz.auto.code.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mybatis.library.config.TypeAlias;
import org.mybatis.library.mapper.TableInfoModel;

/**
 * 生成JavaBean的类
 * 
 * @author Charsp
 * @version 2015年7月7日下午5:01:07
 */
public class DataBase2JavaBean {
	private String[] colNames;// 列名数组
	private String[] colType;// 列名类型数组
	private String[] colComm;// 列名描述数组
	private Integer auto_increment; // 自增量信息
	private List<Integer> primaryKeys;// 主键的列号
	private List<Integer> uniqueKey; // 唯一键的列号
	private List<TableInfoModel> tableInfoModels;
	// private int[] col Size;// 列名大小数组
	private boolean f_util = false;// 是否需要导入java.util.*
	private boolean f_sql = false;// 是否需要导入java.sql.*
	private String args;
	private static DataBase2MybatisMapper dataBase2Mybatis;
	private String headerDoc = "";
	private static List<TypeAlias> typeAlias = new ArrayList<>();

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	public DataBase2JavaBean() {
	}

	/**
	 * 创建JavaBean
	 * 
	 * @param tabName
	 * @param colNames
	 * @param colType
	 */
	private void DataTransaction(String tabName, String[] colNames, String[] colType) {
		String contentJava = parseJava(tabName, colNames);
		// 添加mybatis数据
		dataBase2Mybatis.parseMybatis(tabName, colNames, colType, primaryKeys, uniqueKey, auto_increment);
		// 目录检测
		File dirJava = new File(
				DBUtils.getMavenPath() + DBUtils.getClassPath().replace(".", File.separator) + File.separator);
		if (!dirJava.exists()) {
			dirJava.mkdirs();
			System.out.println("创建目录" + DBUtils.getMavenPath() + DBUtils.getClassPath().replace(".", File.separator)
					+ File.separator + "成功！");
		}
		// 文件生成
		FileWriter fwJava;
		try {
			fwJava = new FileWriter(DBUtils.getMavenPath() + DBUtils.getClassPath().replace(".", File.separator)
					+ File.separator + DBUtils.initCap(tabName) + ".java");
			System.out.println("目标文件" + DBUtils.getMavenPath() + DBUtils.getClassPath().replace(".", File.separator)
					+ File.separator + DBUtils.initCap(tabName) + ".java" + "生成！");
			PrintWriter pwJava = new PrintWriter(fwJava);
			pwJava.println(contentJava);
			pwJava.flush();
			pwJava.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得表结构信息
	 * 
	 * @param tabName
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private void GetMysqlTabelInfo(String tabName) {
		Connection conn = null;
		String sql = "select * from " + tabName;
		try {
			conn = DBUtils.getConnection();
			PreparedStatement prep = conn.prepareStatement(sql);
			ResultSetMetaData rsmd = prep.getMetaData();
			int size = rsmd.getColumnCount();// 共有多少列
			colNames = new String[size];
			colType = new String[size];
			// colSize = new int[size];
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				colNames[i] = rsmd.getColumnName(i + 1);
				colType[i] = rsmd.getColumnTypeName(i + 1);
				// datatime使用String替换了，不需要；text也是
				// if (colType[i].equalsIgnoreCase("datetime")
				// || colType[i].equalsIgnoreCase("date")) {
				// f_util = true;
				// }
				// colType[i].equalsIgnoreCase("text")
				// ||
				if (colType[i].equalsIgnoreCase("image")) {
					f_sql = true;
				}
				// colSize[i] = rsmd.getColumnDisplaySize(i + 1);
			}
			// 添加mybatis数据
			DataTransaction(tabName, colNames, colType);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn);
		}
	}

	private void GetMysqlTabelInfo2(String tabName) {
		Connection conn = null;
		String sql = "show full columns from " + tabName;
		try {
			conn = DBUtils.getConnection();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			resultSet.last();
			int size = resultSet.getRow(); // 共有多少列
			colNames = new String[size];
			colComm = new String[size];
			colType = new String[size];
			resultSet.first();
			int i = 0;
			// 清除主键的位置信息
			auto_increment = null;
			// 每一张新表 N行数据信息
			tableInfoModels = new ArrayList<TableInfoModel>();
			uniqueKey = new ArrayList<Integer>();
			// 清除唯一键的位置信息
			primaryKeys = new ArrayList<Integer>();
			do {
				TableInfoModel tableInfoModel = new TableInfoModel();
				tableInfoModel.setCollation(resultSet.getString("Collation"));
				tableInfoModel.setComment(resultSet.getString("Comment"));
				tableInfoModel.setDefault(resultSet.getString("Default"));
				tableInfoModel.setExtra(resultSet.getString("Extra"));
				tableInfoModel.setField(resultSet.getString("Field"));
				tableInfoModel.setKey(resultSet.getString("Key"));
				tableInfoModel.setNull(resultSet.getString("Null"));
				tableInfoModel.setPrivileges(resultSet.getString("Privileges"));
				tableInfoModel.setType(resultSet.getString("Type"));
				tableInfoModels.add(tableInfoModel);
				// 列名称
				colNames[i] = resultSet.getString("Field");
				// 存储类型
				int typeLocal = resultSet.getString("Type").indexOf("(");
				// 获得描述
				String comment = resultSet.getString("Comment");
				// 除去（11）
				if (typeLocal > 0) {
					colType[i] = resultSet.getString("Type").substring(0, typeLocal);
				} else {
					colType[i] = resultSet.getString("Type");
				}
				if (comment.length() < 1)
					colComm[i] = null;
				else {
					colComm[i] = comment;
				}
				// 获得唯一键,不能为Null且唯一
				String key = resultSet.getString("Key");
				if (key.equalsIgnoreCase("UNI") && resultSet.getString("Null").equalsIgnoreCase("NO")) {
					uniqueKey.add(i);
				}
				// 获得主键
				if (key.equalsIgnoreCase("PRI")) {
					primaryKeys.add(i);
				}
				// 获得自增量
				String increment = resultSet.getString("Extra");
				if (increment.equalsIgnoreCase("auto_increment")) {
					auto_increment = i;
				}
				// System.err.println("输出内容为 " + colNames[i] + "-" +
				// colComm[i]
				// + "-" + colType[i]);
				if (colType[i].equalsIgnoreCase("datetime") || colType[i].equalsIgnoreCase("date")) {
					// f_util = true;
				}
				if (colType[i].equalsIgnoreCase("text") || colType[i].equalsIgnoreCase("image")) {
					// f_sql = true;
				}
				i++;
			} while (resultSet.next());

			// 不执行数据，只输出查看一下
			DataTransaction(tabName, colNames, colType);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn);
		}
	}

	public DataBase2JavaBean(String tabName) {
		// 原始获取表信息方式
		// GetMysqlTabelInfo(tabName);
		// MYSQL特殊获取表信息方式
		GetMysqlTabelInfo2(tabName);
	}

	/**
	 * 解析处理，生成java实体类主体代码
	 * 
	 * @param tabName
	 * @param colNames
	 * @param colType
	 * @param colSize
	 * @return
	 */
	private String parseJava(String tabName, String[] colNames) {
		StringBuffer sb = new StringBuffer();
		// 所在包名
		sb.append("package " + DBUtils.getClassPath() + ";\r\n\r\n");
		TypeAlias typealia = new TypeAlias();
		typealia.setAlias(tabName);
		typealia.setType(DBUtils.getClassPath() + "." + DBUtils.initCap(tabName));
		typeAlias.add(typealia);
		// 引用包
		if (f_util) {
			sb.append("import java.util.Date;\r\n\r\n");
		}
		if (f_sql) {
			sb.append("import java.sql.*;\r\n\r\n");
		}
		sb.append("import java.io.Serializable;\r\n\r\n");
		if (headerDoc != null) {
			// 默认值为Charsp
			sb.append("/**\r\n");
			sb.append(" * 数据库 " + tabName + " 的model（自动生成）\r\n");
			sb.append(" * \r\n");
			sb.append(" * @author Charsp\r\n");
			sb.append(" * @version " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\r\n");
			sb.append(" */\r\n");
		}
		// 类开始
		sb.append("@SuppressWarnings(\"serial\")\r\n");
		sb.append("public class " + DBUtils.initCap(tabName) + " implements Serializable {\r\n");
		processAllAttrs(sb);
		sb.append("\r\n");
		processAllMethod(sb);
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 生成所有的方法
	 * 
	 * @param sb
	 */
	private void processAllMethod(StringBuffer sb) {
		for (int i = 0; i < colNames.length; i++) {
			if (colComm[i] != null) {
				sb.append("\t/**\r\n");
				sb.append("\t * 设置" + colComm[i] + "的值\r\n");
				sb.append("\t */\r\n");
			}
			sb.append("\tpublic void set" + DBUtils.initCap(colNames[i]) + "(" + sqlType2JavaType(colType[i]) + " "
					+ colNames[i] + ") {\r\n");
			sb.append("\t\tthis." + colNames[i] + " = " + colNames[i] + ";\r\n");
			sb.append("\t}\r\n\r\n");

			if (colComm[i] != null) {
				sb.append("\t/**\r\n");
				sb.append("\t * 获取" + colComm[i] + "的值\r\n");
				sb.append("\t */\r\n");
			}
			sb.append("\tpublic " + sqlType2JavaType(colType[i]) + " get" + DBUtils.initCap(colNames[i]) + "() {\r\n");
			sb.append("\t\treturn " + colNames[i] + ";\r\n");
			sb.append("\t}\r\n\r\n");
		}
	}

	/*
	 * 解析输出属性
	 * 
	 * @return
	 */
	private void processAllAttrs(StringBuffer sb) {
		for (int i = 0; i < colNames.length; i++) {
			if (colComm[i] != null) {
				sb.append("\t/** " + colComm[i] + " **/\r\n");
			}
			String type = sqlType2JavaType(colType[i]);
			sb.append("\tprivate " + type + " " + colNames[i] + ";\r\n");
			// 修改当前数据类型
			colType[i] = type;
		}
	}

	private String sqlType2JavaType(String sqlType) {
		if (sqlType.equalsIgnoreCase("bit")) {
			// return "boolean";
			return "Boolean";
		} else if (sqlType.equalsIgnoreCase("tinyint") || sqlType.equalsIgnoreCase("TINYINT UNSIGNED")) {
			// return "byte";
			return "Byte";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			// return "short";
			return "Short";
		} else if (sqlType.equalsIgnoreCase("bigint") || sqlType.equalsIgnoreCase("BIGINT UNSIGNED")
				|| sqlType.equalsIgnoreCase("TIMESTAMP")) {
			// return "long";
			return "Long";
		} else if (sqlType.equalsIgnoreCase("INTEGER") || sqlType.equalsIgnoreCase("INTEGER UNSIGNED")
				|| sqlType.equalsIgnoreCase("INT") || sqlType.equalsIgnoreCase("INT UNSIGNED")) {
			// return "int";
			return "Integer";
		} else if (sqlType.equalsIgnoreCase("float")) {
			// return "float";
			return "Float";
		} else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
				|| sqlType.equalsIgnoreCase("double") || sqlType.equalsIgnoreCase("real")) {
			return "Double";
		} else if (sqlType.equalsIgnoreCase("money") || sqlType.equalsIgnoreCase("smallmoney")) {
			// return "double";
			return "Double";
		} else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
				|| sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("date")) {
			// 返回String
			return "String";
		} else if (sqlType.equalsIgnoreCase("IMAGE")) {
			return "Blob";
		} else if (sqlType.equalsIgnoreCase("TEXT")) {
			return "String";
		}
		// System.err.println("类型未知" + sqlType);
		return sqlType;
	}

	/*
	 * 读取数据库中的表名
	 * 
	 * @return表名的String数组
	 */
	private static String[] getTabNames() {
		Connection conn = null;
		String sql = "show tables";
		String[] tabNames = null;

		try {
			conn = DBUtils.getConnection();
			PreparedStatement prep = conn.prepareStatement(sql);
			ResultSet rs = prep.executeQuery();
			rs.last();
			int size = rs.getRow();
			tabNames = new String[size];
			rs.beforeFirst();
			int i = 0;
			while (rs.next() && i < size) {
				tabNames[i] = rs.getString(1);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn);
		}
		return tabNames;
	}

	// 参数预处理

	public static void main(String[] args) {
		System.out.println("开始生成代码");
		String[] tabNames = getTabNames();
		dataBase2Mybatis = new DataBase2MybatisMapper();
		// 生成JavaBean文件
		for (int i = 0; i < tabNames.length; i++) {
			new DataBase2JavaBean(tabNames[i]);
		}
		// 生成Mybatis文档
		dataBase2Mybatis.CreateFileConfig();
		System.out.println("目标文件生成结束！");
		System.out.println("生成<typeAliases>元素信息");
		// <typeAliases>
	}
}
