package org.mybatis.library.config;

import java.util.List;

/**
 * mybatis_config -> configuration -> settings
 * 
 * @author Charsp
 * @version 2015年8月1日上午8:28:06
 */
public class Settings {
	private List<Setting> setting;

	public List<Setting> getSetting() {
		return setting;
	}

	public void setSetting(List<Setting> setting) {
		this.setting = setting;
	}

}
