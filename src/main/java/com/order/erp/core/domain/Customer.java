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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "erp_customer")
public class Customer implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "level")
	private Integer level;
	@Column(name = "reference")
	private String reference;
	@Column(name = "tel")
	private String tel;
	@Column(name = "mobile")
	private String mobile;
	@Column(name = "wechat")
	private String wechat;
	@Column(name = "qq")
	private String qq;
	@Column(name = "wangwang")
	private String wangwang;
	@Column(name = "memo")
	private String memo;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", referencedColumnName = "id")
	private CustomerCategory category;
    /**
     * JoinTable表示中间表
     * inverseJoinColumns表示关系被维护端对应的中间表的外键名
     * joinColumns表示关系维护端对应的中间表的外键名
     * @return
     */
    @ManyToMany(cascade={CascadeType.REFRESH})
    @JoinTable(name="erp_customer_tag_relation",
            inverseJoinColumns=@JoinColumn(name="customer_id"),
            joinColumns=@JoinColumn(name="tag_id"))
    private List<CustomerTag> tags;
    

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
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
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getWangwang() {
		return wangwang;
	}
	public void setWangwang(String wangwang) {
		this.wangwang = wangwang;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public CustomerCategory getCategory() {
		return category;
	}
	public void setCategory(CustomerCategory category) {
		this.category = category;
	}
	public List<CustomerTag> getTags() {
		return tags;
	}
	public void setTags(List<CustomerTag> tags) {
		this.tags = tags;
	}

}
