package com.order.erp.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Service;

import com.order.erp.core.domain.User;
import com.order.erp.core.service.SecurityService;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {
	@Autowired
	private MutableAclService aclService;
	
	@Override
	public void test() {
		ObjectIdentity oi = new ObjectIdentityImpl(User.class, new Long(1));
		//Sid sid = new PrincipalSid("xrmeng");
		Sid sid = new GrantedAuthoritySid("ROLE_USER");
		Permission p = BasePermission.ADMINISTRATION;

		// Create or update the relevant ACL
		MutableAcl acl = null;
		try {
			acl = (MutableAcl) aclService.readAclById(oi);
		} catch (NotFoundException nfe) {
			acl = aclService.createAcl(oi);
		}

		// Now grant some permissions via an access control entry (ACE)
		acl.insertAce(acl.getEntries().size(), p, sid, true);
		aclService.updateAcl(acl);
	}

	
	public void initializeServiceData() {
		
	}
}
