package com.order.erp.core.service;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.order.erp.core.domain.Account;
import com.order.erp.core.domain.Role;
import com.order.erp.core.service.annotation.ErpService;

@ErpService()
public interface SecurityService extends _BaseService {
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void test(); 
	
}