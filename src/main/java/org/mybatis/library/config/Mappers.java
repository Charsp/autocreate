package org.mybatis.library.config;

import java.util.List;

/**
 * mybatis_config -> configuration ->
 * 
 * @author Charsp
 * @version 2015年8月1日上午8:36:48
 */
public class Mappers {
	private List<Mapper> mapper;
	private List<Package> packagz;

	public List<Mapper> getMapper() {
		return mapper;
	}

	public void setMapper(List<Mapper> mapper) {
		this.mapper = mapper;
	}

	public List<Package> getPackagz() {
		return packagz;
	}

	public void setPackagz(List<Package> packagz) {
		this.packagz = packagz;
	}

}
