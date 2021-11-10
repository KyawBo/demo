package com.example.demo.data;

import com.example.demo.data.model.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Service
public class EmployeeTestDataFactory {

    @Autowired
    private EmployeeService employeeService;

    public Employee createEmployee(String name, String position) {
        Employee createRequest = new Employee(name, position);

        Employee employee = employeeService.createEmployee(createRequest);

        assertNotNull(employee.getId(), "Employee id must not be null!");
        assertEquals(name, employee.getName(), "Employee name must be equal!");

        return employee;
    }

    public Employee createEmployee(String name) {
        return createEmployee(name, "testPosition");
    }

    public void deleteAuthor(Long id) {
        employeeService.deleteByEmployee(id);
    }

}
