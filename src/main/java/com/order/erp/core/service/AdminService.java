package com.order.erp.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.order.erp.core.domain.Amoeba;
import com.order.erp.core.domain.User;
import com.order.erp.core.service.annotation.ErpService;

@ErpService()
public interface AdminService extends _BaseService{

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Page<Amoeba> findAmoebas(Specification<Amoeba> spec, Pageable pageable);

}