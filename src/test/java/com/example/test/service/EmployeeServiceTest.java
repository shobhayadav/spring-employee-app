package com.example.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.example.model.Employee;
import com.example.repository.EmployeeRepository;
import com.example.service.EmployeeService;
import com.example.service.EmployeeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

	private Employee emp ;
	@Mock
	EmployeeRepository employeeRepository;

	@InjectMocks
	EmployeeService employeeService = new EmployeeServiceImpl();

	@Before
	public void setup() {
		emp = new Employee(3L, "Test", new Date(), new BigDecimal(2500), "21345");
	}
	
	@Test
	public void test_find_employee_by_ssn() {
		 when(employeeRepository.findBySsn(anyString())).thenReturn(emp);	 
		 assertThat(emp).
		 isEqualToComparingFieldByField(employeeService.findEmployeeBySsn(emp.getSsn()));
		 verify(employeeRepository, times(1)).findBySsn(emp.getSsn());	 
		 
	}
	
	@Test
	public void test_create_employee() {
		when(employeeRepository.create(any())).thenReturn(emp);
		assertThat(emp).
		isEqualToComparingFieldByField(employeeService.saveEmployee(emp));
		verify(employeeRepository, times(1)).create(emp);
	}
	
	@Test
	public void test_update_employee() {
		when(employeeRepository.update(any())).thenReturn(emp);
		assertThat(emp).
		isEqualToComparingFieldByField(employeeService.updateEmployee(emp));
	}
	
	@Test
	public void test_find_employee_by_id() {
		when(employeeRepository.findOne(Employee.class, emp.getId())).thenReturn(emp);
		assertThat(emp).
		isEqualToComparingFieldByField(employeeService.findById(emp.getId()));
		verify(employeeRepository, times(1)).findOne(Employee.class, emp.getId());
	}

	@Test
	public void test_find_all_employees() {
		List<Employee> employeeLst = new ArrayList<Employee>();
		employeeLst.add(emp);
		when(employeeRepository.findAll()).thenReturn(employeeLst);
		assertThat(employeeService.findAllEmployees()).
		isNotEmpty().
		hasSize(1).extracting("name" ,"ssn").contains(tuple("Test","21345"));
		verify(employeeRepository,times(1)).findAll();
		
		
	}
}
