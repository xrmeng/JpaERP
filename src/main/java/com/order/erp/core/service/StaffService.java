package com.order.erp.core.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.order.erp.core.domain.Company;
import com.order.erp.core.domain.User;
import com.order.erp.core.service.annotation.ErpService;

@ErpService()
public interface StaffService extends _BaseService {
	
	@Transactional(propagation = Propagation.REQUIRED)
	public User save(User user); 
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<User> save(Iterable<User> users); 
	
	public User findOne(Long id);
	
	//public List<Staff> findStaffsByAmoebaId(Long amoebaId);
	//public Page<Staff> findStaffsByAmoebaId(Long amoebaId, Pageable pageable);

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<User> findUsers(Specification<User> spec);
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Page<User> findUsers(Specification<User> spec, Pageable pageable);

	@Transactional(propagation = Propagation.REQUIRED)
	public Company createCompany(Company company);
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public User findByAccountUsername(String username);

}