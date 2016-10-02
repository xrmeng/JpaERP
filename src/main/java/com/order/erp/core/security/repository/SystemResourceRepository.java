package com.order.erp.core.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.order.erp.core.security.domain.SystemResource;

@Repository("systemResourceRepository")
public interface SystemResourceRepository extends JpaRepository<SystemResource, Long>, JpaSpecificationExecutor<SystemResource> {

}
