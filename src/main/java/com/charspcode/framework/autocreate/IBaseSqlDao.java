package com.charspcode.framework.autocreate;

import java.util.Map;

/**
 * SqlDao.java
 * 
 * @author CharsBoll
 * @version 2015年8月14日上午10:15:42
 */
public interface IBaseSqlDao {
	/**
	 * 增
	 * 
	 * @return
	 */
	public Map<String, Object> savedata();

	public Map<String, Object> savedataAll();

	/**
	 * 删
	 * 
	 * @return
	 */

	public Map<String, Object> deldata();

	public Map<String, Object> deldataAll();

	/**
	 * 查
	 * 
	 * @return
	 */
	public Map<String, Object> getdata();

	public Map<String, Object> getdataAll();

	/**
	 * 改
	 * 
	 * @return
	 */
	public Map<String, Object> updatedata();

	public Map<String, Object> updatedataAll();
}
