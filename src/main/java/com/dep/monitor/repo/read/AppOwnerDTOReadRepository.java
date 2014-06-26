package com.dep.monitor.repo.read;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dep.monitor.model.AppOwnerDTO;

public interface AppOwnerDTOReadRepository extends JpaRepository<AppOwnerDTO, String>, JpaSpecificationExecutor<AppOwnerDTO> {

	
}
