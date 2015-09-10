package com.swz.auto.code.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import net.sf.json.JSONObject;

/**
 * File2Json.java
 * 
 * @author CharsBoll
 * @version 2015年8月25日上午11:49:54
 */
public class File2Json {
	private static String getJsonString() {
		File file = new File("d:/json/json.txt");
		BufferedReader reader = null;
		StringBuffer JsonStr = new StringBuffer();
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				// System.out.println("line " + line + ": " +
				// tempString.trim());
				JsonStr.append(tempString.trim());
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return JsonStr.toString();
	}

	public static void main(String[] args) {
		String jsonstr = getJsonString();
		System.out.println(jsonstr);
		JSONObject js = JSONObject.fromObject(jsonstr);
		Json2Model.explainJsonobject("merchant", js);

	}
}
