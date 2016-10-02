package com.order.erp.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.order.erp.core.domain.Account;
import com.order.erp.core.domain.Company;
import com.order.erp.core.domain.User;
import com.order.erp.core.repository.AccountRepository;
import com.order.erp.core.repository.CompanyRepository;
import com.order.erp.core.repository.StaffRepository;
import com.order.erp.core.repository.daoSamples.StaffDao;
import com.order.erp.core.service.StaffService;

@Service("staffService")
public class StaffServiceImpl extends _BaseServiceImpl implements StaffService {
	@Autowired
	private StaffDao daoStaff;
	@Autowired
	private StaffRepository repository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private AccountRepository accountRepository;

	public User findOne(Long id) {
		return repository.findOne(id);
	}

	public User save(User staff) {
		return repository.save(staff);
	}

	public Company createCompany(Company company) {
		return companyRepository.save(company);
	}

	public User findByAccountUsername(String username) {
		Account account = accountRepository.findByUsername(username);
		User staff = null;
		// 在事务中手工加载lazy load属性
		try{
			staff = account.getUser();
			staff.getCompany().getId();
			staff.getAccount().getId();
		}catch(Exception e){
			logger.debug("try to load lazy property for account, get null object.");
		}
		return staff;
	}

	public List<User> findUsers(Specification<User> spec) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page<User> findUsers(Specification<User> spec, Pageable pageable) {
		return repository.findAll(spec, pageable);
	}

	public List<User> save(Iterable<User> staffs) {
		return repository.save(staffs);
	}

	
	public void initializeServiceData() {
		
	}
}
