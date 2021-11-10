package com.example.demo.unittest;

import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.data.model.Employee;
import com.example.demo.data.repository.EmployeeRepository;
import com.example.demo.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceUnitTest {
    @InjectMocks
    EmployeeServiceImpl service;

    @Mock
    EmployeeRepository repository;

    @Test
    public void shouldReturnAllEmployee() {
        List<Employee> list = new ArrayList<Employee>();
        Employee empOne = new Employee("John", "Manager");
        Employee empTwo = new Employee("Alex", "FE Developer");
        Employee empThree = new Employee("Steve", "BE Developer");

        list.add(empOne);
        list.add(empTwo);
        list.add(empThree);

        when(repository.findAll()).thenReturn(list);

        //test
        List<Employee> empList = service.findAllEmployee();

        assertEquals(3, empList.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void whenGivenId_shouldReturnEmployee_ifFound() {
        Employee employee = new Employee("Leo", "Senior Developer");

        Optional<Employee> optionalEmployee = Optional.of(employee);

        when(repository.findById(1L)).thenReturn(optionalEmployee);

        //test
        Optional<Employee> expected = service.findByEmployeeId(1L);

        assertThat(expected.get()).isSameAs(employee);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void whenSaveEmployee_shouldReturnEmployee() {
        Employee employee = new Employee("Leo","Developer");

        when(repository.save(ArgumentMatchers.any(Employee.class))).thenReturn(employee);

        Employee created = service.createEmployee(employee);

        assertThat(created.getName()).isSameAs(employee.getName());
        verify(repository, times(1)).save(employee);
    }


    @Test
    public void whenGivenId_shouldUpdateEmployee_ifFound() throws Exception {
        Employee employee = new Employee();
        employee.setId(89L);
        employee.setName("Test Name");

        Employee newEmployee = new Employee();
        employee.setName("New Test Name");

        given(repository.findById(employee.getId())).willReturn(Optional.of(employee));
        service.updateEmployee(employee.getId(), newEmployee);

        verify(repository).save(employee);
        verify(repository).findById(employee.getId());
    }

    @Test
    public void should_throw_exception_when_employee_doesnt_exist() {
        Employee employee = new Employee();
        employee.setId(89L);
        employee.setName("Test Name");

        Employee newEmployee = new Employee();
        newEmployee.setId(90L);
        newEmployee.setName("New Test Name");

        given(repository.findById(anyLong())).willReturn(Optional.ofNullable(null));

        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> service.updateEmployee(employee.getId(), newEmployee),
                "Expected to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("not found"));
    }

    @Test
    public void whenGivenId_shouldDeleteEmployee_ifFound(){
        Employee employee = new Employee();
        employee.setName("Test Name");
        employee.setId(1L);

        service.deleteByEmployee(employee.getId());
        verify(repository).deleteById(employee.getId());
    }

}
