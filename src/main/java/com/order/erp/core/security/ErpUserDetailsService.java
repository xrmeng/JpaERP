package com.order.erp.core.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.security.access.intercept.aopalliance.MethodSecurityInterceptor;
import org.springframework.security.acls.domain.EhCacheBasedAclCache;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import com.order.erp.core.domain.Account;
import com.order.erp.core.domain.Role;
import com.order.erp.core.domain.User;
import com.order.erp.core.service.AccountService;

import net.sf.ehcache.CacheManager;

//@Component("userDetailsService")
public class ErpUserDetailsService implements UserDetailsService {
	@Autowired
	private AccountService accountService;
	private PersistentTokenBasedRememberMeServices p;
	private RememberMeAuthenticationFilter r;
	private TokenBasedRememberMeServices t;
	private RememberMeAuthenticationProvider rp;
	private EhCacheFactoryBean efb;
	private EhCacheBasedAclCache aclCache;
	private CacheManager cm;
	private MethodSecurityInterceptor msi;
	private GrantedAuthority ga;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountService.findByUsername(username);
		if(account == null){
			//System.out.println("帐号： "+username+" 不存在");
			throw new UsernameNotFoundException("帐号： "+username+" 不存在");
		}
		/*
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        */
		User user = account.getUser();
		if(user == null){
			throw new UsernameNotFoundException("帐号： "+username+" 不存在");
		}
		Set<Role> roles = user.getRoles();
/*
		if (null == roles || roles.isEmpty())  
			throw new UsernameNotFoundException("权限不足!"); 
*/
		
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();  
		for (Role role : roles) {  
			authorities.add(new SimpleGrantedAuthority(role.getCode()));  
		}
		
		Boolean enabled = account.getEnabled() && user.getEnabled();
		Boolean accountNonExpired = account.getAccountNonExpired() && user.getAccountNonExpired();
		Boolean credentialsNonExpired = account.getCredentialsNonExpired() && user.getCredentialsNonExpired();
		Boolean accountNonLocked = account.getAccountNonLocked() && user.getAccountNonLocked();
		org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		
		//org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(), authorities);
		//System.out.println(account.getUsername() + " - " + account.getPassword() + " - " + enabled + " - " + accountNonExpired + " - " + credentialsNonExpired + " - " + accountNonLocked + " - " + authorities);
		return userDetails;
	}

}
