package com.arun.eamsrest.service;

import com.arun.eamsrest.entity.Employee;
import com.arun.eamsrest.entity.Leave;
import com.arun.eamsrest.entity.attendance.Attendance;
import com.arun.eamsrest.exception.BadRequestException;
import com.arun.eamsrest.exception.ResourceNotFoundException;
import com.arun.eamsrest.payload.ApiResponse;
import com.arun.eamsrest.payload.request.AttendanceRequest;
import com.arun.eamsrest.payload.request.LeaveRequest;
import com.arun.eamsrest.repository.AttendanceRepository;
import com.arun.eamsrest.repository.EmployeeRepository;
import com.arun.eamsrest.repository.LeaveRepository;
import com.arun.eamsrest.utils.AppConstants;
import com.arun.eamsrest.utils.LeaveStatus;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class LeaveService {
    @Autowired
    private LeaveRepository leaveRepository;

    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private EmployeeRepository employeeRepository;


    //>>>>>>>>>>>>>>>util functions>>>>>>>>>
    private ApiResponse utilUpdateStatus(String approved,long employeeId,LocalDate date){
        int updateStatus = leaveRepository.updateLeaveStatus(approved, employeeId, date);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(new ArrayList<>())
                .success(Boolean.TRUE)
                .status(HttpStatus.OK)
                .errors(new ArrayList<>())
                .message(AppConstants.UPDATE_SUCCESS)
                .build();
        if (updateStatus == 0) {
            apiResponse.setMessage(AppConstants.RETRIEVAL_FAIL);
            apiResponse.setErrors(Arrays.asList(AppConstants.RETRIEVAL_FAIL));
            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
            apiResponse.setSuccess(Boolean.FALSE);
        }
        return apiResponse;
    }
    //>>>>>>>>>>>>>>>util functions>>>>>>>>>


    @Transactional
    public ApiResponse applyLeave(long employeeId, LeaveRequest leaveRequest) throws BadRequestException, ResourceNotFoundException {
        if (leaveRequest.getDate().isBefore(LocalDate.now())) {
            throw new BadRequestException(AppConstants.LEAVE_NOT_ALLOWED);
        }

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> {
            throw new ResourceNotFoundException("Employee not found with id: " + employeeId);
        });

        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .errors(new ArrayList<>())
                .success(Boolean.TRUE)
                .build();

        Attendance attendance = attendanceRepository.checkEmployeeAttendanceExists(employeeId, Date.valueOf(leaveRequest.getDate()));
        Leave leave = Leave.builder()
                .date(leaveRequest.getDate())
                .reason(leaveRequest.getReason())
                .status(LeaveStatus.PENDING)
                .employee(employee)
                .build();
        if (attendance != null && attendance.getLeave() != null) {
            throw new BadRequestException("Leave already applied for the given date: " + leaveRequest.getDate());

        }
        if (attendance != null) {
            //if employee is already marked present or absent-> append leave id there
            leave.setAttendance(attendance);
//            attendanceRepository.save(attendance);
            Leave save = leaveRepository.save(leave);
            attendanceRepository.updateLeaveId(save.getId(), employeeId, Date.valueOf(leaveRequest.getDate()));
            apiResponse.setMessage(AppConstants.LEAVE_APPLIED);
            return apiResponse;
        }
        Attendance toSaveAttendance = Attendance.builder()
                .leave(leave)
                .employee(employee)
                .date(leaveRequest.getDate())
                .build();
        Attendance save = attendanceRepository.save(toSaveAttendance);
        apiResponse.setMessage(AppConstants.LEAVE_APPLIED + " on" + leaveRequest.getDate());
        //if employee is not marked-> create an attendance record with the leave id appended
        return apiResponse;
    }

    @Transactional
    public ApiResponse deleteLeave(long employeeId, AttendanceRequest attendanceRequest) {
        attendanceRepository.deleteLeave(employeeId, Date.valueOf(attendanceRequest.getDate()));
        leaveRepository.deleteLeave(employeeId, Date.valueOf(attendanceRequest.getDate()));
        return ApiResponse.builder()
                .message("Deleted leave request")
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Transactional
    public ApiResponse approveLeave(long employeeId, AttendanceRequest date) {
        return utilUpdateStatus(LeaveStatus.APPROVED.toString(), employeeId, date.getDate());

    }

    @Transactional
    public ApiResponse rejectLeave(long employeeId, AttendanceRequest date) {
        return utilUpdateStatus(LeaveStatus.REJECTED.toString(), employeeId, date.getDate());
    }

}
