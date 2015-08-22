package com.charspcode.framework.autocreate;

/**
 * OutputClass.java
 * 
 * @author CharsBoll
 * @version 2015年8月14日下午12:48:43
 */
public class OutputClass {
	private StringBuffer outputString = new StringBuffer();

	public String createClass(ClassTemplate classTemplate) {
		// 处理类的package
		outputString.append("package " + classTemplate.getClasspackage()
				+ "\r\n\r\n");
		// 处理类的引用
		if (classTemplate.getClassimport() != null) {
			for (int i = 0; i < classTemplate.getClassimport().size(); i++) {
				outputString.append("import "
						+ classTemplate.getClassimport().get(i) + "\r\n");
			}
			outputString.append("\r\n ");
		}
		// 处理类的描述
		if (classTemplate.getClassDescript() != null)
			outputString.append(classTemplate.getClassDescript() + "\r\n");

		// 虚拟类
		if (classTemplate.getClassType() == ClassType.ABSTRACT) {
		}
		// 普通类
		if (classTemplate.getClassType() == ClassType.CLASS) {
		}
		// enum
		if (classTemplate.getClassType() == ClassType.ENUM) {
		}
		// 接口
		if (classTemplate.getClassType() == ClassType.INTERFACE) {
			CreateInf(classTemplate);
		}
		// 其他未知
		if (classTemplate.getClassType() == ClassType.OTHER) {
		}
		return null;
	}

	public String checkExtends(ClassTemplate classTemplate) {
		StringBuffer checkExtend = new StringBuffer();
		if (classTemplate.getClassparent() != null) {
			checkExtend.append(" extends " + classTemplate.getClassparent());
		}
		if (classTemplate.getClassimplements() != null) {
			for (int i = 0; i < classTemplate.getClassimplements().size(); i++) {
				checkExtend.append(" implements "
						+ classTemplate.getClassimplements().get(i));
			}
		}
		return checkExtend.toString();
	}

	private String CreateInf(ClassTemplate classTemplate) {
		// 判断继承
		outputString.append("public interface " + classTemplate.getClassName());
		outputString.append(checkExtends(classTemplate) + " {\r\n");
		// 直接就是方法public的，自动生成代码，没有返回值和函数体
		if (classTemplate.getClassproperty() != null)
			for (int i = 0; i < classTemplate.getClassproperty().size(); i++) {
				MethodTemplate template = classTemplate.getClassproperty().get(
						i);
				if (template != null) {
					outputString.append("\t" + template.getDescript()
							+ "\t\r\n");
					outputString.append("\tpublic " + template.getType() + " "
							+ template.getName()
							+ "(Map<String,Object> varMap);");
				}

			}

		return null;
	}
}
