package com.dep.monitor.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dep.monitor.model.Application;

@Repository
public interface AppOwnerRepository {

	@Query("select * from app_email ae where ae.url = :blogId")
	Application queryByUrl(@Param("url") String url);
}
