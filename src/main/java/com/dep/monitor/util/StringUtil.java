package com.dep.monitor.util;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.dep.monitor.model.AppOwnerDTO;

public class StringUtil {

	public static String UUID(String prefix) {
		UUID uuid = UUID.randomUUID();
		return prefix + StringUtils.replace(uuid.toString(), "-", "");
	}
	
	public static String toJson(List<AppOwnerDTO> apps) {
		return null;
	}
}
