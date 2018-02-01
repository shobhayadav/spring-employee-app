package com.example.service;

import java.util.List;

import com.example.model.Employee;

public interface EmployeeService {

	/* Method to find employee using Id, returns employee found*/
	Employee findById(Long id) ;

	/* Method to save employee details, returns employee */
	Employee saveEmployee(Employee employee) ;

	/* Method to update employee record, returns employee */
	Employee updateEmployee(Employee employee) ;

	/* Method to delete employee record usingSSNd*/
	void deleteEmployeeBySsn(String ssn) ;

	/* Method to retrieve all employees*/
	List<Employee> findAllEmployees() ;

	/* Method to find employee using ssn, returns employee found*/
	Employee findEmployeeBySsn(String ssn) ;


}