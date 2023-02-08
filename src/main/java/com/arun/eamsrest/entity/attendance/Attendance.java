package com.arun.eamsrest.entity.attendance;

import com.arun.eamsrest.entity.Employee;
import com.arun.eamsrest.entity.Leave;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {

    @Id
    @GeneratedValue
    private long id;

    @DateTimeFormat(pattern="dd-MM-yyyy")
    private LocalDate date;
    private AttendanceStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "leave_id")
    private Leave leave;


}
