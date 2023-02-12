package com.arun.eamsrest.controller;

import com.arun.eamsrest.payload.ApiResponse;
import com.arun.eamsrest.payload.request.AttendanceRequest;
import com.arun.eamsrest.payload.request.LeaveRequest;
import com.arun.eamsrest.service.LeaveService;
import com.arun.eamsrest.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstants.BASE_URL+"/leaves")
public class LeaveController {


    @Autowired
    private LeaveService leaveService;

    @PostMapping("/{employeeId}")
    public ResponseEntity<ApiResponse> applyLeave(@PathVariable long employeeId,@Valid @RequestBody LeaveRequest leaveRequest){
        ApiResponse apiResponse = leaveService.applyLeave(employeeId, leaveRequest);
        return  new ResponseEntity<>(apiResponse, apiResponse.getStatus());

//        return  null;
    }

    @PutMapping("/{employeeId}/approve")
    public ResponseEntity<ApiResponse> approveLeave(@PathVariable long employeeId,@RequestBody AttendanceRequest date){
        ApiResponse apiResponse = leaveService.approveLeave(employeeId, date);
        return  new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }
    @PutMapping("/{employeeId}/reject")
    public ResponseEntity<ApiResponse> rejectLeave(@PathVariable long employeeId,@RequestBody AttendanceRequest date){
        ApiResponse apiResponse = leaveService.rejectLeave(employeeId, date);
        return  new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<ApiResponse> deleteLeave(@PathVariable long employeeId,@Valid @RequestBody AttendanceRequest attendanceRequest){
        ApiResponse apiResponse = leaveService.deleteLeave(employeeId, attendanceRequest);
        return  new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }



}
