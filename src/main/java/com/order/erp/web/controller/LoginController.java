package com.order.erp.web.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.order.erp.core.domain.Account;
import com.order.erp.core.domain.Role;
import com.order.erp.core.domain.User;
import com.order.erp.core.service.AccountService;
import com.order.erp.core.service.RoleService;
import com.order.erp.core.service.StaffService;
import com.order.erp.web.controller.form.RegisterForm;


@Controller
public class LoginController extends BaseController{
	@Autowired
	private ServletContext sc;
	@Autowired
	private ServletContext context;
	@Autowired
	private HttpSession session;
	@Autowired
	private RoleService roleService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String loginForGet(ModelMap model) {
		return "login";
	}
	


}
