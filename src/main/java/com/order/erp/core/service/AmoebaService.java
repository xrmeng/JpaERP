package com.order.erp.core.service;

import com.order.erp.core.domain.Amoeba;
import com.order.erp.core.domain.Company;
import com.order.erp.core.service.annotation.ErpService;

@ErpService()
public interface AmoebaService extends _BaseService {
	public Amoeba save(Amoeba amoeba); 


}