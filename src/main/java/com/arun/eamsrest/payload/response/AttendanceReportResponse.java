package com.arun.eamsrest.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceReportResponse {
    private long totalDays;
    private long presentDays;
    private long leaveDays;
    private long absentDays;
    private long salary;
}
