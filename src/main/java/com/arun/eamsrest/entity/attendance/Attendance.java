package com.arun.eamsrest.entity.attendance;

import com.arun.eamsrest.entity.Employee;
import com.arun.eamsrest.entity.Leave;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {

    @Id
    private long id;

    private Date date;
    private AttendanceStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "leave_id")
    private Leave leave;


}
