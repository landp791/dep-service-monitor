package com.dep.monitor.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dep.monitor.model.App;

@Repository
public interface AppOwnerRepository {

	@Query("select * from app_email ae where ae.url = :blogId")
	App queryByUrl(@Param("url") String url);
	
	@Query("select * from app_email ae")
	App[] queryAllApp();

	@Query("select * ")
	List<String> queryTomailsByAppId(String appId);
}
