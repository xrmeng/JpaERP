package com.order.erp.core.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "erp_product_category")
public class ProductCategory implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "type")
	private String type;

	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	private List<Product> productList;
	
	
}
