package org.akhil.dao;

import org.akhil.anno.MyTxAnno;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDao {
	public void saveEmployee() {
		System.out.println("Employee Saved !!!");
		//throw new RuntimeException("Dummy Exception");
	}
	
	
	public String getEmployee() {
		return "Employee 1";
	}
	
	@MyTxAnno
	public void deleteEmployee() {
		System.out.println("Deleted Employee !!!! ");
	}
}
