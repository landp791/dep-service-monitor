package com.dep.monitor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dep.monitor.model.App;

public interface AppRepository extends JpaRepository<App, Long>, JpaSpecificationExecutor<App> {

	@Query("select s from service s where s.appUrl = :url")
	App queryByUrl(@Param("url") String url);
	
	@Query("select s from service s")
	App[] queryAllApp();

	@Query("select o.mail " 
	     + "from service s, appOwner so, owner o " 
	     + "where s.appId = so.appId "
	     + "and so.umAccount = o.umAccount " 
	     + "and s.serviceId = :serviceId")
	String[] queryTomailsByAppId(@Param("serviceId")long appId);
}
