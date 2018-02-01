package com.example.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.model.Employee;
import com.example.repository.EmployeeRepository;



@Repository
public class EmployeeRepositoryImpl extends GenericRepositoryImpl<Employee, Long>  implements EmployeeRepository {

	@Override
	public List<Employee> findAll() {
		return em.createNamedQuery(Employee.FIND_ALL, Employee.class).getResultList();
	}

	@Override
	public Employee findOne(Class<Employee> t, Long id) {
		return em.find(t, id);
	}

	@Override
	public List<Employee> findByNameLike(String name) {
		return em.createNamedQuery(Employee.FIND_BY_NAME_LIKE, Employee.class)
				.setParameter("name", name + "%").getResultList();
	}

	@Override
	public Employee findBySsn(String ssn) {
		return em.createNamedQuery(Employee.FIND_BY_SSN, Employee.class)
				.setParameter("ssn", ssn ).getSingleResult();
	}

	@Override
	public Employee updateBySsn(Employee employee){
		Employee e = findBySsn(employee.getSsn());
		employee.setId(e.getId());
		return update(employee);
	}

	@Override
	public int deleteBySSn(String ssn){
		return em.createNamedQuery(Employee.DELETE_BY_SSN).executeUpdate();
	}

}
