package com.order.erp.core.repository.daoSamples.impl;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import com.order.erp.core.repository.daoSamples.BaseDao;

@Component
public class BaseDaoImpl extends JdbcDaoSupport implements InitializingBean, BaseDao {
	@Autowired
	private DataSource dataSource;
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
    
    /*
从页面上传过来的参数不是存放在request中吗? 
之后得到request.getParameterMap(),因为你这里是dao层,所以建议弄成一个普通的Map,调用putAll,将参数传进来 
之后调用for循环,拼接sql语句 
     */
    
    
    
	@PostConstruct
	public void init() {
		super.setDataSource(this.dataSource);
		super.setJdbcTemplate(new JdbcTemplate(this.getDataSource()));
		this.namedParameterJdbcTemplate= new NamedParameterJdbcTemplate(this.getDataSource());
	}

	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
}