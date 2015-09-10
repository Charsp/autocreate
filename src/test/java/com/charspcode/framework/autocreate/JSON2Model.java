package com.charspcode.framework.autocreate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import com.swz.auto.code.test.DBUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * JSON2Model.java
 * 
 * @author CharsBoll
 * @version 2015年8月25日上午10:30:09
 */
public class JSON2Model {
	private static void outputFile(String file, StringBuffer classv) {
		FileWriter fwMybatis;
		try {
			classv.insert(0, "package " + DBUtils.getClassPath() + ";\r\n\r\n");
			// 目录检测 dir+ main/resources+ mysql+dao
			String myBatisPath = DBUtils.getMavenPath()
					+ DBUtils.getClassPath().replace(".", File.separator)
					+ File.separator;
			File dirXml = new File(myBatisPath);
			if (!dirXml.exists()) {
				dirXml.mkdirs();
				System.out.println("创建目录" + myBatisPath + "成功！");
			}
			// 文件生成 com.swz.dao
			fwMybatis = new FileWriter(myBatisPath + file + ".java");
			System.out.println("目标文件" + myBatisPath + file + ".java" + "生成！");
			PrintWriter pwMybatis = new PrintWriter(fwMybatis);
			// 打印输出
			pwMybatis.println(classv);
			pwMybatis.flush();
			pwMybatis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String upfirstChar(String key) {
		return key.substring(0, 1).toUpperCase()
				+ key.substring(1, key.length());

	}

	@SuppressWarnings("unchecked")
	private static void explainJsonobject(String className, JSONObject obj) {
		StringBuffer classv = new StringBuffer();
		className = upfirstChar(className);
		classv.append("\tpublic class " + className + " {\n");
		Iterator<String> it = obj.keys();
		while (it.hasNext()) {
			String key = it.next();
			Object keyval = obj.get(key);
			// JSONArray
			if (keyval instanceof JSONArray) {
				// System.out.println("数组");
				explainJsonarray(key, (JSONArray) keyval);
				classv.insert(0, "\timport java.util.List;\n");
				classv.append("\t\tprivate List<" + upfirstChar(key) + "> "
						+ key + ";\n");
			}
			// JSONObject
			else if (keyval instanceof JSONObject) {
				// System.out.println("非数组");
				explainJsonobject(key, (JSONObject) keyval);
				classv.append("\t\tprivate " + upfirstChar(key) + " " + key
						+ ";\n");
			}
			// 常量
			else {
				// System.out.println(key);
				if (keyval instanceof Integer) {
					classv.append("\t\tprivate Integer " + key + ";\n");
				} else if (keyval instanceof Double) {
					classv.append("\t\tprivate Double " + key + ";\n");
				} else if (keyval instanceof String) {
					classv.append("\t\tprivate String " + key + ";\n");
				}
				// 默认String
				else {
					classv.append("\t\tprivate String " + key + ";\n");
				}
			}
		}
		classv.append("\t}");
		outputFile(className, classv);
	}

	private static void explainJsonarray(String name, JSONArray keyval) {
		if (!keyval.isEmpty()) {
			explainJsonobject(name, keyval.getJSONObject(0));
		}
	}

	public static void main(String[] args) {
		JSONObject test = new JSONObject();
		JSONArray ary = new JSONArray();
		test.put("dd", 12);
		ary.add(test);
		test.put("dd", "adaaa");
		ary.add(test);
		test.put("aaa", 22.30);
		test.put("ddd", ary);
		explainJsonobject("test", test);
		System.out.println(test);
	}
}
