package com.arun.eamsrest.service;

import com.arun.eamsrest.entity.Employee;
import com.arun.eamsrest.entity.Leave;
import com.arun.eamsrest.entity.attendance.Attendance;
import com.arun.eamsrest.entity.attendance.AttendanceStatus;
import com.arun.eamsrest.exception.BadRequestException;
import com.arun.eamsrest.exception.ResourceNotFoundException;
import com.arun.eamsrest.payload.ApiResponse;
import com.arun.eamsrest.payload.request.AttendanceRequest;
import com.arun.eamsrest.repository.AttendanceRepository;
import com.arun.eamsrest.repository.EmployeeRepository;
import com.arun.eamsrest.utils.AppConstants;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    private ModelMapper modelMapper =new ModelMapper();
    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public ApiResponse markPresent(long employeeId, AttendanceRequest attendanceRequest) throws BadRequestException,ResourceNotFoundException {
        Attendance attendance = attendanceRepository.checkEmployeeAttendanceExists(employeeId,Date.valueOf(attendanceRequest.getDate()));
        ApiResponse apiResponse=ApiResponse.builder()
                .success(Boolean.TRUE)
                .errors(new ArrayList<>())
                .data(new ArrayList<>())
                .build();
        if(attendance!=null){
            //Update the existing attendance here
            if(attendance.getStatus()==AttendanceStatus.PRESENT){
                throw new BadRequestException("Employee with id: "+employeeId+" is already marked "+AttendanceStatus.PRESENT);
            }
            if(attendance.getStatus()==AttendanceStatus.ABSENT){
                attendanceRepository.changeAttendanceStatus(employeeId,Date.valueOf(attendanceRequest.getDate()),AttendanceStatus.PRESENT.ordinal());
                apiResponse.setMessage(AppConstants.ATTENDANCE_STATUS_UPDATED);
                apiResponse.setStatus(HttpStatus.OK);
                return apiResponse;
            }


        }
        //This is the logic for first entry

        Employee employee=employeeRepository.findById(employeeId).orElseThrow(()->{
           throw new ResourceNotFoundException("Employee with id: "+employeeId+" is not found!");
        });
        Attendance toSaveAttendance=Attendance.builder()
                .employee(employee)
                .date(attendanceRequest.getDate())
                .status(AttendanceStatus.PRESENT)
                .build();
        Attendance save = attendanceRepository.save(toSaveAttendance);
        apiResponse.setStatus(HttpStatus.CREATED);
        apiResponse.setMessage(AppConstants.ATTENDANCE_STATUS_CREATED);
        return apiResponse;

    }


    @Transactional
    public ApiResponse markAbsent(long employeeId, AttendanceRequest attendanceRequest) throws BadRequestException,ResourceNotFoundException {
        Attendance attendance = attendanceRepository.checkEmployeeAttendanceExists(employeeId,Date.valueOf(attendanceRequest.getDate()));
        ApiResponse apiResponse=ApiResponse.builder()
                .success(Boolean.TRUE)
                .errors(new ArrayList<>())
                .data(new ArrayList<>())
                .build();
        if(attendance!=null){
            //Update the existing attendance here
            if(attendance.getStatus()==AttendanceStatus.ABSENT){
                throw new BadRequestException("Employee with id: "+employeeId+" is already marked "+AttendanceStatus.ABSENT);
            }
            if(attendance.getStatus()==AttendanceStatus.PRESENT){
                attendanceRepository.changeAttendanceStatus(employeeId, Date.valueOf(attendanceRequest.getDate()),AttendanceStatus.ABSENT.ordinal());
                apiResponse.setMessage(AppConstants.ATTENDANCE_STATUS_UPDATED);
                apiResponse.setStatus(HttpStatus.OK);
                return apiResponse;
            }

        }
        //This is the logic for first entry

        Employee employee=employeeRepository.findById(employeeId).orElseThrow(()->{
            throw new ResourceNotFoundException("Employee with id: "+employeeId+" is not found!");
        });
        Attendance toSaveAttendance=Attendance.builder()
                .employee(employee)
                .date(attendanceRequest.getDate())
                .status(AttendanceStatus.ABSENT)
                .build();
        Attendance save = attendanceRepository.save(toSaveAttendance);
        apiResponse.setStatus(HttpStatus.CREATED);
        apiResponse.setMessage(AppConstants.ATTENDANCE_STATUS_CREATED);
        return apiResponse;

    }

}
