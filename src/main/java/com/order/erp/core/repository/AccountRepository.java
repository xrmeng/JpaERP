package com.order.erp.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.order.erp.core.domain.Account;

@Repository("accountRepository")
public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor {
	public Account findByUsername(String username);
}
