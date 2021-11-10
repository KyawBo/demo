package com.example.demo.api;

import com.example.demo.data.EmployeeTestDataFactory;
import com.example.demo.data.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.example.demo.integrationtest.util.JsonHelper.fromJson;
import static com.example.demo.integrationtest.util.JsonHelper.toJson;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("kbbyt")
public class TestEmployeeApi {

    private final String API_URL = "/api/employee";

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final EmployeeTestDataFactory employeeTestDataFactory;

    @Autowired
    public TestEmployeeApi(MockMvc mockMvc,
                           ObjectMapper objectMapper,
                           EmployeeTestDataFactory employeeTestDataFactory) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.employeeTestDataFactory = employeeTestDataFactory;
    }

    @Test
    public void testCreateSuccess() throws Exception {
        Employee goodRequest = new Employee("Test Author A", "Developer");

        MvcResult createResult = this.mockMvc
                .perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, goodRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        Employee employee = fromJson(objectMapper, createResult.getResponse().getContentAsString(), Employee.class);
        assertNotNull(employee.getId(), "Employee id must not be null!");
        assertEquals(goodRequest.getName(), employee.getName(), "Employee name must be equal!");
    }

    @Test
    public void testCreateFail() throws Exception {
        Employee badRequest = new Employee();

        this.mockMvc
                .perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, badRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Method argument validation failed")));
    }

    @Test
    public void testEditSuccess() throws Exception {
        Employee employee = employeeTestDataFactory.createEmployee("Test Employee A");

        Employee updateRequest = new Employee("Test Employee B", "Cool Position");

        MvcResult updateResult = this.mockMvc
                .perform(put(String.format("/api/employee/%s", employee.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, updateRequest)))
                .andExpect(status().isOk())
                .andReturn();
        Employee newEmployee = fromJson(objectMapper, updateResult.getResponse().getContentAsString(), Employee.class);

        assertEquals(updateRequest.getName(), newEmployee.getName(), "Employee name must be equals");
        assertEquals(updateRequest.getPosition(), newEmployee.getPosition(), "Employee position must be equal!");
    }


    @Test
    public void testEditFailNotFound() throws Exception {
        Employee employee = employeeTestDataFactory.createEmployee("Test Employee A");

        Employee updateRequest = new Employee();

        this.mockMvc
                .perform(put(String.format("/api/author/%s", employee.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, updateRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        Employee employee = employeeTestDataFactory.createEmployee("Test Employee A");

        this.mockMvc
                .perform(delete(String.format("/api/employee/%s", employee.getId())))
                .andExpect(status().isOk());

        this.mockMvc
                .perform(get(String.format("/api/employee/%s", employee.getId())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteFailServerError() throws Exception {
        this.mockMvc
                .perform(delete(String.format("/api/employee/%s", "1")))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void testFindByIdSuccess() throws Exception {
        Employee employee = employeeTestDataFactory.createEmployee("Test Employee A");

        MvcResult updateResult = this.mockMvc
                .perform(get(String.format("/api/employee/%s", employee.getId())))
                .andExpect(status().isOk())
                .andReturn();
        Employee newEmployee = fromJson(objectMapper, updateResult.getResponse().getContentAsString(), Employee.class);

        assertEquals(employee.getName(), newEmployee.getName(), "Employee name must be equals");
        assertEquals(employee.getPosition(), newEmployee.getPosition(), "Employee position must be equal!");
    }

    @Test
    public void testFindByIdFail() throws Exception {
        Employee employee = employeeTestDataFactory.createEmployee("Test Employee A");

        this.mockMvc
                .perform(delete(String.format("/api/employee/%s", employee.getId())))
                .andExpect(status().isOk());
        this.mockMvc
                .perform(get(String.format("/api/employee/%s", employee.getId())))
                .andExpect(status().isNotFound());
    }
}
