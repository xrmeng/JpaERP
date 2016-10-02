package com.order.erp.core.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "erp_customer_tag")
public class CustomerTag implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Column(name = "tag")
	private String tag;

    @ManyToMany(cascade=CascadeType.REFRESH,mappedBy="tags")
    private List<Customer> customers;
}
