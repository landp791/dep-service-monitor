package com.dep.monitor.repo.write;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dep.monitor.model.App;

public interface AppWriteRepository  extends JpaRepository<App, Long>, JpaSpecificationExecutor<App> {

	
}
