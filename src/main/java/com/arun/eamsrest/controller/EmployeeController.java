package com.arun.eamsrest.controller;

import com.arun.eamsrest.entity.Employee;
import com.arun.eamsrest.payload.ApiResponse;
import com.arun.eamsrest.payload.request.EmployeeRequest;
import com.arun.eamsrest.repository.DepartmentRepository;
import com.arun.eamsrest.service.EmployeeService;
import com.arun.eamsrest.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppConstants.BASE_URL+"/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentRepository departmentRepository;

    @PostMapping("{departmentId}/add")
    public ResponseEntity<Employee> addEmployee(@PathVariable long departmentId,@Valid @RequestBody EmployeeRequest employee){

        Employee emp=employeeService.addEmployee(departmentId,employee);
        return  new ResponseEntity<>(emp, HttpStatus.CREATED);
    }

    @PostMapping("{departmentId}/k")
    public ResponseEntity<Employee> addEmployeee(@PathVariable long departmentId,@Valid @RequestBody Employee employee){

     return null;
    }
    @GetMapping
    public ResponseEntity<ApiResponse> getAllEmployees(){
        ApiResponse allEmployees = employeeService.getAllEmployees();

        return new ResponseEntity<>(allEmployees,HttpStatus.OK);

    }

}
