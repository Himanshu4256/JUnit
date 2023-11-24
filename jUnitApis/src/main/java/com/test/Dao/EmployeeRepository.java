package com.test.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
