package com.charspcode.framework.autocreate;


/**
 * TestDao.java
 * 
 * @author CharsBoll
 * @version 2015年8月14日上午10:22:32
 */
public class ActiveDao extends ABaseSqlDao implements IActiveDao {

	@Override
	public String helloworld() {
		System.out.println("HD");
		return null;
	}

}
