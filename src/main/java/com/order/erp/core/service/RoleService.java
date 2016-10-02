package com.order.erp.core.service;

import java.util.List;
import java.util.Set;

import com.order.erp.core.domain.Account;
import com.order.erp.core.domain.Role;
import com.order.erp.core.service.annotation.ErpService;

@ErpService()
public interface RoleService extends _BaseService {
	public Role save(Role role); 
	public Role findByRolename(String rolename); 
	
	public Role findOne(Long id);

}