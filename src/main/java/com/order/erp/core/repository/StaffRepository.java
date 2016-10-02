package com.order.erp.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.order.erp.core.domain.User;

@Repository("staffRepository")
public interface StaffRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor {
	public User getStaffByAccount(String account);
}
