package com.example.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@NamedQueries({
	@NamedQuery(name=Employee.FIND_ALL, query = "select e from Employee e"),
	@NamedQuery(name=Employee.FIND_BY_NAME_LIKE, query = "select e from Employee e where e.name like :name"),
	@NamedQuery(name=Employee.FIND_BY_SSN, query = "select e from Employee e where e.ssn = :ssn"),
	@NamedQuery(name=Employee.DELETE_BY_SSN, query = "delete from Employee e where e.ssn = :ssn")
})
@Table(name = "EMPLOYEE")
public class Employee {
	
	public static final String FIND_ALL ="Employee.findAllEmployees";
	public static final String FIND_BY_NAME_LIKE ="Employee.findByNameLike";
	public static final String FIND_BY_SSN="Employee.findBySsn";
	public static final String DELETE_BY_SSN="Employee.deleteBySsn";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(min = 3, max = 50)
	@Column(name = "NAME", nullable = false)
	private String name;

	@NotNull
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name = "JOINING_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date joiningDate;

	@NotNull
	@Digits(integer = 8, fraction = 2)
	@Column(name = "SALARY", nullable = false)
	private BigDecimal salary;

	@NotEmpty
	@Column(name = "SSN", unique = true, nullable = false)
	private String ssn;
	

	public Employee() {
		super();
		
	}

	public Employee(Long id, String name, Date joiningDate, BigDecimal salary, String ssn) {
		super();
		this.id = id;
		this.name = name;
		this.joiningDate = joiningDate;
		this.salary = salary;
		this.ssn = ssn;
	}

	public Employee( String name, Date joiningDate, BigDecimal salary, String ssn) {
		this.name = name;
		this.joiningDate = joiningDate;
		this.salary = salary;
		this.ssn = ssn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", joiningDate=" + joiningDate + ", salary=" + salary + ", ssn=" + ssn + "]";
	}

}