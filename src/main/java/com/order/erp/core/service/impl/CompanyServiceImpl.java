package com.order.erp.core.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.order.erp.core.domain.User;
import com.order.erp.core.repository.CompanyRepository;
import com.order.erp.core.repository.StaffRepository;
import com.order.erp.core.service.CompanyService;

@Service("companyService")
public class CompanyServiceImpl extends _BaseServiceImpl implements CompanyService {
	private EntityManager entityManager;
	@Autowired
	private CompanyRepository repository;
	@Autowired
	private StaffRepository staffRepository;

	public List<User> findStaffsByCompanyId(Long companyId) {
		final Long finalCompanyId = companyId;
		List<User> page = staffRepository.findAll(new Specification<User>() {
	        public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	        	query.distinct(true);
	        	Predicate p1 = cb.equal(root.get("company").get("id").as(Long.class), finalCompanyId);
	            return p1;
	        }
	    });
		/*
		Company company = repository.findOne(companyId);
		List<Staff> staffs = null;
		try{
			staffs = company.getStaffs();
			staffs.size();
		}catch(Exception e){
			logger.debug("try to load lazy property for account, get null object.");
		}
		*/
		return page;
	}



	public void initializeServiceData() {
		
	}


}
