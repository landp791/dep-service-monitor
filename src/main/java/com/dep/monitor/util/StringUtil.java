package com.dep.monitor.util;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

	public static String UUID(String prefix) {
		UUID uuid = UUID.randomUUID();
		return prefix + StringUtils.replace(uuid.toString(), "-", "");
	}
}
