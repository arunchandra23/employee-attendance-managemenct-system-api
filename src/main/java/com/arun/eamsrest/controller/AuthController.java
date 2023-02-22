package com.arun.eamsrest.controller;

import com.arun.eamsrest.payload.request.LoginRequest;
import com.arun.eamsrest.payload.response.LoginResponse;
import com.arun.eamsrest.service.AuthService;
import com.arun.eamsrest.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppConstants.BASE_URL+"/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        return new ResponseEntity<>(authService.login(request), HttpStatus.OK);

    }
    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> signup(@RequestBody LoginRequest request){
        return new ResponseEntity<>(authService.login(request), HttpStatus.OK);

    }


}
