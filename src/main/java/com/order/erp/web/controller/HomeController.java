package com.order.erp.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.order.erp.core.domain.Account;
import com.order.erp.core.domain.Role;
import com.order.erp.core.domain.User;
import com.order.erp.core.service.AccountService;
import com.order.erp.core.service.AdminService;
import com.order.erp.core.service.AmoebaService;
import com.order.erp.core.service.CompanyService;
import com.order.erp.core.service.CustomerService;
import com.order.erp.core.service.RoleService;
import com.order.erp.core.service.SecurityService;
import com.order.erp.core.service.StaffService;
import com.order.erp.plugin.security.RolesInitializer;

@Controller
@RequestMapping("")
public class HomeController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AccountService accountService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private AmoebaService amoebaService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private PasswordEncoder passwordEncoder;




	@RequestMapping("/")
	public String index(ModelMap model) {
		model.put("time", new Date());
		model.put("message", "Hello World");
		return "index";
	}

	@RequestMapping("/init")
	public String init(ModelMap model) {
		logger.debug("attempt to initialize ERP Roles.");
		RolesInitializer.init(roleService);
		logger.debug("initialized ERP Roles completed.");

		Account account = new Account();
		account.setUsername("xrmeng");
		account.setPassword(this.passwordEncoder.encode("x.r.meng"));
		account.setMobile("15232629701");
		account.setEmail("x.r.meng@qq.com");

		Set<Role> roles = new HashSet<Role>();
		roles.add(roleService.findOne(Long.valueOf(String.valueOf(1))));
		roles.add(roleService.findOne(Long.valueOf(String.valueOf(2))));

		User staff = new User();
		account.setUser(staff);
		staff.setAccount(account);
		staff.setRoles(roles);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			staffService.save(staff);
			map.put("success", true);
			map.put("message", "注册成功");
			map.put("foward", "/login");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("message", "创建帐户失败：" + e.getMessage() + ". 请联系运维人员");
		}

		return "index";
	}

	@RequestMapping("/acl")
	public String acl(ModelMap model) {
		securityService.test();
		return "index";
	}
	
	
	@RequestMapping("/is")
	public String initService(ModelMap model) {
		logger.debug("attempt to initialize ERP Services.");
		
		this.accountService.initializeServiceData();

		this.adminService.initializeServiceData();

		this.amoebaService.initializeServiceData();

		this.companyService.initializeServiceData();

		this.customerService.initializeServiceData();

		this.roleService.initializeServiceData();

		this.securityService.initializeServiceData();

		this.staffService.initializeServiceData();

		logger.debug("initialized ERP Services completed.");
		return "index";
	}
	
}
