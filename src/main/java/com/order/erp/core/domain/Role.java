package com.order.erp.core.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "erp_role")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String ROlE_USER	 = "ROLE_用户";
	public static final String ROlE_CUSTOMER = "ROLE_客户";
	public static final String ROlE_CUSTOMER_CARE	 = "ROLE_客服";
	public static final String ROlE_BUYER	 = "ROLE_买手";
	public static final String ROlE_WAREHOUSE_KEEPER = "ROLE_仓库管理员";
	public static final String ROlE_ACCOUNTANT		 = "ROLE_现金会计";
	public static final String ROlE_ADMIN			= "ROLE_系统管理员";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "is_editable", unique = false, nullable = false)
	private Boolean isEditable = true;
	
	@Column(name = "is_hidden", unique = false, nullable = false)
	private Boolean isHidden = false;
	
	@Column(name = "is_system", unique = false, nullable = false)
	private Boolean isSystem = false;
	
	@Column(name = "code", unique = false, nullable = false)
	private String code;
	
	@Column(name = "rolename", unique = false, nullable = false)
	private String rolename;
	
	@JSONField(serialize = false) 
    @ManyToMany(cascade=CascadeType.REFRESH, mappedBy="roles", fetch = FetchType.LAZY)
    private List<User> users;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}

	public Boolean getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
	}

	public Boolean getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}


}
