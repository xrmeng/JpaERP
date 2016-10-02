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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.order.erp.core.domain.Account;
import com.order.erp.core.domain.Role;
import com.order.erp.core.domain.User;
import com.order.erp.core.service.AccountService;
import com.order.erp.core.service.RoleService;
import com.order.erp.core.service.StaffService;
import com.order.erp.plugin.security.RolesInitializer;
import com.order.erp.web.controller.form.RegisterForm;


@Controller
public class RegisterController extends BaseController{
	
	private String baseRoleName = RolesInitializer.getBaseRoleName(); 
	
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


	@RequestMapping(path = "/register", method = RequestMethod.GET)
	public String registerForget(ModelMap model) {
		return "register";
	}

	@ResponseBody
	@RequestMapping(path = "/register", method = RequestMethod.POST)
	public Map<String, Object> registerForPost(@Valid RegisterForm data, BindingResult br, ModelMap model) {
		String c = (String)session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		Map<String, Object> map = new HashMap<String, Object>();
		if((c==null)||(data.getRegCaptcha()==null)||(!data.getRegCaptcha().equalsIgnoreCase(c))){
    		map.put("success", false);
    		map.put("message", "验证码错误");
    		return map;
		}
	    if (br.hasErrors()) {
	    	List<ObjectError> errorList = br.getAllErrors();
	    	if(errorList != null && errorList.size() >= 1){
	    		ObjectError error = errorList.get(0);
	    		System.out.println(error);
	    		map.put("success", false);
	    		map.put("message", error.getDefaultMessage());
	    		return map;
	    	}
	    }
		if(!data.getRegPassword().equals(data.getRepeatPassword())){
    		map.put("success", false);
    		map.put("message", "两次输入的密码不一致");
    		return map;
		}
		
		Account acc = accountService.findByUsername(data.getRegAccount());
		if(acc!=null){
    		map.put("success", false);
    		map.put("message", "帐号已存在");
    		return map;
		}

		Account account = new Account();
		account.setUsername(data.getRegAccount());
		account.setPassword(this.passwordEncoder.encode(data.getRegPassword()));
		account.setMobile(data.getRegMobile());
		account.setEmail(data.getRegEmail());
		
		Set<Role> roles = new HashSet<Role>();
		roles.add(roleService.findByRolename(Role.ROlE_USER));

		User staff = new User();
		account.setUser(staff);
		staff.setAccount(account);
		staff.setRoles(roles);
		try{
			staffService.save(staff);
			map.put("success", true);
			map.put("message", "注册成功");	
			map.put("object", data);
			map.put("foward", "/login");
			return map;
		}catch(Exception e){
			e.printStackTrace();
    		map.put("success", false);
    		map.put("message", "创建帐户失败：" + e.getMessage() + ". 请联系运维人员");
    		return map;
		}
	}
	
}
