package org.akhil.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TxService {
	
	//Aspect is (TxService) class provides additional services to project
	//Advice is cross cutting methods inside Aspect class (like beginTx() and commitTx())
		// ************Types of Advices
		// Before : executed before method
		// After : executed after method
		// Around : executed before method
		// After returning : Advice code made in 2 parts/sections, 1st part executed before 
							//	then b.method later 2nd part executed 
		// After throwing : executed after method , if b.method throws any exception
	//PointCut is selecting the business methods which need Advice
	
	@Pointcut("execution(public void org.akhil.dao.EmployeeDao.saveEmployee())")
	public void p1() {
	}
	
	@Pointcut("execution(public String org.akhil.dao.EmployeeDao.getEmployee())")
	public void p2() {
	}
	
	@Before("p1()") //called JoinPoint
	public void beginTx() {
		System.out.println("Tx started ..");
	}
	
	//@After("p1()") //called JoinPoint
	//@AfterReturning("p1()")
	@AfterReturning(value = "p2()", returning = "ob")
	public void commitTx(Object ob) {
		System.out.println("Tx committed !!" + ob.toString());
	}
	
	//@AfterThrowing("p1()")
	//@AfterThrowing(value = "p1()", throwing = "th")
	public void rollbackTx(Throwable th) {
		System.out.println("Tx rollback "+ th.getMessage());
		
	}
	
	@After("p1()")
	public void sendReport() {
		System.out.println("Report sent !");
	}
	
	@Around("p2()")
	public void aroundTest(ProceedingJoinPoint jp) {
		System.out.println(" Before business method");
		try {
			Object ob=jp.proceed();
			System.out.println("Data is : "+ ob);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		System.out.println(" After business method");
	}
	
}
