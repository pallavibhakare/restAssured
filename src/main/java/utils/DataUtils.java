package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import constants.FileConstants;

public class DataUtils {
		
	public static String readLoginTestData(String key) throws IOException {
		FileInputStream file = new FileInputStream(FileConstants.TESTDATA_FILE_PATH);
		Properties p = new Properties();		
		p.load(file);
		return p.getProperty(key);
	}
}
