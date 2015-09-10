package com.swz.auto.code.tool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.swz.auto.code.test.DBUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * JSON2Model.java
 * 
 * @author CharsBoll
 * @version 2015年8月25日上午10:30:09
 */
public class Json2Model {
	private static void outputFile(String file, StringBuffer classv) {
		FileWriter fwMybatis;
		try {
			// 增加包名
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

	private static StringBuffer getSet(String property, String name) {
		StringBuffer getSetStr = new StringBuffer();
		getSetStr.append("\tpublic " + property + " get" + upfirstChar(name)
				+ "() {\n");
		getSetStr.append("\t\treturn " + name + ";\n");
		getSetStr.append("\t}\n\n");

		getSetStr.append("\tpublic void set" + upfirstChar(name) + "("
				+ property + " " + name + ") {\n");
		getSetStr.append("\tthis." + name + " = " + name + ";");
		getSetStr.append("\t}\n\n");
		return getSetStr;
	}

	@SuppressWarnings("unchecked")
	public static void explainJsonobject(String className, JSONObject obj) {
		StringBuffer classv = new StringBuffer();
		StringBuffer getSetStr = new StringBuffer();
		boolean hasSet = false;
		className = upfirstChar(className);
		// 增加序列化支持
		classv.append("import java.io.Serializable;\r\n\r\n");
		classv.append("@SuppressWarnings(\"serial\")\r\n");
		classv.append("public class " + className
				+ " implements Serializable{\r\n");
		Iterator<String> it = obj.keys();
		while (it.hasNext()) {
			String key = it.next();
			Object keyval = obj.get(key);
			// JSONArray
			if (keyval instanceof JSONArray) {
				// System.out.println("数组");
				// 判断下腾讯不一样的接口！傻X腾讯
				JSONArray testArray = (JSONArray) keyval;
				hasSet = true;
				if (testArray.get(0) instanceof JSONObject) {
					explainJsonarray(key, (JSONArray) keyval);
					classv.append("\tprivate Set<" + upfirstChar(key) + "> "
							+ key + ";\n");
					getSetStr.append(getSet("Set<" + upfirstChar(key) + "> ",
							key));
				} else {
					classv.append("\tprivate Set<String> " + key + ";\r\n");
					getSetStr.append(getSet("Set<String>", key));
				}
			}
			// JSONObject
			else if (keyval instanceof JSONObject) {
				// System.out.println("非数组");
				explainJsonobject(key, (JSONObject) keyval);
				classv.append("\tprivate " + upfirstChar(key) + " " + key
						+ ";\n");
				getSetStr.append(getSet(upfirstChar(key), key));
			}
			// 常量
			else {
				// System.out.println(key);
				if (keyval instanceof Integer) {
					classv.append("\tprivate Integer " + key + ";\r\n");
					getSetStr.append(getSet("Integer", key));
				} else if (keyval instanceof Double) {
					classv.append("\tprivate Double " + key + ";\r\n");
					getSetStr.append(getSet("Double", key));
				} else if (keyval instanceof String) {
					classv.append("\tprivate String " + key + ";\r\n");
					getSetStr.append(getSet("String", key));
				}
				// 默认String
				else {
					classv.append("\tprivate String " + key + ";\r\n");
					getSetStr.append(getSet("String", key));
				}
			}
		}
		classv.append("\r\n" + getSetStr);
		classv.append("}");
		if (hasSet)
			classv.insert(0, "import java.util.Set;\r\n\r\n");
		outputFile(className, classv);
	}

	@SuppressWarnings("unchecked")
	private static void explainJsonarray(String name, JSONArray keyval) {
		if (!keyval.isEmpty()) {
			JSONObject arrayTemp = new JSONObject();
			List<String> keys = new ArrayList<String>();
			for (int i = 0; i < keyval.size(); i++) {
				JSONObject Temp = keyval.getJSONObject(i);
				for (String key : (Set<String>) Temp.keySet()) {
					if (!keys.contains(key)) {
						arrayTemp.put(key, Temp.get(key));
					}
				}
			}
			explainJsonobject(name, arrayTemp);
		}
	}

	public static void main(String[] args) {
		JSONObject test = new JSONObject();
		JSONArray ary = new JSONArray();
		test.put("dd", 12);
		ary.add(test);
		test.put("dd", "adaaa");
		test.put("dd2", "adaaa");
		ary.add(test);
		test.put("aaa", 22.30);
		test.put("ddd", ary);
		ary = new JSONArray();
		ary.add("s1");
		ary.add("s2");
		ary.add("s3");
		ary.add("s4");
		test.put("ddd", ary);
		explainJsonobject("test", test);
		System.out.println(test);
	}
}
