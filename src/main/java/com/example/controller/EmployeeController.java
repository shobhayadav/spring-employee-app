package com.example.controller;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.Employee;
import com.example.service.EmployeeService;

@Controller
@RequestMapping("/")
public class EmployeeController {

	@Autowired
	EmployeeService service;

	@Autowired
	MessageSource messageSource;

	/*
	 * This method will list all existing employees.
	 */
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String listEmployees(ModelMap model) {
		model.addAttribute("employees", service.findAllEmployees());
		return "index";
	}

	/*
	 * This method will provide the medium to add a new employee.
	 */
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public String newEmployee(ModelMap model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		model.addAttribute("edit", false);
		return "registration";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * saving employee in database. It also validates the user input
	 */
	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
	public String saveEmployee(@Valid Employee employee, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			return "registration";
		}

		service.saveEmployee(employee);

		model.addAttribute("success", "Employee " + employee.getName() + " registered successfully");
		return "success";
	}

	/*
	 * This method will provide the medium to update an existing employee.
	 */
	@RequestMapping(value = { "/edit-{ssn}-employee" }, method = RequestMethod.GET)
	public String editEmployee(@PathVariable String ssn, ModelMap model) {
		model.addAttribute("employee", service.findEmployeeBySsn(ssn));
		model.addAttribute("edit", true);
		return "registration";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * updating employee in database. It also validates the user input
	 */
	@RequestMapping(value = { "/edit-{ssn}-employee" }, method = RequestMethod.POST)
	public String updateEmployee(@Valid Employee employee, BindingResult result, ModelMap model, @PathVariable String ssn) {

		if (result.hasErrors()) {
			return "registration";
		}

		service.updateEmployee(employee);

		model.addAttribute("success", "Employee " + employee.getName() + " updated successfully");
		return "success";
	}

	/*
	 * This method will delete an employee by it's SSN value.
	 */
	@RequestMapping(value = { "/delete-{ssn}-employee" }, method = RequestMethod.GET)
	public String deleteEmployee(@PathVariable String ssn) {

		service.deleteEmployeeBySsn(ssn);

		return "redirect:/list";
	}

	/*
	 * This method handles duplicate SSN exception thrown by DB and redirects to error page indicating the same.
	 */
	@ExceptionHandler(PersistenceException.class )
	public ModelAndView handleUniqueConstriantError(HttpServletRequest req, Exception ex) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("errorMsg", "Duplicate SSN");
		mav.setViewName("error");
		return mav;
	}
	
	/*
	 * This method handles all exceptions and redirects to error page.
	 */
	@ExceptionHandler(Exception.class )
	public ModelAndView handleError(HttpServletRequest req, Exception ex) {
		String errorMsg = "Technical error.Please contact support for help.";
		ModelAndView mav = new ModelAndView();
		mav.addObject("errorMsg", errorMsg);
		mav.setViewName("error");
		return mav;
	}

}