package com.example.demo.service.impl;

import com.example.demo.data.model.Employee;
import com.example.demo.data.repository.EmployeeRepository;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employeeDto) throws Exception {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            Employee emp = employee.get();
            emp.setName(employeeDto.getName());
            emp.setPosition(employeeDto.getPosition());
            return employeeRepository.save(emp);
        } else {
            throw new NotFoundException(Employee.class, id);
        }
    }

    @Override
    public Optional<Employee> findByEmployeeId(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public List<Employee> findAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public void deleteByEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
