package com.order.erp.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.order.erp.core.domain.Brand;
import com.order.erp.core.domain.Customer;

@Repository("brandRepository")
public interface BrandRepository extends JpaRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {

	@Query("SELECT b FROM Brand b WHERE b.name LIKE %:keystring%")
	public List<Brand> findAllBrandContaining(@Param("keystring") String keyString);

	@Query("SELECT b FROM Brand b WHERE b.id LIKE %:keylong% OR b.name LIKE %:keystring%")
	public List<Brand> findAllBrandContaining(@Param("keylong") Long keyLong, @Param("keystring") String keyString);

}
