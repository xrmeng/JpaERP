package com.order.erp.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.order.erp.core.domain.Role;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
	public Role findByRolename (String rolename);
	public Role findOne(Long id);
}
