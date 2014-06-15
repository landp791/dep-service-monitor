package com.dep.monitor.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dep.monitor.model.Application;

public interface AppRepository {

	@Query("select * from app_email ae where ae.url = :blogId")
	Application queryByUrl(@Param("url") String url);
}
