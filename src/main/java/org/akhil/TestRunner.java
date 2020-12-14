package org.akhil;

import org.akhil.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestRunner implements CommandLineRunner {

	@Autowired
	private EmployeeDao dao; 
	
	@Override
	public void run(String... args) throws Exception {
		dao.getEmployee();
		dao.saveEmployee();
		dao.deleteEmployee();
		
	}

}
