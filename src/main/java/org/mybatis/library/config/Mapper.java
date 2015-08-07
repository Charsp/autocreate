package org.mybatis.library.config;

/**
 * mybatis_config -> configuration -> mapper
 * 
 * @author Charsp
 * @version 2015年8月1日上午8:36:40
 */
public class Mapper {
	private String resource;
	private String url;
	private String clazz;

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

}
