package com.order.erp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "erp_product")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@JoinColumn(name = "brand", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.EAGER)
	private Brand brand;
    
	@JoinColumn(name = "category", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.EAGER)
	private ProductCategory category;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}
	
    
}
