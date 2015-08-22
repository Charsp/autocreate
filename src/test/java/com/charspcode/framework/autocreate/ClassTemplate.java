package com.charspcode.framework.autocreate;

import java.util.List;

/**
 * ClassTemplate.java
 * 
 * @author CharsBoll
 * @version 2015年8月14日下午12:49:14
 */
public class ClassTemplate {
	private List<String> classimport;
	private String classpackage;
	private String classDescript;
	private ClassType classType;
	private String className;
	private List<MethodTemplate> classproperty;
	private List<String> classimplements;
	private String classparent;

	public List<String> getClassimport() {
		return classimport;
	}

	public void setClassimport(List<String> classimport) {
		this.classimport = classimport;
	}

	public String getClasspackage() {
		return classpackage;
	}

	public void setClasspackage(String classpackage) {
		this.classpackage = classpackage;
	}

	public String getClassDescript() {
		return classDescript;
	}

	public void setClassDescript(String classDescript) {
		this.classDescript = classDescript;
	}

	public ClassType getClassType() {
		return classType;
	}

	public void setClassType(ClassType classType) {
		this.classType = classType;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<MethodTemplate> getClassproperty() {
		return classproperty;
	}

	public void setClassproperty(List<MethodTemplate> classproperty) {
		this.classproperty = classproperty;
	}

	public List<String> getClassimplements() {
		return classimplements;
	}

	public void setClassimplements(List<String> classimplements) {
		this.classimplements = classimplements;
	}

	public String getClassparent() {
		return classparent;
	}

	public void setClassparent(String classparent) {
		this.classparent = classparent;
	}

}
