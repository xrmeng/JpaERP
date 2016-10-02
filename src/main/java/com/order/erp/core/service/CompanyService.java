package com.order.erp.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.order.erp.core.domain.User;
import com.order.erp.core.service.annotation.ErpService;

@ErpService()
public interface CompanyService extends _BaseService {

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<User> findStaffsByCompanyId(Long companyId); 

}