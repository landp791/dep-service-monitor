package com.dep.monitor.repo.write;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dep.monitor.model.AppOwner;

public interface AppOwnerWriteRepository  extends JpaRepository<AppOwner, Long>, JpaSpecificationExecutor<AppOwner> {

	
}
