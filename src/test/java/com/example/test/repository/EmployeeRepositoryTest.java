package com.example.test.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.tuple;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.configuration.JpaConfiguration;
import com.example.model.Employee;
import com.example.repository.EmployeeRepository;

@ContextConfiguration(classes={JpaConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)

public class EmployeeRepositoryTest {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	private static Validator validator;
	
	 @BeforeClass
	    public static void setUp() {
	        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	        validator = factory.getValidator();
	    }
	
	
	@Test
	public void test_find_all_employees(){
		assertThat(employeeRepository.findAll()).isNotNull().isNotEmpty().hasSize(3);
	}
	@Test
	public void test_find_by_name_like() {
		assertThat(employeeRepository.findByNameLike("C")).
		hasSize(1).
		extracting("name" ,"ssn").contains(tuple("Clark Kent","12890"));

	}
	@Test
	@Transactional
	public void test_duplicate_ssn_check_is_handled(){
		// given
		Employee e = new Employee();
		e.setName("TestDuplicateSSN");
		e.setSalary(new BigDecimal(250000));
		e.setSsn("23456"); // Existing SSN
		e.setJoiningDate(new Date());
		
		//when
	  Throwable thrown = catchThrowable(() -> { employeeRepository.create(e); });
		//then
	   assertThat(thrown).isInstanceOf(PersistenceException.class)
		                  .hasCauseExactlyInstanceOf(ConstraintViolationException.class).
		                  hasStackTraceContaining("Unique index or primary key violation");
		                               
	}
	
	
	@Test
	public void test_employee_details_valid(){
		Employee employee = new Employee();
		employee.setName("TestValidEmployee");
		employee.setSalary(new BigDecimal(300000));
		employee.setSsn("43256");
		employee.setJoiningDate(new Date());
		Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
		assertThat(violations).isEmpty();
		
	}
	
	@Test
	public void test_to_check_name_field_validation_in_employee(){
		Employee employee = new Employee();
		employee.setName("TestNameFieldCheck1111111111111111111111111111111111111111111111111111111111111111111111111111111");
		employee.setSalary(new BigDecimal(300000));
		employee.setSsn("88765");
		employee.setJoiningDate(new Date());
		Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
		assertThat(violations).isNotEmpty()
		.extracting("interpolatedMessage")
		.containsExactly("size must be between 3 and 50");
		
	}
	

}
