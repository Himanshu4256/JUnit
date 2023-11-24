package com.test;


import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.Dao.EmployeeRepository;
import com.test.entity.Employee;

//@DataJpaTest
@SpringBootTest
public class EmployeeRepositoryTests {

	@Autowired
	private EmployeeRepository employeeRepository;

//	@Test
//	public void saveEmployee() {
//		Employee employee = Employee.builder()
//							.firstName("Barat")
//							.lastName("Trevedi")
//							.email("bharat@gmail.com")
//							.build();
//		employeeRepository.save(employee);
//		
//		assertThat(employee.getId()).isGreaterThan(0);
//	}
	
	@Test
	public void getEmployee() {
		Employee employee = employeeRepository.findById(1l).get();
		
		assertThat(employee.getId()).isEqualTo(1);
	}
	
	@Test
	public void getListOfEmployees() {
		List<Employee> employees = employeeRepository.findAll();
		assertThat(employees.size()).isGreaterThan(2);
	}
	
//	@Test
//	public void updateEmployeeTest() {
//		Employee employee = employeeRepository.findById(1L).get();
//		employee.setFirstName("Rupak");
//		employee.setLastName("Chauhan");
//		employee.setEmail("rupak@gmail.com");
//		
//		Employee employeeUpdated = employeeRepository.save(employee);
//		
//		assertThat(employeeUpdated.getEmail()).isEqualTo("rupak@gmail.com");
//	}
}