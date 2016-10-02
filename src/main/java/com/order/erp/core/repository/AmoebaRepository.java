package com.order.erp.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.order.erp.core.domain.Amoeba;
import com.order.erp.core.domain.Customer;

@Repository("amoebaRepository")
public interface AmoebaRepository extends JpaRepository<Amoeba, Long>, JpaSpecificationExecutor<Amoeba> {

}
