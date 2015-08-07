package com.swz.auto.code.model;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.library.config.Configuration;
import org.mybatis.library.config.Setting;
import org.mybatis.library.config.Settings;

import com.swz.auto.code.tool.Bean2Xml;

public class MybatisConfig {
	private final String tipsBar = "<!DOCTYPE configuration PUBLIC  \n\"-//mybatis.org//DTD Config 3.0//EN\"  \n\"http://mybatis.org/dtd/mybatis-3-config.dtd\">\n";
	private Configuration configuration;

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public static void main(String[] args) {
		MybatisConfig aConfig = new MybatisConfig();
		aConfig.crerateConfig();
	}

	public String crerateConfig() {
		Configuration config = new Configuration();
		if (configuration != null) {
			config = configuration;
		}
		if (config.getSettings() == null) {
			config.setSettings(defaultSettings());
		}
		String xml = Bean2Xml.getAttributes("configuration", config, 0);
		return Bean2Xml.HEADER + tipsBar + xml;
	}

	private Settings defaultSettings() {
		Settings settings = new Settings();
		List<Setting> setting = new ArrayList<Setting>();
		// <!-- 这个配置使全局的映射器启用或禁用缓存 -->
		Setting set = new Setting();
		set.setName("cacheEnabled");
		set.setValue("true");
		setting.add(set);
		// <!-- 全局启用或禁用延迟加载。当禁用时，所有关联对象都会即时加载 -->
		set = new Setting();
		set.setName("lazyLoadingEnabled");
		set.setValue("true");
		setting.add(set);
		// <!-- 当启用时，有延迟加载属性的对象在被调用时将会完全加载任意属性。否则，每种属性将会按需要加载 -->
		set = new Setting();
		set.setName("aggressiveLazyLoading");
		set.setValue("true");
		setting.add(set);
		// <!-- 允许或不允许多种结果集从一个单独的语句中返回（需要适合的驱动） -->
		set = new Setting();
		set.setName("multipleResultSetsEnabled");
		set.setValue("true");
		setting.add(set);
		// <!-- 使用列标签代替列名。不同的驱动在这方便表现不同。参考驱动文档或充分测试两种方法来决定所使用的驱动 -->
		set = new Setting();
		set.setName("useColumnLabel");
		set.setValue("true");
		setting.add(set);
		// <!--
		// 允许JDBC支持生成的键。需要适合的驱动。如果设置为true则这个设置强制生成的键被使用，尽管一些驱动拒绝兼容但仍然有效（比如Derby）
		// -->
		set = new Setting();
		set.setName("useGeneratedKeys");
		set.setValue("true");
		setting.add(set);
		// <!--
		// 指定MyBatis如何自动映射列到字段/属性。PARTIAL只会自动映射简单，没有嵌套的结果。FULL会自动映射任意复杂的结果（嵌套的或其他情况）
		// -->
		set = new Setting();
		set.setName("autoMappingBehavior");
		set.setValue("PARTIAL");
		setting.add(set);
		// <!--
		// 配置默认的执行器。SIMPLE执行器没有什么特别之处。REUSE执行器重用预处理语句。BATCH执行器重用语句和批量更新
		// -->
		set = new Setting();
		set.setName("defaultExecutorType");
		set.setValue("SIMPLE");
		setting.add(set);
		// <!-- 设置超时时间，它决定驱动等待一个数据库响应的时间 -->
		set = new Setting();
		set.setName("defaultStatementTimeout");
		set.setValue("25000");
		setting.add(set);
		settings.setSetting(setting);
		return settings;
	}
}
