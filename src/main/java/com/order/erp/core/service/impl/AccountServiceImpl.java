package com.order.erp.core.service.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.erp.core.domain.Account;
import com.order.erp.core.repository.AccountRepository;
import com.order.erp.core.service.AccountService;

@Service("accountService")
public class AccountServiceImpl extends _BaseServiceImpl implements AccountService {
	private EntityManager entityManager;
	@Autowired
	private AccountRepository repository;
	
	public Account save(Account account) {
		return repository.save(account);
	}
	
	public Account findByUsername(String username) {
		Account account = repository.findByUsername(username);
		// 在事务中手工加载 Staff/Company 和 Customer属性
		try{
			account.getUser().getCompany().getId();
		}catch(Exception e){
			logger.debug("try to load lazy property for account, get null object.");
		}
		return account;
	}


	public void initializeServiceData() {
		
	}





}
