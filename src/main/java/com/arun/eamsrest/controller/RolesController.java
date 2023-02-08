package com.arun.eamsrest.controller;

import com.arun.eamsrest.entity.Department;
import com.arun.eamsrest.entity.role.Role;
import com.arun.eamsrest.payload.request.DepartmentRequest;
import com.arun.eamsrest.service.RolesService;
import com.arun.eamsrest.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(AppConstants.BASE_URL+"/roles")
public class RolesController {


    @Autowired
    private RolesService rolesService;

    @PostMapping()
    public ResponseEntity<List<Role>> addRoles(){
        List<Role> roles = rolesService.addRoles();
        return  new ResponseEntity<>(roles, HttpStatus.CREATED);
    }

}
