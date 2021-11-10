package com.example.demo.api;

import com.example.demo.data.model.Employee;
import com.example.demo.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Tag(name = "Employee")
@RestController @RequestMapping("api/employee")
@RequiredArgsConstructor
public class EmployeeApi {

    private final EmployeeService employeeService;

    @PostMapping
    ResponseEntity<Employee> createEmployee(@RequestBody @Valid Employee employee) {
        try {
            Employee emp = employeeService.createEmployee(employee);
            return new ResponseEntity<>(emp, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody @Valid Employee employee) throws Exception {
        Employee emp = employeeService.updateEmployee(id, employee);
        return new ResponseEntity<>(emp, HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<List<Employee>> findAllEmployee() {
        try {
            List<Employee> employees = employeeService.findAllEmployee();
            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<Employee> findByEmployeeId(@PathVariable Long id) {
        try {
            Optional<Employee> emp = employeeService.findByEmployeeId(id);
            if (emp.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(emp.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteByEmployee(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
