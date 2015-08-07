package org.mybatis.library.config;

import java.util.List;

public class DataSource {
	private String type;
	private List<Setting> property;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Setting> getProperty() {
		return property;
	}

	public void setProperty(List<Setting> property) {
		this.property = property;
	}

}
