package com.order.erp.web.controller.form;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class ProfileForm implements Serializable {
	
	@NotBlank(message="帐号不能为空")
    @Size(min=5, max=50, message="帐号长度在5-50字符之间")
	@Pattern(regexp="[a-z|A-Z]+[0-9|_]*", message="帐号必须以字母开始，其它位置可以是数字或下划线")
	private String regAccount;
	private String regMobile;
	//@NotBlank(message="必须填写邮件地址")
	//@Email
	private String regEmail;
	private String regName;
	@NotBlank(message="密码不能为空")
    @Size(min=6, max=50, message="密码长度在6-50字符之间")
	private String regPassword;
	private String repeatPassword;
	private String regCaptcha;
	
	public String getRegAccount() {
		return regAccount;
	}
	public void setRegAccount(String regAccount) {
		this.regAccount = regAccount;
	}
	public String getRegMobile() {
		return regMobile;
	}
	public void setRegMobile(String regMobile) {
		this.regMobile = regMobile;
	}
	public String getRegEmail() {
		return regEmail;
	}
	public void setRegEmail(String regEmail) {
		this.regEmail = regEmail;
	}
	public String getRegName() {
		return regName;
	}
	public void setRegName(String regName) {
		this.regName = regName;
	}
	public String getRegPassword() {
		return regPassword;
	}
	public void setRegPassword(String regPassword) {
		this.regPassword = regPassword;
	}
	public String getRepeatPassword() {
		return repeatPassword;
	}
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
	public String getRegCaptcha() {
		return regCaptcha;
	}
	public void setRegCaptcha(String regCaptcha) {
		this.regCaptcha = regCaptcha;
	}
	
	
}
