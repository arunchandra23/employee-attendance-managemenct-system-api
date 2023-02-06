package com.arun.eamsrest.service;

import com.arun.eamsrest.entity.Department;
import com.arun.eamsrest.payload.request.DepartmentRequest;
import com.arun.eamsrest.repository.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;
    private ModelMapper modelMapper =new ModelMapper();

    public Department addDepartment(DepartmentRequest departmentRequest) {

        Department dep=modelMapper.map(departmentRequest,Department.class);
        Department save = departmentRepository.save(dep);
        return save;
    }
}
