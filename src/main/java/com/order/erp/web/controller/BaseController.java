package com.order.erp.web.controller;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController {
	@Autowired
	protected static EntityManager entityManager;
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

}
