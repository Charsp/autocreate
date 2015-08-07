package org.mybatis.library.config;

import java.util.List;

public class Properties {
	private String resource;
	private String url;
	private List<Setting> property;

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

	public List<Setting> getProperty() {
		return property;
	}

	public void setProperty(List<Setting> property) {
		this.property = property;
	}

}
