package org.mybatis.library.config;

import java.util.List;

/**
 * mybatis_config -> configuration ->
 * 
 * @author Charsp
 * @version 2015年8月1日上午8:32:02
 */
public class TypeAliases {
	private List<Package> packagz;
	private List<TypeAlias> typeAlias;

	public List<Package> getPackagz() {
		return packagz;
	}

	public void setPackagz(List<Package> packagz) {
		this.packagz = packagz;
	}

	public List<TypeAlias> getTypeAlias() {
		return typeAlias;
	}

	public void setTypeAlias(List<TypeAlias> typeAlias) {
		this.typeAlias = typeAlias;
	}

}
