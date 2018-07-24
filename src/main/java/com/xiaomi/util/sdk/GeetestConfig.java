package com.xiaomi.util.sdk;

import org.springframework.context.annotation.Configuration;

import javax.swing.text.StyledEditorKit.BoldAction;

/**
 * GeetestWeb配置文件
 * 
 *
 */
@Configuration
public class GeetestConfig {

	private static final String geetest_id = "2be4c09c3d8ff3bae2ab7ab9b7c156b8";
	private static final String geetest_key = "1fb92703e353dee1abf983f8ec3a039c";
	private static final boolean newfailback = true;

	public static final String getGeetest_id() {
		return geetest_id;
	}

	public static final String getGeetest_key() {
		return geetest_key;
	}
	
	public static final boolean isnewfailback() {
		return newfailback;
	}

}
