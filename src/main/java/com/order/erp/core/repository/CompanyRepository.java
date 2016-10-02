package com.order.erp.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.order.erp.core.domain.Company;
import com.order.erp.core.domain.Customer;

@Repository("companyRepository")
public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {
	public Company findByLeaderId(Long leaderId);
}
