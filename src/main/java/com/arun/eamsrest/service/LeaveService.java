package com.arun.eamsrest.service;

import com.arun.eamsrest.entity.Employee;
import com.arun.eamsrest.entity.Leave;
import com.arun.eamsrest.entity.attendance.Attendance;
import com.arun.eamsrest.entity.attendance.AttendanceStatus;
import com.arun.eamsrest.exception.BadRequestException;
import com.arun.eamsrest.exception.ResourceNotFoundException;
import com.arun.eamsrest.payload.ApiResponse;
import com.arun.eamsrest.payload.request.LeaveRequest;
import com.arun.eamsrest.repository.AttendanceRepository;
import com.arun.eamsrest.repository.EmployeeRepository;
import com.arun.eamsrest.repository.LeaveRepository;
import com.arun.eamsrest.utils.AppConstants;
import com.arun.eamsrest.utils.LeaveStatus;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class LeaveService {
    @Autowired
    private LeaveRepository leaveRepository;

    private ModelMapper modelMapper =new ModelMapper();
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private EmployeeRepository employeeRepository;


    @Transactional
    public ApiResponse applyLeave(long employeeId, LeaveRequest leaveRequest) throws BadRequestException,ResourceNotFoundException {
        if(leaveRequest.getDate().equals(LocalDate.now()) || LocalDate.now().isAfter(leaveRequest.getDate())){
            throw new BadRequestException(AppConstants.LEAVE_NOT_ALLOWED);
        }
        Employee employee =employeeRepository.findById(employeeId).orElseThrow(()->{
            throw new ResourceNotFoundException("Employee not found with id: "+employeeId);
        });
        Attendance attendance = attendanceRepository.checkEmployeeAttendanceExists(employeeId, Date.valueOf(leaveRequest.getDate()));
        Leave leave= Leave.builder()
                .date(leaveRequest.getDate())
                .reason(leaveRequest.getReason())
                .status(LeaveStatus.PENDING)
                .employee(employee)
                .build();
        if(attendance!=null){
            //if employee is already marked present or absent-> append leave id there
            leave.setAttendance(attendance);
//            attendanceRepository.save(attendance);
            Leave save = leaveRepository.save(leave);
            attendanceRepository.updateLeaveId(save.getId(),employeeId,Date.valueOf(leaveRequest.getDate()));
            return null;
        }
        Attendance toSaveAttendance=Attendance.builder()
                .leave(leave)
                .employee(employee)
                .date(leaveRequest.getDate())
                .build();
        Attendance save = attendanceRepository.save(toSaveAttendance);
        ApiResponse apiResponse =ApiResponse.builder()
                .data(save)
                .build();
        //if employee is not marked-> create an attendance record with the leave id appended
        return null;
    }
}
