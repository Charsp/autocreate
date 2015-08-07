package org.mybatis.library.mapper;

/**
 * 表的基本信息
 * 
 * @author Charsp
 * @version 2015年7月7日下午3:40:19 使用 show full columns from 表名 查询一下数据
 */
public class TableInfoModel {
	/**
	 * 列
	 */
	private String Field;
	/**
	 * 列的类型
	 */
	private String Type;
	/**
	 * 编码格式
	 */
	private String Collation;
	/**
	 * 是否可为Null
	 */
	private String Null;
	/**
	 * 键值类型(eg 主键，外键，唯一键)
	 */
	private String Key;
	/**
	 * 默认值
	 */
	private String Default;
	/**
	 * 其他说明(eg 自增)
	 */
	private String Extra;
	/**
	 * 权限
	 */
	private String Privileges;
	/**
	 * 列的描述
	 */
	private String Comment;

	public String getField() {
		return Field;
	}

	public void setField(String field) {
		Field = field;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getCollation() {
		return Collation;
	}

	public void setCollation(String collation) {
		Collation = collation;
	}

	public String getNull() {
		return Null;
	}

	public void setNull(String null1) {
		Null = null1;
	}

	public String getDefault() {
		return Default;
	}

	public void setDefault(String default1) {
		Default = default1;
	}

	public String getKey() {
		return Key;
	}

	public void setKey(String key) {
		Key = key;
	}

	public String getExtra() {
		return Extra;
	}

	public void setExtra(String extra) {
		Extra = extra;
	}

	public String getPrivileges() {
		return Privileges;
	}

	public void setPrivileges(String privileges) {
		Privileges = privileges;
	}

	public String getComment() {
		return Comment;
	}

	public void setComment(String comment) {
		Comment = comment;
	}
}
