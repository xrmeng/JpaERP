/**
 * 
 */
package com.order.erp.core.service.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xrmeng
 *
 */
//@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ErpService {
	String module() default "";
	String name() default "";
	String display() default "0";
}
