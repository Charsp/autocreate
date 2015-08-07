package com.swz.auto.code.test;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtils {
	private static Properties prop = new Properties();
	private static String modelpath = "com.swz.data.vo.mysql";
	private static String mybatispath = "mysql.dao";
	private static String mavenPath = File.separator + "src" + File.separator;
	private static String database;
	private static String daopath;

	public static String getDatabase() {
		return database;
	}

	public static String getMybatispath() {
		return mybatispath;
	}

	public static String getDaopath() {
		return daopath;
	}

	public static void setDaopath(String daopath) {
		DBUtils.daopath = daopath;
	}

	static {
		ClassLoader loader = DBUtils.class.getClassLoader();
		InputStream in = loader.getResourceAsStream("config.properties");
		try {
			prop.load(in);
			Class.forName(prop.getProperty("driver"));
			if (prop.containsKey("maven")) {
				if (prop.getProperty("maven").equalsIgnoreCase("true"))
					mavenPath = File.separator + "src" + File.separator
							+ "main" + File.separator + "java" + File.separator;
			}

			if (prop.containsKey("modelpath")) {
				modelpath = prop.getProperty("modelpath").trim();
			}

			if (prop.containsKey("mybatispath")) {
				mybatispath = prop.getProperty("mybatispath").trim();
			}

			if (prop.containsKey("database")) {
				database = prop.getProperty("database").trim();
			}
			if (prop.containsKey("daopath")) {
				daopath = prop.getProperty("daopath").trim();
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("异常！！");
		}
	}

	/*
	 * 把输入字符串的首字母变成大写
	 * 
	 * @param str
	 * 
	 * @return
	 */
	public static String initCap(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}

	public static Connection getConnection() throws Exception {
		return DriverManager.getConnection(prop.getProperty("url")
				+ getDatabase() + "?characterEncoding=UTF-8",
				prop.getProperty("username"), prop.getProperty("password"));
	}

	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static String getClassPath() {
		return modelpath;
	}

	public static String getMavenPath() {
		return System.getProperty("user.dir") + mavenPath;
	}

	public static void main(String[] args) throws Exception {
		getConnection();
		System.out.println("ceshi wancheng ");
	}
}
