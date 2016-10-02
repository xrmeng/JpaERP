package com.order.erp.core.security;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.order.erp.core.security.domain.SystemResource;
import com.order.erp.core.security.repository.SystemResourceRepository;
import com.order.erp.core.service.annotation.ErpService;
import com.order.erp.core.service.annotation.ErpServiceMethod;

@Component
@Aspect
public class ServiceAspect {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SystemResourceRepository systemResourceRepository;


	// 配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
	@Pointcut("execution(public * com.order.erp.core.service.*Service.*(..))")
	public void aspect() {
	}

	/*
	 * 配置前置通知,使用在方法aspect()上注册的切入点 同时接受JoinPoint切入点对象,可以没有该参数
	 */
	
	
	/*
	@Before("aspect()")
	public void before(JoinPoint joinPoint) {
		if (logger.isInfoEnabled()) {
			logger.info("before " + joinPoint);
		}
	}

	// 配置后置通知,使用在方法aspect()上注册的切入点
	@After("aspect()")
	public void after(JoinPoint joinPoint) {
		if (logger.isInfoEnabled()) {
			logger.info("after " + joinPoint);
		}
	}
	*/

	// 配置环绕通知,使用在方法aspect()上注册的切入点
	@Around("aspect()")
	public Object around(JoinPoint joinPoint) {

		/*
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        System.out.print("执行的类"+joinPoint.getTarget().getClass().getSimpleName()+"\n");
        System.out.print("执行的方法" + signature.getMethod().getName()+"\n");
        */

		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		if(signature.getMethod().getName().equals("initializeServiceData")){
			this.initializeServiceData(joinPoint);
		}
		
		Object result = null;  
		long start = System.currentTimeMillis();
		try {
			result = ((ProceedingJoinPoint) joinPoint).proceed();
			long end = System.currentTimeMillis();
			if (logger.isInfoEnabled()) {
				logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
			}
		} catch (Throwable e) {
			long end = System.currentTimeMillis();
			if (logger.isInfoEnabled()) {
				logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : "
						+ e.getMessage());
			}
		}
		return result;
	}

	/*
	// 配置后置返回通知,使用在方法aspect()上注册的切入点
	@AfterReturning("aspect()")
	public void afterReturn(JoinPoint joinPoint) {
		if (logger.isInfoEnabled()) {
			logger.info("afterReturn " + joinPoint);
		}
	}

	// 配置抛出异常后通知,使用在方法aspect()上注册的切入点
	@AfterThrowing(pointcut = "aspect()", throwing = "ex")
	public void afterThrow(JoinPoint joinPoint, Exception ex) {
		if (logger.isInfoEnabled()) {
			logger.info("afterThrow " + joinPoint + "\t" + ex.getMessage());
		}
	}
	*/
	
	
	private Class<?> getServiceInterface(JoinPoint joinPoint){
		Class<?>[] ins = joinPoint.getTarget().getClass().getInterfaces();
		for (Class<?> in : ins) {
			ErpService a = in.getAnnotation(ErpService.class);
			if( a != null){
				return in;
			}
		}
		return null;
	}
	private ErpService getServiceAnnotation(JoinPoint joinPoint){
		Class<?>[] ins = joinPoint.getTarget().getClass().getInterfaces();
		for (Class<?> in : ins) {
			ErpService a = in.getAnnotation(ErpService.class);
			if( a != null){
				return a;
			}
		}
		return null;
	}
	private ErpServiceMethod getServiceMethodAnnotation(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        String methedName = signature.getMethod().getName();
		Class<?>[] ins = joinPoint.getTarget().getClass().getInterfaces();
		for (Class<?> in : ins) {
			ErpService a = in.getAnnotation(ErpService.class);
			if( a != null){
				Method[] ms = in.getMethods();
				for (Method m : ms) {
					System.out.print(m.getName()+" : _ : "+methedName+"\n");	
					if(m.getName().equals(methedName)){
						System.out.print("equals:"+m.getName()+" : _ : "+methedName+"\n");	
						ErpServiceMethod ma = m.getDeclaredAnnotation(ErpServiceMethod.class);
						if(ma!=null){
							System.out.print("ma:"+ma.name()+"\n");
						}
						return ma;
					}
				}
			}
		}
		return null;
	}
	
	
	private void initializeServiceData(JoinPoint joinPoint){
		Class<?> in = this.getServiceInterface(joinPoint);
		System.out.println(in.getName()+"++++++++++++++++++++");
		Method[] ms = in.getMethods();
		for (Method m : ms) {
			System.out.println("method:"+in.getName()+"."+m.getName());
			SystemResource sr = new SystemResource();
			sr.setPath(in.getName()+"."+m.getName());
			sr.setModule(this.getServiceAnnotation(joinPoint).module());
			sr.setType("DomainService");
			if(this.getServiceMethodAnnotation(joinPoint)!=null){
				sr.setTitle(this.getServiceMethodAnnotation(joinPoint).name());
				sr.setDisplay(Boolean.valueOf((this.getServiceMethodAnnotation(joinPoint).display())));
			}else{
				sr.setTitle(" - ");
				sr.setDisplay(false);
			}
			
			try{
				this.systemResourceRepository.save(sr);
			}catch(Exception e){
				e.printStackTrace();
			}
			Parameter[] ps = m.getParameters();
			for (Parameter p : ps ){
				System.out.println("parameter:"+p.getName());
			}
		}
	}
	
}











/*
基于@Aspectj使用Spring aop @Around进行权限拦截  
http://zhanghua.1199.blog.163.com/blog/static/464498072011111393634448/
http://blog.csdn.net/a9529lty/article/details/7031070
http://www.android100.org/html/201503/30/131452.html
*/
	
