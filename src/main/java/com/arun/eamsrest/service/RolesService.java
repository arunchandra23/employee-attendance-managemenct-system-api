package com.arun.eamsrest.service;

import com.arun.eamsrest.entity.role.Role;
import com.arun.eamsrest.entity.role.RoleName;
import com.arun.eamsrest.exception.BadRequestException;
import com.arun.eamsrest.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RolesService {

    @Autowired
    private RoleRepository roleRepository;
    private ModelMapper modelMapper = new ModelMapper();

    public List<Role> addRoles() {

        if (roleRepository.count() == 3) throw new BadRequestException("Roles added already!");
        Role roleUser = Role.builder()
                .name(RoleName.ROLE_USER)
                .build();
        Role roleHr = Role.builder()
                .name(RoleName.ROLE_HR)
                .build();
        Role roleAdmin = Role.builder()
                .name(RoleName.ROLE_ADMIN)
                .build();
        List<Role> roles = new ArrayList<>(Arrays.asList(roleHr, roleAdmin, roleUser));

        List<Role> roles1 = roleRepository.saveAll(roles);

        return roles1;

    }
}
