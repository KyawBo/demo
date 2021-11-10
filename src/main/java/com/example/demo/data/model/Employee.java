package com.example.demo.data.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name="employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    @NotNull
    private String name;
    @Column(name = "position")
    @NotNull
    private String position;

    public Employee() {}

    public Employee(String name, String position) {
        this.name = name;
        this.position = position;
    }
}
