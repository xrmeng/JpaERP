package com.order.erp.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.erp.core.domain.Role;
import com.order.erp.core.repository.RoleRepository;
import com.order.erp.core.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepository repository;
	
	public Role save(Role role) {
		return repository.save(role);
	}

	public Role findByRolename(String rolename) {
		return repository.findByRolename(rolename);
	}

	public Role findOne(Long id) {
		return repository.findOne(id);
	}


	public void initializeServiceData() {
		
	}
}
