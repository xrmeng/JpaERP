package com.order.erp.core.security.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@DDDD(eee="这个是借口的注解")
interface aa { }
@DDDD(eee="这个是父类的注解")
class FFF{ }
@DDDD(eee="这个是实现类的注解")
public class BBB extends FFF implements aa {
	public static void main(String[] args) {
		List<DDDD> l=new ArrayList<>();
		 Class<?>[] iii = BBB.class.getInterfaces();
		  //Class<? super BBB> cc = BBB.class.getSuperclass();
		  //while (cc!=null&&cc!=Object.class) {
			//  l.add(cc.getAnnotation(DDDD.class));
			//  cc = cc.getSuperclass();
		 // }
		//l.add(BBB.class.getAnnotation(DDDD.class));
		for (Class<?> annotatedType : iii) {
			l.add(annotatedType.getAnnotation(DDDD.class));
		}
		 l.forEach(new Consumer<DDDD>() { @Override public void accept(DDDD t) {
				// TODO Auto-generated method stub
			 System.out.println(t.eee());
			}});
		
	}
}

