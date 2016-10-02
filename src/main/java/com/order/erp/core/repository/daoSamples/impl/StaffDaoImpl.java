package com.order.erp.core.repository.daoSamples.impl;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.order.erp.core.repository.daoSamples.StaffDao;

@Repository("staffDao")
public class StaffDaoImpl extends BaseDaoImpl implements StaffDao  {

	public SqlRowSet findOne(Long id) {
		String sql = "select * from erp_user u where u.id = ?";
		Object args[] = {1};  
		SqlRowSet rs = this.getJdbcTemplate().queryForRowSet(sql, args);
		while (rs.next()) {  
		    System.out.println(rs.getObject("account"));  
		}  
		return rs;
		
	}
}