package com.swz.auto.code.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.library.config.Configuration;
import org.mybatis.library.config.Mapper;
import org.mybatis.library.config.Mappers;

import com.swz.auto.code.model.MybatisConfig;

public class DataBase2MybatisConfig {
	public void createFile() {
		Mapper mapper = new Mapper();
		mapper.setResource(DBUtils.getMybatispath().replace(".", "/") + "/" + DBUtils.getDatabase() + "*.xml");
		Mappers mappers = new Mappers();
		List<Mapper> mapperlist = new ArrayList<>();
		mapperlist.add(mapper);
		mappers.setMapper(mapperlist);
		Configuration configuration = new Configuration();
		configuration.setMappers(mappers);
		MybatisConfig config = new MybatisConfig();
		config.setConfiguration(configuration);
		FileWriter fwMybatis;
		try {
			// 目录检测 dir+ main/resources+ mysql+dao
			String myBatisPath = DBUtils.getMavenPath().replace("java", "resources")
					+ DBUtils.getMybatispath().replace(".", File.separator) + File.separator;
			File dirXml = new File(myBatisPath);
			if (!dirXml.exists()) {
				dirXml.mkdirs();
				System.out.println("创建目录" + myBatisPath + "成功！");
			}
			// 文件生成 com.swz.dao
			fwMybatis = new FileWriter(myBatisPath + ".xml");
			System.out.println("目标文件" + myBatisPath + ".xml" + "生成！");
			PrintWriter pwMybatis = new PrintWriter(fwMybatis);
			// 打印输出
			pwMybatis.println(config.crerateConfig());
			pwMybatis.flush();
			pwMybatis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
