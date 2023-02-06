package com.arun.eamsrest.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeRequest {

    @NotBlank(message = "First name should not be blank or null")
    private String firstName;
    @NotBlank(message = "Last name should not be blank or null")
    private String lastName;

    @NotBlank(message = "Gender should not be blank or null")
    private String gender;
    @NotNull(message = "Age should not be blank or null, minimum age is 18")
    @Min(18)
    private long age;

    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password should not be blank or null")
    private String password;

    @NotNull(message = "Salary should not be blank or null")
    @JsonProperty("salaryAmount")
    private long amount;

    @NotBlank(message = "Job title should not be blank or null")
    @JsonProperty("jobTitle")
    private String title;
}
