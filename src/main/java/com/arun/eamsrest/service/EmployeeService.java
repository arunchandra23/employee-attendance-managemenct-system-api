package com.arun.eamsrest.service;


import com.arun.eamsrest.entity.Department;
import com.arun.eamsrest.entity.Employee;
import com.arun.eamsrest.entity.Job;
import com.arun.eamsrest.entity.Salary;
import com.arun.eamsrest.entity.attendance.Attendance;
import com.arun.eamsrest.entity.attendance.AttendanceStatus;
import com.arun.eamsrest.entity.role.Role;
import com.arun.eamsrest.entity.role.RoleName;
import com.arun.eamsrest.exception.ResourceNotFoundException;
import com.arun.eamsrest.payload.ApiResponse;
import com.arun.eamsrest.payload.request.EmployeeRequest;
import com.arun.eamsrest.payload.response.AttendanceReportResponse;
import com.arun.eamsrest.payload.response.EmployeeResponse;
import com.arun.eamsrest.payload.response.JobResponse;
import com.arun.eamsrest.payload.response.SalaryResponse;
import com.arun.eamsrest.repository.AttendanceRepository;
import com.arun.eamsrest.repository.DepartmentRepository;
import com.arun.eamsrest.repository.EmployeeRepository;
import com.arun.eamsrest.repository.RoleRepository;
import com.arun.eamsrest.utils.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private RoleRepository roleRepository;
    private ModelMapper modelMapper=new ModelMapper();
    @Autowired
    private AttendanceRepository attendanceRepository;


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
        Set<Role> roles=new HashSet<>();
        roles.add(
                roleRepository.findByName(RoleName.ROLE_USER)
        );
        Employee emp1=Employee.builder()
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .age(employee.getAge())
                .email(employee.getEmail())
                .password(employee.getPassword())
                .gender(employee.getGender())
                .salary(salary)
                .roles(roles)
                .department(department)
                .build();
        Employee emp =employeeRepository.save(emp1);
        return emp;
    }

    public ApiResponse getAllEmployees() {
        List<EmployeeResponse> all=new ArrayList<>();
        employeeRepository.findAll().forEach((employee)->{

            SalaryResponse salary=SalaryResponse.builder()
                    .amount(employee.getSalary().getAmount())
                    .department(modelMapper.map(employee.getSalary().getDepartment(),Department.class))
                    .build();
            JobResponse jobResponse =JobResponse.builder()
                    .title(employee.getSalary().getJob().getTitle())
                    .build();
            EmployeeResponse emp=EmployeeResponse.builder()
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .gender(employee.getGender())
                    .age(employee.getAge())
                    .email(employee.getEmail())
                    .password(employee.getPassword())
                    .salary(salary)
                    .job(jobResponse)
                    .build();
            all.add(emp);
        });


        ApiResponse apiResponse=ApiResponse.builder()
                .success(Boolean.TRUE)
                .status(HttpStatus.OK)
                .message(AppConstants.RETRIEVAL_SUCCESS)
                .errors(new ArrayList<>())
                .data(all)
                .build();
        return apiResponse;
    }

    public Employee updateEmployee(long employeeId, EmployeeRequest employee) {

        Employee employee1=employeeRepository.findById(employeeId).orElseThrow(()->{
            throw new ResourceNotFoundException("Employee not found with id: "+employeeId);
        });
        Salary salary=employee1.getSalary();
        salary.setAmount(employee.getAmount());
        salary.getJob().setTitle(employee.getTitle());

        employee1.setFirstName(employee.getFirstName());
        employee1.setLastName(employee.getLastName());
        employee1.setGender(employee.getGender());
        employee1.setAge(employee.getAge());
        employee1.setEmail(employee.getEmail());
        employee1.setPassword(employee.getPassword());
        employee1.setSalary(salary);

        return employeeRepository.save(employee1);

    }

    public ApiResponse getEmployeeByDepartmentId(long departmentId) {
        List<Employee> employees = employeeRepository.findByDepartment_id(departmentId);
        ApiResponse apiResponse= ApiResponse.builder()
                .success(Boolean.TRUE)
                .status(HttpStatus.OK)
                .message(AppConstants.RETRIEVAL_SUCCESS)
                .data(employees)
                .errors(new ArrayList<>())
                .build();
        return apiResponse;
    }

    public ApiResponse getEmployeeAttendanceBetweenDates(long employeeId, LocalDate from, LocalDate to) {
        System.out.println(">>>>>>>"+employeeId+from+to);
        List<Attendance> attendances = attendanceRepository.findByEmployee_idAndDateBetween(employeeId,from, to);
        ApiResponse apiResponse= ApiResponse.builder()
                .errors(new ArrayList<>())
                .success(Boolean.TRUE)
                .data(attendances)
                .status(HttpStatus.OK)
                .message(AppConstants.RETRIEVAL_SUCCESS)
                .build();
        if(attendances.size()==0){
            apiResponse.setMessage(AppConstants.NO_DATA_FOUND);
        }
        return apiResponse;
    }

    public ApiResponse getEmployeeAttendanceReport(long employeeId, LocalDate from, LocalDate to) {
        List<Attendance> attendanceList = attendanceRepository.findByEmployee_idAndDateBetween(employeeId, from,to);
        ApiResponse apiResponse=ApiResponse.builder()
                .status(HttpStatus.OK)
                .errors(new ArrayList<>())
                .success(Boolean.TRUE)
                .build();
        if(attendanceList.size()==0){
            apiResponse.setData(new ArrayList<>());
            apiResponse.setMessage(AppConstants.NO_DATA_FOUND);
            return apiResponse;
        }
        long p=attendanceList.stream().filter(x->
                x.getStatus() == AttendanceStatus.PRESENT).count();
        long a=attendanceList.stream().filter(x->
                x.getStatus() == AttendanceStatus.ABSENT).count();
        long l=attendanceList.stream().filter(x->
                x.getLeave() != null).count();

        AttendanceReportResponse attendanceReportResponse=AttendanceReportResponse.builder()
                .totalDays(attendanceList.size())
                .presentDays(p)
                .absentDays(a)
                .leaveDays(l)
                .salary((employeeRepository.findById(employeeId).get().getSalary().getAmount()/30)*p)
                .build();
        apiResponse.setData(attendanceReportResponse);
        apiResponse.setMessage(AppConstants.RETRIEVAL_SUCCESS);
        return apiResponse;

    }

//    @Transactional
//    public ApiResponse deleteEmployee(long employeeId) {
//        employeeRepository.deleteById(employeeId);
//        attendanceRepository.deleteByEmployee(employeeId);
//        ApiResponse apiResponse=ApiResponse.builder()
//                .message("Delete successful")
//                .build();
//        return apiResponse;
//    }
}
