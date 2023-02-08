package com.arun.eamsrest.service;


import com.arun.eamsrest.entity.Department;
import com.arun.eamsrest.entity.Employee;
import com.arun.eamsrest.entity.Job;
import com.arun.eamsrest.entity.Salary;
import com.arun.eamsrest.entity.role.Role;
import com.arun.eamsrest.entity.role.RoleName;
import com.arun.eamsrest.exception.ResourceNotFoundException;
import com.arun.eamsrest.payload.ApiResponse;
import com.arun.eamsrest.payload.request.EmployeeRequest;
import com.arun.eamsrest.repository.DepartmentRepository;
import com.arun.eamsrest.repository.EmployeeRepository;
import com.arun.eamsrest.repository.RoleRepository;
import com.arun.eamsrest.utils.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RoleRepository roleRepository;


    public Employee addEmployee(long departmentId, EmployeeRequest employee) {

        Department department=departmentRepository.findById(departmentId).orElseThrow(()->
                 new ResourceNotFoundException("Department not found with id: "+departmentId)
        );
        Job job= Job.builder()
                .title(employee.getTitle())
                .build();
        Salary salary=Salary.builder()
                .amount(employee.getAmount())
                .department(department)
                .job(job)
                .build();
        Set<Role> roles=new HashSet<>();
        roles.add(
                roleRepository.findByName(RoleName.ROLE_USER)
        );
        Employee emp1=Employee.builder()
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .age(employee.getAge())
                .email(employee.getEmail())
                .password(employee.getPassword())
                .gender(employee.getGender())
                .salary(salary)
                .roles(roles)
                .build();
        Employee emp =employeeRepository.save(emp1);
        return emp;
    }

    public ApiResponse getAllEmployees() {
        List<Employee> all = employeeRepository.findAll();

        ApiResponse apiResponse=ApiResponse.builder()
                .success(Boolean.TRUE)
                .status(HttpStatus.OK)
                .message(AppConstants.RETRIEVAL_SUCCESS)
                .errors(new ArrayList<>())
                .data(all)
                .build();
        return apiResponse;
    }
}
