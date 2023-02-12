package com.arun.eamsrest.entity;


import com.arun.eamsrest.entity.attendance.Attendance;
import com.arun.eamsrest.entity.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @JsonIgnore
    @Id
    @GeneratedValue
    private long id;
    private String firstName;
    private String lastName;

    private String gender;

    private long age;
    private String email;
    private String password;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "employee_role", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "salary_id")
    private Salary salary;

    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    private List<Attendance> attendance;

    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    private List<Leave> leaves;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    private Department department;


}
