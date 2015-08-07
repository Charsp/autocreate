package org.mybatis.library.config;


/**
 * mybatis_config -> configuration
 * 
 * @author Charsp
 * @version 2015年8月1日上午8:52:44
 */
public class Configuration {
	private Settings settings;
	private TypeAliases typeAliases;
	private Mappers mappers;
	private DatabaseIdProvider databaseIdProvider;
	private Environments environments;
	private DataSource objectFactory;
	private DataSource objectWrapperFactory;
	private Plugins plugins;
	private Properties properties;
	private TypeHandlers typehandlers;

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public TypeAliases getTypeAliases() {
		return typeAliases;
	}

	public void setTypeAliases(TypeAliases typeAliases) {
		this.typeAliases = typeAliases;
	}

	public Mappers getMappers() {
		return mappers;
	}

	public void setMappers(Mappers mappers) {
		this.mappers = mappers;
	}

	public DatabaseIdProvider getDatabaseIdProvider() {
		return databaseIdProvider;
	}

	public void setDatabaseIdProvider(DatabaseIdProvider databaseIdProvider) {
		this.databaseIdProvider = databaseIdProvider;
	}

	public Environments getEnvironments() {
		return environments;
	}

	public void setEnvironments(Environments environments) {
		this.environments = environments;
	}

	public DataSource getObjectFactory() {
		return objectFactory;
	}

	public void setObjectFactory(DataSource objectFactory) {
		this.objectFactory = objectFactory;
	}

	public DataSource getObjectWrapperFactory() {
		return objectWrapperFactory;
	}

	public void setObjectWrapperFactory(DataSource objectWrapperFactory) {
		this.objectWrapperFactory = objectWrapperFactory;
	}

	public Plugins getPlugins() {
		return plugins;
	}

	public void setPlugins(Plugins plugins) {
		this.plugins = plugins;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public TypeHandlers getTypehandlers() {
		return typehandlers;
	}

	public void setTypehandlers(TypeHandlers typehandlers) {
		this.typehandlers = typehandlers;
	}

}
