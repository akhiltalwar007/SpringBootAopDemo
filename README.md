# SpringBootAopDemo
AOP Springboot concepts explained and executed using different aspects, pointcuts. Also used mostly used @annotation pointcut by creating own Annotation.


				Spring Boot AOP				
				
			AOP : Aspect Oriented Programming

*) Cross-Cutting Concern: Move Additional Services of a Project into other classes
   [Services class/Aspect classes] and bind when and where they are required.


1) Aspect   : Aspect is a class, that provides additional services to project.
		Transaction Management, Logging, Security, Encode and Decode..etc.

2) Advice   : It is a method inside Aspect class. [It is actual implementation of Aspect]

Types of Advices(5)
Before Advice          : Executing Advice before calling b.method

	execution order :  adviceMethod() -- 1st
	                   b.method()     -- 2nd 

After Advice           : Executing Advice After b.method finished

	execution order :  b.method()      -- 1st
	                   adviceMethod()  -- 2nd

Around Advice          : Advice code made into 2 sections/parts, 1st part executed
			 before advice and then b.method, later 2nd part of advice.

	execution order : adviceMethod()  -- 1st-Part
			  b.method()      
	                  adviceMethod()  -- 2nd-Part

After Returning Advice : After executing b.method, only on sucess execute advice.

	execution order :  b.method()      -- 1st
	       (Is B.method executed successfully, no exception) then call
	                   adviceMethod()  -- 2nd

After Throwing Advice  : After executing b.method, if b.method is throwing any
                              exception then execute advice.

      execution order :  b.method()      -- 1st
	       (Is B.method executed not successfully, exception occured) then call
	                   adviceMethod()  -- 2nd


3) Pointcut : It is expression, it will select b.method which needs advices.
               It can never specify what advices.

  expression :   AS  RT  PACK.CLASS.METHOD(PARAM)


4) JoinPoint : It is a combination of Advices + Pointcut. In simple connecting b.methods
		with required advices.


5) Target  : Pure Business class object.

6) Weaving : It is a process of mixing b.class methods and their connected advices

7) Proxy   : Final Output (class/object) is called as Proxy that contains both
             logic connected.

--------------------------------------------------------------------------------
Implementations
a) Spring AOP using XML Based Configuration [Legacy Style]
b) Spring AOP using AspectJ [Annotations]

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>


Annotations:
@Aspect
@Before
@After
@Around
@AfterReturning
@AfterThrowing
@Pointcut

------------------------------------------------------------------
Q) What is the difference between After, After Returning, After Throwing Advices?
A) After advices is executed next to b.method either success or fail.
   After Returning advice is executed only on succssful execution of b.method
   After Throwing advice is executed only on Failure(Exception) execution of b.method

=======================================================================================
				Pointcut

=> Pointcut is expression, it will select b.class methods which needs advices.
=> Pointcut can never specify which advice is going to be connected.

Pointcut Syntax:-
 Specifier  ReturnType   Package.ClassName.MethodName(ParameterTypes)

Note : allowed symbols in Pointcut expression: *(star), .(dot)

-------Examples-----------------------------------------------------
#1) public  int  in.nit.dao.EmployeeDao.saveEmployee(Employee)

=> saveEmployee() method having Parameter 'Employee' with return type 'int'
   of type public defined in class EmployeeDao (in.nit.dao)
   is selected to connecte with advice.

#2) public  *  org.akhil.dao.EmployeeDao.*()

  => Zero Parametes only (no parameter)
  => Any method name/ Method inside EmployeeDao class
  => Any return type

#3) public  *  org.akhil.dao.EmployeeDao.*(..)
  
  => Zero or more params (of any type)  [Any Parameter is fine]

#4) public  *  org.akhil.dao.*.*(..)

 => all classes which ar inside in.nit.dao package and thier methods
 ====================
 B.Methods
---------------
M#1  public int saveEmployee(Employee e)  { ...}
M#2  public void deleteEmployee(Integer id)  { ...}
M#3  public void updateEmployee(Employee e)  { ...}
M#4  public Employee getEmployee(Integer id)  { ...}

-----Pointcut expressions--------------
 Specifier  ReturnType   Package.ClassName.MethodName(ParameterTypes)

d) public  *  *(Integer)
No of Methods matching : M#2, M#4 (2)

c) public  *  saveEmployee(..)

No of Methods matching : M#1 (only one)

a) public  *  *()
[Zero Params, any method name and any return type]

No of Methods matching : zero

b) public  void  *(..)
[two dots inside method param indicates any no.of/type of parameters]

No of Methods matching (2):  M#2, M#3

			 Spring Boot AOP Session-2					
    ------------------------------------------------------------
  

Aspect (class)
Advice (method)
 Before    advice()-->b.method()

 After    b.method()-->success/fail--> advice()

 AfterReturning    b.method()-->only on success--> advice()
 AfterThrowing     b.method()-->only on fail--> advice()

  Around -- advice-part1() --->b.method()--->advice-part-2()


Pointcut - expression-selecting b.method

===================================================================

Before  execution of saveEmployee() method plz call beginTx() method



-----------code--------------------------------------------
Step#1 Spring Starter Project
 Name: SpringBoot2AopEx

pom.xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-aop</artifactId>
</dependency>

S#2 At starter class: @EnableAspectJAutoProxy

S#3 Business class

package org.akhil.dao;

import org.springframework.stereotype.Component;

@Component
public class EmployeeDao  {

	public String saveEmployee() {
		System.out.println("FROM SAVE EMPLOYEE");
		/*if(new Random().nextInt(15)<=10) {
			throw new RuntimeException("DUMMY EXCEPTION");
		}*/
		return "HELLO";
	}
}

S#4 Aspect class
package org.akhil.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TxService {
	
	// pointcut : AS RT PACK.CLS.MN(PARAM)
	@Pointcut("execution(public String org.akhil.dao.EmployeeDao.saveEmployee())")
	public void p1() {}
	
	// advice
	@Before("p1()") //joinpoint
	public void beginTx() {
		System.out.println("Tx Started!!");
	}
	
	@AfterReturning(value = "p1()", returning = "ob")
	public void commitTx(Object ob) {
		System.out.println("Tx is committed" + ob);
	}
	
	@AfterThrowing(value = "p1()",throwing = "th")
	public void rollbackTx(Throwable th) {
		System.out.println("Tx is rollback." + th.getMessage());
	}
	
	
	@After("p1()")
	public void sendReport() {
		System.out.println("Report sent!");
	}
	
}


S#5 Runner class
package org.akhil.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import org.akhil.dao.EmployeeDao;

@Component
public class TestRunner implements CommandLineRunner {
	@Autowired
	private EmployeeDao dao;
	
	@Override
	public void run(String... args) throws Exception {
		dao.saveEmployee();
	}

}
===================================================================
From Advice, we need to call b.method by using joinpoint details
ie using : ProceedingJoinPoint.
It has a method  proceed() that makes call to b.method.
Even it returns value given by b.method (as Object).



