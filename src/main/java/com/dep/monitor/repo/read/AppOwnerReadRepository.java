package com.dep.monitor.repo.read;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dep.monitor.model.App;

public interface AppOwnerReadRepository extends JpaRepository<App, Long>, JpaSpecificationExecutor<App> {

	@Query("select a from app a where a.appUrl = :url")
	App queryByUrl(@Param("url") String url);
	
	@Query("select a from app a")
	App[] queryAllApp();

	@Query("select o.mail " 
	     + "from app a, appOwner ao, owner o " 
	     + "where a.id = ao.appId "
	     + "and ao.umAccount = o.umAccount " 
	     + "and a.id = :appId")
	String[] queryTomailsByAppId(@Param("appId")long appId);
}
