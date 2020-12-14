package org.akhil.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TxServiceAspectAnnotationBased {

	@Pointcut("@annotation(org.akhil.anno.MyTxAnno)")
	public void p1() {
		
	}
	
	@Before("p1()")
	public void beginTx() {
		System.out.println("Begin Aspect Anno Tx ... !! ");
	}
}
