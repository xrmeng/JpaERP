package com.order.erp.core.service.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.order.erp.core.domain.Amoeba;
import com.order.erp.core.domain.User;
import com.order.erp.core.repository.AmoebaRepository;
import com.order.erp.core.service.AdminService;

@Service("adminService")
public class AdminServiceImpl extends _BaseServiceImpl implements AdminService {
	private EntityManager entityManager;
	@Autowired
	private AmoebaRepository amoebaRepository;
	
	public Page<Amoeba> findAmoebas(Specification<Amoeba> spec, Pageable pageable) {
		return amoebaRepository.findAll(spec, pageable);
	}

	@Override
	public void initializeServiceData() {
		// TODO Auto-generated method stub
		
	}
}
