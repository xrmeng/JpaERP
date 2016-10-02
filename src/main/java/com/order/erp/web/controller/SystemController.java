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
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.order.erp.core.domain.Account;
import com.order.erp.core.domain.Amoeba;
import com.order.erp.core.domain.Company;
import com.order.erp.core.domain.Role;
import com.order.erp.core.domain.User;
import com.order.erp.core.service.AccountService;
import com.order.erp.core.service.AdminService;
import com.order.erp.core.service.AmoebaService;
import com.order.erp.core.service.CompanyService;
import com.order.erp.core.service.RoleService;
import com.order.erp.core.service.StaffService;
import com.order.erp.web.controller.form.RegisterForm;


@Controller
@RequestMapping("/erp/system/")
@PreAuthorize("hasRole('"+Role.ROlE_ADMIN+"')")
public class SystemController extends BaseController {
	@Autowired
	AccountService accountService;
	@Autowired
	StaffService staffService;
	@Autowired
	AmoebaService amoebaService;
	@Autowired
	RoleService roleService;
	@Autowired
	CompanyService companyService;
	@Autowired
	AdminService adminService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(path = "", method = RequestMethod.GET)
	public String index(ModelMap model) {
		return "/system/index";
	}

	@RequestMapping(path = "staff", method = RequestMethod.GET)
	public String staffIndex(HttpServletRequest request, 
						@RequestParam(name="keyword", required=false) String keyword, 
						@PageableDefault(page=0, size=10, sort="id", direction=Direction.ASC) Pageable pageable, 
						ModelMap model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User me = staffService.findByAccountUsername(((UserDetails) principal).getUsername());
		Company company = me.getCompany();
		//List<User> userList = userService.findUsersByAmoebaId(company.getId());

		final User meStaff = me;
		final String finalKeyword = keyword;
		final Company finalCompany = company;
		Page<User> page = staffService.findUsers(new Specification<User>() {
	        public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	        	query.distinct(true);
	        	Predicate p1 = cb.equal(root.get("company").as(Company.class), finalCompany);	        	
	        	Predicate p2 = cb.conjunction();
	            if (StringUtils.isNotBlank(finalKeyword)) {
		        	Predicate p1_like = cb.like(root.get("account").get("username").as(String.class), "%" + finalKeyword + "%");
		        	Predicate p2_like = cb.like(root.get("realname").as(String.class), "%" + finalKeyword + "%");
		        	p2 = cb.or(p1_like, p2_like);
		        }
	            Predicate p3 = cb.notEqual(root.as(User.class), meStaff);
	            Predicate p_result = cb.and(p1, p2, p3);
	            return p_result;
	        }
	    },pageable);

		model.put("user", me);
		model.put("company", company);
		model.put("page", page);
		return "/system/staff_list";
	}
	
	


	@RequestMapping(path = "staff/create", method = RequestMethod.GET)
	public String createStaffForGet(ModelMap model) {
		return "/system/staff_create";
	}

	@RequestMapping(path = "staff/create", method = RequestMethod.POST)
	public String createStaffForPost(HttpServletRequest request, @Valid RegisterForm data, BindingResult br,
			ModelMap model) {
		System.out.println(JSON.toJSONString(data));
		if (br.hasErrors()) {
			List<ObjectError> errorList = br.getAllErrors();
			if (errorList != null && errorList.size() >= 1) {
				ObjectError error = errorList.get(0);
				request.setAttribute("error", error.getDefaultMessage());
			}
			model.put("data", data);
			return "/system/staff_create";
		}
		Account account = accountService.findByUsername(data.getRegAccount());
		if (account != null) {
			request.setAttribute("error", "帐号已存在");
			model.put("data", data);
			return "/system/staff_create";
		}
		if (!(data.getRegPassword()).equals(data.getRepeatPassword())) {
			request.setAttribute("error", "两次输入的密码不一致");
			model.put("data", data);
			return "/system/staff_create";
		}
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User me = staffService.findByAccountUsername(((UserDetails) principal).getUsername());
		Company company = me.getCompany();
		
		User newStaff = new User();
		Account newAccount = new Account();
		newAccount.setUsername(data.getRegAccount());
		newAccount.setPassword(this.passwordEncoder.encode(data.getRegPassword()));
		newAccount.setUser(newStaff);

		newStaff.setAccount(newAccount);
		newStaff.setMobile(data.getRegMobile());
		newStaff.setEmail(data.getRegEmail());
		newStaff.setCompany(company);
		Set<Role> roles = new HashSet<Role>();
		roles.add(roleService.findByRolename(Role.ROlE_USER));
		newStaff.setRoles(roles);

		try {
			newStaff = staffService.save(newStaff);
			return "redirect:/erp/system/staff";
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			model.put("data", data);
			return "/system/staff_create";
		}
	}

	/*
	@RequestMapping(path = "staff/view/{id}", method = RequestMethod.GET)
	public String viewStaff(@PathVariable("id") User user, ModelMap model) {
		model.addAttribute("user", user);
		return "/system/staff_view";
	}
	
	@RequestMapping(path = "staff/edit/{id}", method = RequestMethod.GET)
	public String editStaffForm(@PathVariable("id") Staff staff, ModelMap model) {
		model.addAttribute("staff", staff);
		return "/system/staff_edit_form";
	}
	*/
	
	
	@RequestMapping(path = "amoeba", method = RequestMethod.GET)
	public String amoebaIndex(HttpServletRequest request, 
						@RequestParam(name="keyword", required=false) String keyword, 
						@PageableDefault(page=0, size=10, sort="id", direction=Direction.ASC) Pageable pageable, 
						ModelMap model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User me = staffService.findByAccountUsername(((UserDetails) principal).getUsername());
		Company company = me.getCompany();

		final String finalKeyword = keyword;
		final Company finalCompany = company;
		Page<Amoeba> page = adminService.findAmoebas(new Specification<Amoeba>() {
	        public Predicate toPredicate(Root<Amoeba> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	        	query.distinct(true);
	        	Predicate p1 = cb.equal(root.get("company").as(Company.class), finalCompany);	        	
	        	Predicate p2 = cb.conjunction();
	            if (StringUtils.isNotBlank(finalKeyword)) {
		        	p2 = cb.like(root.get("name").as(String.class), "%" + finalKeyword + "%");
		        }
	            Predicate p_result = cb.and(p1, p2);
	            return p_result;
	        }
	    },pageable);

		model.put("user", me);
		model.put("company", company);
		model.put("page", page);
		String json = JSON.toJSONString(page, SerializerFeature.PrettyFormat);
		System.out.println(json);
		return "/system/amoeba_list";
	}
	
	@RequestMapping(path = "amoeba/create", method = RequestMethod.GET)
	public String createAmoebaForGet(ModelMap model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User me = staffService.findByAccountUsername(((UserDetails) principal).getUsername());
		Company company = me.getCompany();
		List<User> staffs = companyService.findStaffsByCompanyId(company.getId());
		model.put("user", me);
		model.put("staffs", staffs);
		return "/system/amoeba_create";
	}

	@RequestMapping(path = "amoeba/create", method = RequestMethod.POST)
	public String createAmoebaForPost(HttpServletRequest request, @Valid Amoeba data, BindingResult br,
			ModelMap model) {
		System.out.println(JSON.toJSONString(data));

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User me = staffService.findByAccountUsername(((UserDetails) principal).getUsername());
		Company company = me.getCompany();

		/*
		staff leader = staffService.findOne(data.get)
		Amoeba amoeba = new Amoeba();
		amoeba.setCompany(company);
		
		Account newAccount = new Account();
		newAccount.setUsername(data.getRegAccount());
		newAccount.setPassword(this.passwordEncoder.encode(data.getRegPassword()));
		newAccount.setStaff(newStaff);

		newStaff.setAccount(newAccount);
		newStaff.setMobile(data.getRegMobile());
		newStaff.setEmail(data.getRegEmail());
		newStaff.setCompany(company);
		Set<Role> roles = new HashSet<Role>();
		roles.add(roleService.findByRolename(Role.ROlE_USER));
		roles.add(roleService.findByRolename(Role.ROlE_STAFF));
		newStaff.setRoles(roles);

		try {
			newStaff = staffService.save(newStaff);
			return "redirect:/erp/system/amoeba";
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			model.put("data", data);
			return "/system/staff_create";
		}
		*/
		
		return "redirect:/erp/system/amoeba";
	}
	
	
}
