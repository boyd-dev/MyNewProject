package com.foo.myapp.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GlobalProperties {

	private final static Logger LOGGER = LoggerFactory.getLogger(GlobalProperties.class);

	//파일구분자
	private final static String FILE_SEPARATOR = System.getProperty("file.separator");

	public static final String RELATIVE_PATH_PREFIX = GlobalProperties.class.getResource("").getPath().substring(0, GlobalProperties.class.getResource("").getPath().lastIndexOf("com"));
	public static final String GLOBALS_PROPERTIES_FILE = RELATIVE_PATH_PREFIX + "framework" + FILE_SEPARATOR + "props" + FILE_SEPARATOR + "globals.properties";


	public static String getProperty(String keyName) {
		String value = "";

		LOGGER.debug("getProperty : {} = {}", GLOBALS_PROPERTIES_FILE, keyName);

		FileInputStream fis = null;
		try {
			Properties props = new Properties();

			fis = new FileInputStream(WebUtil.filePathBlackList(GLOBALS_PROPERTIES_FILE));

			props.load(new BufferedInputStream(fis));
			if (props.getProperty(keyName) == null) {
				return "";
			}
			value = props.getProperty(keyName).trim();
		} catch (FileNotFoundException fe) {
			LOGGER.debug("Property file not found.", fe);
			throw new RuntimeException("Property file not found", fe);
		} catch (IOException ioe) {
			LOGGER.debug("Property file IO exception", ioe);
			throw new RuntimeException("Property file IO exception", ioe);
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return value;
	}


}
