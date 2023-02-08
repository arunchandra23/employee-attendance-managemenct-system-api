package com.arun.eamsrest.entity;

import com.arun.eamsrest.entity.attendance.Attendance;
import com.arun.eamsrest.utils.LeaveStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Leave {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private LocalDate date;

    private String reason;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    @OneToOne(mappedBy = "leave",cascade = CascadeType.ALL)
    private Attendance attendance;


}
