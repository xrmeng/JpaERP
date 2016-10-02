package com.order.erp.core.service.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.erp.core.domain.Amoeba;
import com.order.erp.core.domain.Company;
import com.order.erp.core.repository.AmoebaRepository;
import com.order.erp.core.repository.CompanyRepository;
import com.order.erp.core.service.AmoebaService;

@Service("amoebaService")
public class AmoebaServiceImpl implements AmoebaService {
	private EntityManager em;
	@Autowired
	private AmoebaRepository amoebaRepository;
	@Autowired
	private CompanyRepository repositoryCompany;

	public Amoeba save(Amoeba amoeba) {
		return amoebaRepository.save(amoeba);
	}
	

	public void initializeServiceData() {
		
	}



}
