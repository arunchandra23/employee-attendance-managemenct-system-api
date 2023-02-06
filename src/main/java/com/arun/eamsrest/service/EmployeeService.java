package com.arun.eamsrest.service;


import com.arun.eamsrest.entity.Department;
import com.arun.eamsrest.entity.Employee;
import com.arun.eamsrest.entity.Job;
import com.arun.eamsrest.entity.Salary;
import com.arun.eamsrest.exception.ResourceNotFoundException;
import com.arun.eamsrest.payload.ApiResponse;
import com.arun.eamsrest.payload.request.EmployeeRequest;
import com.arun.eamsrest.repository.DepartmentRepository;
import com.arun.eamsrest.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Employee emp1=Employee.builder()
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .age(employee.getAge())
                .email(employee.getEmail())
                .password(employee.getPassword())
                .gender(employee.getGender())
                .salary(salary)
                .build();
        Employee emp =employeeRepository.save(emp1);
        return emp;
    }

}
