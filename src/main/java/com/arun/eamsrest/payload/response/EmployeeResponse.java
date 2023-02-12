package com.arun.eamsrest.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeResponse {

    private String firstName;
    private String lastName;
    private String gender;
    private long age;
    private String email;
    private String password;
    private SalaryResponse salary;
    private JobResponse job;
}
