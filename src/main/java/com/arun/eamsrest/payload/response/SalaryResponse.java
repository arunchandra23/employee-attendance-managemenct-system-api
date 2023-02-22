package com.arun.eamsrest.payload.response;

import com.arun.eamsrest.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalaryResponse {
    private long amount;
    private Department department;
}
