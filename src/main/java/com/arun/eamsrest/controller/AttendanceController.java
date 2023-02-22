package com.arun.eamsrest.controller;

import com.arun.eamsrest.exception.BadRequestException;
import com.arun.eamsrest.payload.ApiResponse;
import com.arun.eamsrest.payload.request.AttendanceRequest;
import com.arun.eamsrest.service.AttendanceService;
import com.arun.eamsrest.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstants.BASE_URL + "/attendance")
public class AttendanceController {


    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("present/{employeeId}")
    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> markPresent(@PathVariable long employeeId, @Valid @RequestBody AttendanceRequest attendanceRequest) throws BadRequestException {
        ApiResponse apiResponse = attendanceService.markPresent(employeeId, attendanceRequest);
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @PostMapping("absent/{employeeId}")
    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> markAbsent(@PathVariable long employeeId, @Valid @RequestBody AttendanceRequest attendanceRequest) {
        ApiResponse apiResponse = attendanceService.markAbsent(employeeId, attendanceRequest);
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }


}
