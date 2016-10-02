package com.order.erp.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.erp.core.repository.CustomerRepository;
import com.order.erp.core.service.CustomerService;


@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	CustomerRepository repository;

	public void initializeServiceData() {
		
	}
}
