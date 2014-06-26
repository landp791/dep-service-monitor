package com.dep.monitor.repo.read;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dep.monitor.model.AppOwnerDep;

public interface AppOwnerReadRepository extends JpaRepository<AppOwnerDep, Long>, JpaSpecificationExecutor<AppOwnerDep> {

	@Query("select ao from appOwner ao where ao.id = :appId")
	AppOwnerDep queryByAppId(@Param("appId") String appId);
	
}
