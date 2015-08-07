package org.mybatis.library.config;

import java.util.List;

/**
 * mybatis_config -> configuration ->
 * 
 * @author Charsp
 * @version 2015年8月1日上午8:57:39
 */
public class Environments {
	private List<Environment> environment;

	public List<Environment> getEnvironment() {
		return environment;
	}

	public void setEnvironment(List<Environment> environment) {
		this.environment = environment;
	}

}
