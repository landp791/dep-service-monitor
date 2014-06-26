package com.dep.monitor.repo.write;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dep.monitor.model.AppOwnerDep;

public interface AppOwnerWriteRepository  extends JpaRepository<AppOwnerDep, Long>, JpaSpecificationExecutor<AppOwnerDep> {

	
}
