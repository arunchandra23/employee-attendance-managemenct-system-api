package com.arun.eamsrest.entity.attendance;

import com.arun.eamsrest.entity.Employee;
import com.arun.eamsrest.entity.Leave;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @Id
    @GeneratedValue
    private long id;

    @DateTimeFormat(pattern="dd-MM-yyyy")
    private LocalDate date;
//    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "leave_id")
    private Leave leave;


}
