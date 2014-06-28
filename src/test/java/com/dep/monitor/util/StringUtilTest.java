package com.dep.monitor.util;

import static com.dep.monitor.util.StringUtil.UUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.dep.monitor.model.AppOwner;
import com.google.common.collect.Lists;

public class StringUtilTest {

	@Test
	public void should_not_empty_when_get_uuid() {
		String uuid = UUID("serviceid");

		assertTrue(!StringUtils.isEmpty(uuid));
		assertEquals(uuid.length(), 41);
	}

	@Test
	public void should_without_line_when_get_uuid() {
		String uuid = UUID("serviceid");

		assertTrue(!StringUtils.contains(uuid, "-"));
	}

	@Test
	public void should_OK_when_one_appowner_in_list() {
		List<AppOwner> list = Lists.newArrayList();
		list.add(anAppOwner());
		
		String jsonStr = JSONArray.fromObject(list).toString();

		String expected = "[{\"id\":10,\"appUrl\":\"www.baidu.com\",\"appName\":"
				+ "\"testApp\",\"status\":2,\"owner\":\"landongpingpub@163.com\"}]";
		assertEquals(jsonStr, expected);
	}
	
	@Test
	public void should_OK_when_multi_appowner_in_list() {
		List<AppOwner> list = Lists.newArrayList();
		list.add(anAppOwner());
		list.add(anAppOwner());
		
		String jsonStr = JSONArray.fromObject(list).toString();

		String expected = "[{\"id\":10,\"appUrl\":\"www.baidu.com\",\"appName\":"
				+ "\"testApp\",\"status\":2,\"owner\":\"landongpingpub@163.com\"},"
				+ "{\"id\":10,\"appUrl\":\"www.baidu.com\",\"appName\":\"testApp\","
				+ "\"status\":2,\"owner\":\"landongpingpub@163.com\"}]";
		assertEquals(jsonStr, expected);
	}

	private AppOwner anAppOwner() {
		AppOwner ao = new AppOwner();
		ao.setId(10l);
		ao.setAppName("testApp");
		ao.setAppUrl("www.baidu.com");
		ao.setOwner("landongpingpub@163.com");
		ao.setStatus(2);
		return ao;
	}

}
