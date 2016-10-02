/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.order.erp.web.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.order.erp.core.domain.Account;
import com.order.erp.core.domain.Company;
import com.order.erp.core.domain.Role;
import com.order.erp.core.domain.User;
import com.order.erp.core.service.AccountService;
import com.order.erp.core.service.AmoebaService;
import com.order.erp.core.service.RoleService;
import com.order.erp.core.service.StaffService;

@Controller
@PreAuthorize("hasRole('"+Role.ROlE_USER+"')")
@RequestMapping("/profile")
public class ProfileController extends BaseController {
	@Autowired
	AccountService accountService;
	@Autowired
	StaffService staffService;
	@Autowired
	AmoebaService amoebaService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(path = "", method = RequestMethod.GET)
	public String index(ModelMap model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
		} else {
			String username = principal.toString();
		}
		return "/profile/index";
	}

	@RequestMapping(path = "changePassword", method = RequestMethod.GET)
	public String changePasswordForGet(ModelMap model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = accountService.findByUsername(((UserDetails) principal).getUsername());
		model.put("account", account);
		return "/profile/changePassword";
	}
	@RequestMapping(path = "changePassword", method = RequestMethod.POST)
	public String changePasswordForPost(@RequestParam("password") String password, @RequestParam("rePassword") String rePassword, ModelMap model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = accountService.findByUsername(((UserDetails) principal).getUsername());
		if((password.length()<6) || (password.length()>50)){
			String error = "密码必须在 6-50 位 之间";
			model.put("error", error);
			return "/error";
		}
		if(!password.equals(rePassword)){
			String error = "两次输入 的密码不一致";
			model.put("error", error);
			return "/error";
		}
		account.setPassword(this.passwordEncoder.encode(password));
		accountService.save(account);
		return "redirect:/erp/profile";
	}
	
	

	@RequestMapping(path = "completeInfo", method = RequestMethod.GET)
	public String completeInfoForGet(ModelMap model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = accountService.findByUsername(((UserDetails) principal).getUsername());
		User staff = account.getUser();
		model.put("user", staff);
		return "/profile/completeInfo";
	}
	@RequestMapping(path = "completeInfo", method = RequestMethod.POST)
	public String completeInfoForPost(User data, ModelMap model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = accountService.findByUsername(((UserDetails) principal).getUsername());
		User staff = account.getUser();
		staff.setRealname(data.getRealname());
		staff.setMobile(data.getMobile());
		staff.setQq(data.getQq());
		staff.setWechat(data.getWechat());
		staff.setWangwang(data.getWangwang());
		staff.setEmail(data.getEmail());
		staffService.save(staff);
		return "redirect:/erp/profile/completeInfo";
	}
	

	@RequestMapping(path = "registerCompany", method = RequestMethod.GET)
	public String registerCompanyForGet(ModelMap model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = accountService.findByUsername(((UserDetails) principal).getUsername());
		User staff = account.getUser();
		Company company = staff.getCompany();
		model.put("user", staff);
		model.put("company", company);
		return "/profile/registerCompany";
	}
	@RequestMapping(path = "registerCompany", method = RequestMethod.POST)
	public String registerCompanyForPost(Company data, ModelMap model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
		Account account = accountService.findByUsername(((UserDetails) principal).getUsername());
		User staff = account.getUser();
		if(!account.getUsername().equals("xrmeng")){
			String error = "权限不足!";
			model.put("error", error);
			return "/error";
		}
		try{
			Company company = staff.getCompany();
			if(company==null){
				company = new Company();
			}
			company.setLeader(staff);
			company.setName(data.getName());
			company.setShortName(data.getShortName());
			
			Company nc = staffService.createCompany(company);
			if(nc!=null){
				staff.setCompany(nc);
				Set<Role> roles = new HashSet<Role>();
				roles.add(roleService.findByRolename(Role.ROlE_CUSTOMER_CARE));
				roles.add(roleService.findByRolename(Role.ROlE_BUYER));
				roles.add(roleService.findByRolename(Role.ROlE_WAREHOUSE_KEEPER));
				roles.add(roleService.findByRolename(Role.ROlE_ACCOUNTANT));
				roles.add(roleService.findByRolename(Role.ROlE_ADMIN));
				staff.getRoles().addAll(roles);
				staffService.save(staff);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:/erp/profile/registerCompany";
	}
	
}
