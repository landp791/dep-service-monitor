package com.dep.monitor.repo.read;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dep.monitor.model.AppOwner;

public interface AppOwnerReadRepository extends JpaRepository<AppOwner, Long>, JpaSpecificationExecutor<AppOwner> {

	@Query("select ao from appOwner ao where ao.appUrl = :appUrl")
	AppOwner findByAppUrl(@Param("appUrl") String appUrl);
	
}
