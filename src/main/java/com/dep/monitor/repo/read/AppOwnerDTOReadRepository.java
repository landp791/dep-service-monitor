package com.dep.monitor.repo.read;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dep.monitor.model.AppOwner;

public interface AppOwnerDTOReadRepository extends JpaRepository<AppOwner, Long>, JpaSpecificationExecutor<AppOwner> {

	
}
