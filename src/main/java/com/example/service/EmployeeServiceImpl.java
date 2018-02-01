package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Employee;
import com.example.repository.EmployeeRepository;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public Employee findById(Long id) {
		return employeeRepository.findOne(Employee.class, id);
	}

	@Override
	@Transactional
	public Employee saveEmployee(Employee employee)  {
		return employeeRepository.create(employee);
	}

	@Override
	@Transactional
	public Employee updateEmployee(Employee employee)  {
		return employeeRepository.update(employee);
	}

	@Override
	@Transactional
	public void deleteEmployeeBySsn(String ssn) {
		employeeRepository.deleteBySSn(ssn);
		
	}

	@Override
	public List<Employee> findAllEmployees()  {
		return employeeRepository.findAll();
	}

	
	@Override
	public Employee findEmployeeBySsn(String ssn)  {
		return employeeRepository.findBySsn(ssn);
	}

}