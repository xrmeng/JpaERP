package com.order.erp.plugin.security;

import com.order.erp.core.domain.Role;
import com.order.erp.core.service.RoleService;

public final class RolesInitializer {
	
	public static void init(RoleService roleService){
		Role role = null;
		
		role = new Role();
		role.setRolename("用户");
		role.setCode("ROLE_USER");
		role.setIsEditable(false);
		role.setIsSystem(true);
		role.setIsHidden(true);
		roleService.save(role); 
		
		role = new Role();
		role.setRolename("系统管理员");
		role.setCode("ROLE_SYSTEM");
		role.setIsEditable(false);
		role.setIsSystem(true);
		role.setIsHidden(true);
		roleService.save(role); 
		
		role = new Role();
		role.setRolename("管理员");
		role.setCode("ROLE_ADMIN");
		role.setIsEditable(false);
		role.setIsSystem(true);
		role.setIsHidden(false);
		roleService.save(role); 
		
		role = new Role();
		role.setRolename("店长");
		role.setCode("ROLE_MANAGER");
		role.setIsEditable(false);
		role.setIsSystem(true);
		role.setIsHidden(false);
		roleService.save(role); 
		
		role = new Role();
		role.setRolename("客服");
		role.setCode("ROLE_SERVICE");
		role.setIsEditable(false);
		role.setIsSystem(true);
		role.setIsHidden(false);
		roleService.save(role); 

		role = new Role();
		role.setRolename("财务");
		role.setCode("ROLE_ACCOUNTANT");
		role.setIsEditable(false);
		role.setIsSystem(true);
		role.setIsHidden(false);
		roleService.save(role); 
		
		role = new Role();
		role.setRolename("库管");
		role.setCode("ROLE_WHAREHOUSE");
		role.setIsEditable(false);
		role.setIsSystem(true);
		role.setIsHidden(false);
		roleService.save(role); 
		
		role = new Role();
		role.setRolename("客户");
		role.setCode("ROLE_CUSTOMER");
		role.setIsEditable(false);
		role.setIsSystem(true);
		role.setIsHidden(false);
		roleService.save(role); 
		
	}
	
	public static String getBaseRoleName(){
		return Role.ROlE_USER;
	}
}
