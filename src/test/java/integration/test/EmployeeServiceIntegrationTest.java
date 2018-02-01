package integration.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import com.example.configuration.EmployeeAppConfig;
import com.example.model.Employee;
import com.example.service.EmployeeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {EmployeeAppConfig.class})
@WebAppConfiguration
@TransactionConfiguration(defaultRollback=false)
public class EmployeeServiceIntegrationTest {
	
	private Employee emp;
	
	
	@Autowired
	private EmployeeService employeeService;
	
	@Before
	public void setUp() {
		emp = new Employee("TestEmployee", new Date(), new BigDecimal(2600.00), "26578");
	}
	@Test
	@Transactional(rollbackOn= {Exception.class})
	public void test_save_employee() /*Positive scenario*/{
		assertThat(employeeService.saveEmployee(emp))
		.isEqualToComparingFieldByFieldRecursively(emp);
		assertThat(employeeService.findAllEmployees())
		.hasSize(4);
		
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void test_save_employee_fails_duplicate_ssn() /*Negative scenario*/{
		emp.setSsn("11345");

		Throwable thrown =  catchThrowable(() -> {employeeService.saveEmployee(emp);});
		 assertThat(thrown).isInstanceOf(PersistenceException.class);
        
		
	}
	
	@Test
	@Transactional
	public void test_update_employee() {
		emp.setSsn("78654");
		assertThat(employeeService.updateEmployee(emp)).isNotNull();
		
	}

}
