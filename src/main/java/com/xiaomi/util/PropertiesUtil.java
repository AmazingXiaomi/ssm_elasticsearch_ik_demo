package com.xiaomi.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取config.properties配置
 * @author LTQ
 *
 */
public class PropertiesUtil {

	private static Properties properties = new Properties();
	static {
		InputStream in = PropertiesUtil.class.getResourceAsStream("/config.properties");
		try {
			properties.load(in); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    public static String get(String key) {  
    	String value = properties.getProperty(key);
    	if(value != null){
    		value = value.trim();
    	}
    	return value;
    }  
}
