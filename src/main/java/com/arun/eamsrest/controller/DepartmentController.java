package com.arun.eamsrest.controller;

import com.arun.eamsrest.entity.Department;
import com.arun.eamsrest.payload.ApiResponse;
import com.arun.eamsrest.payload.request.DepartmentRequest;
import com.arun.eamsrest.service.DepartmentService;
import com.arun.eamsrest.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstants.BASE_URL+"/departments")
public class DepartmentController {


    @Autowired
    private DepartmentService departmentService;

    @PostMapping()

    public ResponseEntity<Department> addDepartment(@Valid @RequestBody DepartmentRequest departmentRequest){
        Department department=departmentService.addDepartment(departmentRequest);
        return  new ResponseEntity<>(department, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllDepartments(){
        ApiResponse allDepartments=departmentService.getAllDepartments();
        return new ResponseEntity<>(allDepartments,allDepartments.getStatus());
    }

}
