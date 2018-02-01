package com.example.controller;

import java.util.List;

import javax.persistence.NoResultException;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.exceptions.RestApiError;
import com.example.model.Employee;
import com.example.service.EmployeeService;

@RestController
@RequestMapping(value="/employees")
public class EmployeeRestController {
	
	@Autowired
	private EmployeeService employeeService;
	
	/* This method will get  list of employees*/
	@RequestMapping( method=RequestMethod.GET )
	public List<Employee> findAllEmployees(){
		return employeeService.findAllEmployees();
	}
	
	/* This method fetches returns employee details based on SSN passed as Path varaible.Returns 404 if record not found*/
	@RequestMapping(value="/{ssn}" , method=RequestMethod.GET)
	public Employee findEmployeeBySSn(@PathVariable("ssn") String ssn ) throws NoResultException{
		return employeeService.findEmployeeBySsn(ssn); 
	}
	
	/* This method fetches returns employee details based on SSN passed as query parameter.Returns 404 if record not found*/
	@RequestMapping(value="/params" , method=RequestMethod.GET)
    public Employee findEmployeeBySsnUsingRequestParam(@RequestParam("ssn") String ssn) throws NoResultException {
		return employeeService.findEmployeeBySsn(ssn); 
    }

	/* This method validates the request body  and if valid request then creates a new employee*/
	@RequestMapping(method=RequestMethod.POST)
    public Employee createEmployee(@RequestBody  @Valid Employee employee){
		 employeeService.saveEmployee(employee);
         return employee;
    }
	
	/* Exception handler for Record Not found*/
	@ExceptionHandler(NoResultException.class)
	public ResponseEntity<Object> handleEntityNotFound(NoResultException ex) {
		return buildResponseEntity(new RestApiError(HttpStatus.NOT_FOUND,"Record not found",ex));
	}
	/*Exception handler for Invalid Argument */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleBadRequest(MethodArgumentNotValidException ex){
	  return buildResponseEntity(new RestApiError(HttpStatus.BAD_REQUEST, "Invalid input", ex));
	}
	
	/* Generic exception handler*/
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception ex) {
		 if (ex.getCause() instanceof ConstraintViolationException)
			return buildResponseEntity(new RestApiError(HttpStatus.CONFLICT,
					"Duplicate SSN", ex));
		else
			return buildResponseEntity(new RestApiError(
					HttpStatus.INTERNAL_SERVER_ERROR, ex));

	}
	/* Exception object builder method */
	private ResponseEntity<Object> buildResponseEntity(RestApiError apiError) {
	       return new ResponseEntity<>(apiError, apiError.getStatus());
	   }
}
