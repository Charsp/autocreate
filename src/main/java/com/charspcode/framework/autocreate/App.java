package com.charspcode.framework.autocreate;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		// MybatisBaseTest test = new MybatisBaseTest();
		// test.querySessionTest();
		// test.returnMapTest();
		IActiveDao activeDao = new ActiveDao();
		activeDao.deldata();
		activeDao.helloworld();
	}
}
