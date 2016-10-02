package com.order.erp.core.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name = "erp_user")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@OneToOne(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE }, mappedBy ="user")
	private Account account;
	
	@Column(name = "realname")
	private String realname;
	
	@Column(name = "enabled", nullable = false)
	private Boolean enabled = true;
	
	@Column(name = "account_non_expired", nullable = false)
	private Boolean accountNonExpired = true;
	
	@Column(name = "credentials_non_expired", nullable = false)
	private Boolean credentialsNonExpired = true;
	
	@Column(name = "account_non_locked", nullable = false)
	private Boolean accountNonLocked = true;

	@Column(name = "mobile")
	private String mobile;

	@Column(name = "wechat")
	private String wechat;

	@Column(name = "wangwang")
	private String wangwang;

	@Column(name = "qq")
	private String qq;

	@Column(name = "email")
	private String email;
	
	@JSONField(serialize = false) 
	@ManyToOne(cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
	@JoinColumn(name="amoeba_id", nullable = true)
	private Amoeba amoeba;
	
	@JSONField(serialize = false) 
	@ManyToOne(cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
	@JoinColumn(name="company", nullable = true)
	private Company company;
	
	@OneToOne(mappedBy = "leader", fetch = FetchType.EAGER)
	private Amoeba leadedAmoeba;
	
    /**
     * JoinTable表示中间表
     * inverseJoinColumns表示关系被维护端对应的中间表的外键名
     * joinColumns表示关系维护端对应的中间表的外键名
     * @return
     */
    @ManyToMany(cascade={CascadeType.REFRESH}, fetch=FetchType.EAGER)
    @JoinTable(name="erp_user_role_relation",
            inverseJoinColumns=@JoinColumn(name="role_id"), 
            joinColumns=@JoinColumn(name="user_id"))
    private Set<Role> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Boolean getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getWangwang() {
		return wangwang;
	}

	public void setWangwang(String wangwang) {
		this.wangwang = wangwang;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Amoeba getAmoeba() {
		return amoeba;
	}

	public void setAmoeba(Amoeba amoeba) {
		this.amoeba = amoeba;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Amoeba getLeadedAmoeba() {
		return leadedAmoeba;
	}

	public void setLeadedAmoeba(Amoeba leadedAmoeba) {
		this.leadedAmoeba = leadedAmoeba;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
