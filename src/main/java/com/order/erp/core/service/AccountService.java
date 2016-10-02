package com.order.erp.core.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.order.erp.core.domain.Account;
import com.order.erp.core.service.annotation.ErpService;
import com.order.erp.core.service.annotation.ErpServiceMethod;

/*
 * This Service is used only by logining
 * to creat an account need to use Customer or Staff Service
 */
@ErpService(module = "会员模块", name = "会员服务")
public interface AccountService extends _BaseService {
	
	@ErpServiceMethod(name = "保存帐号")
	@Transactional(propagation = Propagation.REQUIRED)
	public Account save(Account account); 
	
	@ErpServiceMethod(name = "根据用户名查找帐号")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Account findByUsername(String username); 

}