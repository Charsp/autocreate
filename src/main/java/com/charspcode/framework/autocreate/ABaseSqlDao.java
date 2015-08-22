package com.charspcode.framework.autocreate;

import java.util.Map;

/**
 * TestDao.java
 * 
 * @author CharsBoll
 * @version 2015年8月14日上午10:19:20
 */
public abstract class ABaseSqlDao implements IBaseSqlDao {

	@Override
	public Map<String, Object> savedata() {
		System.out.println("BaseTestDao：savedata");
		return null;
	}

	@Override
	public Map<String, Object> savedataAll() {
		System.out.println("BaseTestDao：savedataAll");
		return null;
	}

	@Override
	public Map<String, Object> deldata() {
		System.out.println("BaseTestDao：deldata");
		return null;
	}

	@Override
	public Map<String, Object> deldataAll() {
		System.out.println("BaseTestDao：deldataAll");
		return null;
	}

	@Override
	public Map<String, Object> getdata() {
		System.out.println("BaseTestDao：getdata");
		return null;
	}

	@Override
	public Map<String, Object> getdataAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> updatedata() {
		System.out.println("updatedata");
		return null;
	}

	@Override
	public Map<String, Object> updatedataAll() {
		System.out.println("updatedataAll");
		return null;
	}
}
