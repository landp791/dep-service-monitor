package com.dep.monitor.util;

import static com.dep.monitor.util.StringUtil.UUID;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class StringUtilTests {
	
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
}
