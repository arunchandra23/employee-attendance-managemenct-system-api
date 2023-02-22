package com.arun.eamsrest.controller;

import com.arun.eamsrest.entity.Employee;
import com.arun.eamsrest.payload.ApiResponse;
import com.arun.eamsrest.payload.request.EmployeeRequest;
import com.arun.eamsrest.repository.DepartmentRepository;
import com.arun.eamsrest.service.EmployeeService;
import com.arun.eamsrest.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(AppConstants.BASE_URL + "/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentRepository departmentRepository;

    @PostMapping("{departmentId}")
    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Employee> addEmployee(@PathVariable long departmentId, @Valid @RequestBody EmployeeRequest employee) {

        Employee emp = employeeService.addEmployee(departmentId, employee);
        return new ResponseEntity<>(emp, HttpStatus.CREATED);
    }

    @PutMapping("{employeeId}")
    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long employeeId, @Valid @RequestBody EmployeeRequest employee) {

        Employee emp = employeeService.updateEmployee(employeeId, employee);
        return new ResponseEntity<>(emp, HttpStatus.CREATED);
    }

    //    @PreAuthorize("hasRole('HR')")
    @GetMapping
    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getAllEmployees() {
        ApiResponse allEmployees = employeeService.getAllEmployees();

        return new ResponseEntity<>(allEmployees, HttpStatus.OK);

    }

    @GetMapping("/departments/{departmentId}")
    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getEmployeeByDepartmentId(@PathVariable long departmentId) {

        ApiResponse apiResponse = employeeService.getEmployeeByDepartmentId(departmentId);
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @GetMapping("{employeeId}/attendance/{from}/{to}")
    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getEmployeeAttendanceBetweenDates(@PathVariable long employeeId, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate from, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate to) {
        ApiResponse apiResponse = employeeService.getEmployeeAttendanceBetweenDates(employeeId, from, to);
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());

    }

    @GetMapping("/{employeeId}/report/{from}/{to}")
    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getEmployeeAttendanceReport(@PathVariable long employeeId, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate from, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate to) {
        ApiResponse apiResponse = employeeService.getEmployeeAttendanceReport(employeeId, from, to);
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());

    }
//    @DeleteMapping("/{employeeId}")
//    public ResponseEntity<ApiResponse> deleteEmployee(@PathVariable long employeeId){
//        ApiResponse apiResponse = employeeService.deleteEmployee(employeeId);
//        return new ResponseEntity<>(apiResponse,apiResponse.getStatus());
//    }

}
