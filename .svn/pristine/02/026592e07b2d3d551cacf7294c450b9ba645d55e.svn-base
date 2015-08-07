package com.swz.auto.code.tool;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.library.config.Configuration;
import org.mybatis.library.config.DatabaseIdProvider;
import org.mybatis.library.config.Plugin;
import org.mybatis.library.config.Plugins;
import org.mybatis.library.config.Setting;

/**
 * 由javaBean，转换成 XML
 * 
 * @author Charsp
 * @version 2015年8月1日上午9:19:38
 */
public class Bean2Xml {
	public final static String HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

	private static <T> String isString(T t) {
		// 以String作为 Type
		if (t instanceof String) {
			return "STRING";
		}
		// 以List作为复合参数值
		if (t instanceof List<?>) {
			return "LIST";
		}
		// 以StringBuffer作为value域
		if (t instanceof StringBuffer) {
			return "STRINGBUFFER";
		}
		return "";
	}

	private static String AddType(String type, Object obj) {
		return " " + type + "=\"" + obj + "\"";

	}

	/*
	 * 获取当前类的所有变量名
	 */
	public static String getAttributes(String father, Object object, int depth) {
		// System.out.println("将要解析class：" + clazz.getName());
		// System.out.println("===============本类属性========================");
		// 取得本类的全部属性
		Class<?> clazz = object.getClass();
		Field[] field = clazz.getDeclaredFields();
		String typeVaule = "";
		String propertieValue = "";
		String listVaule = "";
		for (int i = 0; i < field.length; i++) {
			// // 权限修饰符
			// int mo = field[i].getModifiers();
			// String priv = Modifier.toString(mo);
			// // 属性类型
			// Class<?> type = field[i].getType();
			// System.out.println(priv + " " + type.getName() + " "
			// + field[i].getName() + ";");
			field[i].setAccessible(true);// 修改访问权限
			String typeName = field[i].getName();
			typeName = typeName.substring(0, 1).toUpperCase()
					+ typeName.substring(1, typeName.length());
			// 获取一个数据的值
			Object obj = null;
			try {
				field[i].setAccessible(true);
				obj = clazz.newInstance();
				// Method method = obj.getClass().getMethod("get" + typeName);
				// System.out.println("获得" + method.invoke(obj));
				// obj = method.invoke(obj);
				obj = field[i].get(object);
				if (obj != null) {
					switch (isString(obj)) {
					case "STRING":
						typeVaule += AddType(field[i].getName(), obj);
						break;
					case "LIST":
						List<?> listObj = (List<?>) obj;
						for (int j = 0; j < listObj.size(); j++) {
							listVaule += getAttributes(field[i].getName(),
									listObj.get(j), depth + 1);
						}
						break;
					case "STRINGBUFFER":
						propertieValue = (String) obj;
						break;
					default:
						propertieValue += getAttributes(field[i].getName(),
								obj, depth + 1);
						break;
					}

				} else {
					// System.out.println(obj + "is null ");
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		// System.out.println("=============实现的接口或者父类的属性===========");
		// 取得实现的接口或者父类的属性
		Field[] filed1 = clazz.getFields();
		for (int j = 0; j < filed1.length; j++) {
			// 权限修饰符
			int mo = filed1[j].getModifiers();
			String priv = Modifier.toString(mo);
			// 属性类型
			Class<?> type = filed1[j].getType();
			System.out.println(priv + " " + type.getName() + " "
					+ filed1[j].getName() + ";");
		}
		String result = "";
		if (depth > 0) {
			result = "\n";
			for (int j = 0; j < depth; j++) {
				result += "\t";
			}
		}
		result += "<" + father + typeVaule;
		if (propertieValue != "" || listVaule != "") {
			result += ">" + propertieValue + listVaule + "\n";
			for (int j = 0; j < depth; j++) {
				result += "\t";
			}
			result += "</" + father;
		} else {
			result += " /";
		}
		result += ">";
		return result;
	}

	public static void main(String[] args) {
		Configuration configuration = new Configuration();
		DatabaseIdProvider databaseIdProvider = new DatabaseIdProvider();
		databaseIdProvider.setType("AAAA");
		List<Setting> property = new ArrayList<Setting>();
		Setting ddd = new Setting();
		ddd.setName("属性12名称");
		ddd.setValue("属1性值");
		property.add(ddd);
		ddd = new Setting();
		ddd.setName("属性22名称");
		ddd.setValue("属2性值");
		property.add(ddd);
		ddd = new Setting();
		ddd.setName("属性23名称");
		ddd.setValue("属3性值");
		property.add(ddd);
		databaseIdProvider.setProperty(property);
		configuration.setDatabaseIdProvider(databaseIdProvider);
		Plugins plugins = new Plugins();
		List<Plugin> plugin = new ArrayList<Plugin>();
		Plugin arg0 = new Plugin();
		arg0.setProperty(property);
		arg0.setInterceptor("adsfads");
		plugin.add(arg0);
		plugins.setPlugin(plugin);
		configuration.setPlugins(plugins);
		String aaa = getAttributes("AAA", configuration, 0);
		System.err.println("++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println(aaa);
	}
}
