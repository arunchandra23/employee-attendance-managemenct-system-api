package com.arun.eamsrest.service;

import com.arun.eamsrest.entity.Department;
import com.arun.eamsrest.payload.ApiResponse;
import com.arun.eamsrest.payload.request.DepartmentRequest;
import com.arun.eamsrest.repository.DepartmentRepository;
import com.arun.eamsrest.utils.AppConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;
    private ModelMapper modelMapper = new ModelMapper();

    public Department addDepartment(DepartmentRequest departmentRequest) {

        Department dep = modelMapper.map(departmentRequest, Department.class);
        Department save = departmentRepository.save(dep);
        return save;
    }

    public ApiResponse getAllDepartments() {
        List<Department> all = departmentRepository.findAll();
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message(AppConstants.RETRIEVAL_SUCCESS)
                .success(Boolean.TRUE)
                .errors(new ArrayList<>())
                .data(all)
                .build();
        return apiResponse;
    }
}
