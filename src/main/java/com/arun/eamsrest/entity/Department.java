package com.arun.eamsrest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    @JsonIgnore
    @Id
    @GeneratedValue
    private long id;


    private String departmentName;

    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private List<Salary> salaries;


    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
}
