package org.amazon.library;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

	public static String configReader(String Key) throws IOException {
		File f = new File("./ConfigReader/ApplicationConfig.properties");
		FileReader fr = new FileReader(f);
		Properties prop = new Properties();
		prop.load(fr);
		return prop.get(Key).toString();
	}
}
