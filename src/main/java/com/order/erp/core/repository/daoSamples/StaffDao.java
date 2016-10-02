package com.order.erp.core.repository.daoSamples;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public interface StaffDao {
	public SqlRowSet findOne(Long id);
}
