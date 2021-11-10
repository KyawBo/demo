package com.example.demo.service;

import com.example.demo.data.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee createEmployee(Employee employee);
    Employee updateEmployee(Long id, Employee employee) throws Exception;
    Optional<Employee> findByEmployeeId(Long id);
    List<Employee> findAllEmployee();
    void deleteByEmployee(Long id);
}
