package dmlExport.manager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {

	private static final ConfigManager instance = new ConfigManager();

	private ConfigManager() {
	}

	public static ConfigManager instance() {
		return instance;
	}

	private Properties properties;

	// 프로그램이 처음 실행될 때 한번만 호출되는 메소드
	public void loadProperties(String path) {
		properties = new Properties();

		File file = new File(path);
		try {
			properties.load(new BufferedInputStream(new FileInputStream(file)));
			System.out
					.println("Succeeded in loading 'database.properties' file");
		} catch (FileNotFoundException e) {
			System.out.println("'database.properties' file was not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Exception occurred");
			e.printStackTrace();
		}
	}

	public Properties getProperties() {
		return properties;
	}

}
