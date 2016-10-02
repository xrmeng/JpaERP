package com.order.erp.web.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.order.erp.core.service.RoleService;
import com.order.erp.plugin.security.RolesInitializer;

//@WebFilter("/*")
@Component
public class LogFilter implements Filter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private RoleService roleService;

	/**
	 * Default constructor.
	 */
	public LogFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub1
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		/*
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		System.out.println("doFilter..." + httpRequest.getRequestURI());
		Enumeration<String> enu = httpRequest.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			System.out.println(paraName + ": " + httpRequest.getParameter(paraName));
		}
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		System.out.println("doFilter..." + httpResponse);
		*/
		// pass the request along the filter chain
		chain.doFilter(request, response);
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		logger.debug("attempt to initialize ERP Roles.");
		RolesInitializer.init(roleService);
		logger.debug("initialized ERP Roles completed.");
	}

}
