package com.arun.eamsrest.service;

import com.arun.eamsrest.entity.Employee;
import com.arun.eamsrest.payload.request.LoginRequest;
import com.arun.eamsrest.payload.response.EmployeeResponse;
import com.arun.eamsrest.payload.response.LoginResponse;
import com.arun.eamsrest.security.CustomUserDetailService;
import com.arun.eamsrest.security.JwtService;
import com.arun.eamsrest.security.UserPrinciple;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtService jwtService;


    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        UserDetails user=customUserDetailService.loadUserByUsername(request.getEmail());
        log.info("UserDetails {}",user.getUsername());
        String jwtToken=jwtService.generateToken(user);
        return LoginResponse.builder()
                .token(jwtToken)
                .build();

    }
}
