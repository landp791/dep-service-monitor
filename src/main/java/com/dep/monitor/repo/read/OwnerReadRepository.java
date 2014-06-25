package com.dep.monitor.repo.read;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dep.monitor.model.App;
import com.dep.monitor.model.Owner;

public interface OwnerReadRepository extends JpaRepository<Owner, Long>, JpaSpecificationExecutor<Owner> {

	@Query("select a from owner o where a.appUrl = :id")
	App queryById(@Param("id") String ownerId);
	
}
