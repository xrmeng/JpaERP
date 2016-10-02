package com.order.erp.core.security;

import org.springframework.security.acls.domain.BasePermission;

public class WarehousePermission extends BasePermission {

	private static final long serialVersionUID = -2527587877879101109L;

	protected WarehousePermission(int mask) {
		super(mask);
		// TODO Auto-generated constructor stub
	}
	
	protected WarehousePermission(int mask, char code) {
		super(mask, code);
		// TODO Auto-generated constructor stub
	}

}
