package com.example.repository;

import java.util.List;

import com.example.model.Employee;

public interface EmployeeRepository extends GenericRespository<Employee, Long> {
 
	    /* Method to find names where names are like the arg passed*, returns employee found*/
		List<Employee> findByNameLike(String name);

		/* Method to find employee record using ssn , returns employee created*/
		Employee findBySsn(String ssn) ;

		/* Method to update employee record using ssn , returns employee updated*/
		Employee updateBySsn(Employee employee) ;

		/* Method to delete employee record using ssn, returns no of rows affected */
		int deleteBySSn(String ssn) ;
}
