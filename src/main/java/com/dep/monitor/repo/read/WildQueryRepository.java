package com.dep.monitor.repo.read;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.dep.monitor.model.AllAppOwners;

public class WildQueryRepository extends JdbcDaoSupport{
	public static final String APP_NAME_KEY = "appName";
	public static final String UM_ACCOUNT_KEY = "umAccount";

	public AllAppOwners queryAllAppOwners() {
		String sql = " select a.appName, a.appUrl, o.umAccount " +
				" from appOwner ao, app a, owner o " +
				" where a.id = ao.appId " +
				"   and o.umAccount = ao.umAccount " +
				" order by a.appUrl ";
		final Map<String, Map<String, String>> tmpValue = new HashMap<String, Map<String, String>>();
		
		Object[] args = new Object[10];
		getJdbcTemplate().query(sql, args, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				String appUrl = rs.getString("APPURL");
				String appName = rs.getString("APPNAME");
				String umAccount = rs.getString("UMACCOUNT");
				if (tmpValue.get(appUrl) == null) {
					Map<String, String> map = new HashMap<String, String>();
					map.put(APP_NAME_KEY, appName);
					map.put(UM_ACCOUNT_KEY, umAccount);
					tmpValue.put(appUrl, map);
				}
			}
		});
		
		return null;
	}
}
