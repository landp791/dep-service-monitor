package com.dep.monitor.util;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

	/**
	 * 
	 * @return
	 */
	public static String UUID() {
		UUID uuid = UUID.randomUUID();
		return StringUtils.replace(uuid.toString(), "-", "");
	}
}
