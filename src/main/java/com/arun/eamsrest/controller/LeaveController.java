package com.arun.eamsrest.controller;

import com.arun.eamsrest.entity.Department;
import com.arun.eamsrest.payload.ApiResponse;
import com.arun.eamsrest.payload.request.DepartmentRequest;
import com.arun.eamsrest.payload.request.LeaveRequest;
import com.arun.eamsrest.service.DepartmentService;
import com.arun.eamsrest.service.LeaveService;
import com.arun.eamsrest.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstants.BASE_URL+"/leaves")
public class LeaveController {


    @Autowired
    private LeaveService leaveService;

    @PostMapping("/{employeeId}/apply")
    public ResponseEntity<ApiResponse> applyLeave(@PathVariable long employeeId,@Valid @RequestBody LeaveRequest leaveRequest){
        leaveService.applyLeave(employeeId,leaveRequest);
//        return  new ResponseEntity<>(ApiResponse, HttpStatus.CREATED);

        return  null;
    }


}
