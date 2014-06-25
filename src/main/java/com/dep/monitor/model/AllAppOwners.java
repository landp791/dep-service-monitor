package com.dep.monitor.model;

import java.util.List;

/**
 * 对应管理页面上的数据表
 * 看起来不像是model
 * @author LANDONGPING791
 *
 */
public class AllAppOwners {
	
	private List<AppOwnerView> appOwnerViews;

	public List<AppOwnerView> getAppOwnerViews() {
		return appOwnerViews;
	}

	public void setAppOwnerViews(List<AppOwnerView> appOwnerViews) {
		this.appOwnerViews = appOwnerViews;
	}
	
}
