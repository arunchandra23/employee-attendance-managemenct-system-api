package com.arun.eamsrest.entity;

import com.arun.eamsrest.entity.attendance.Attendance;
import com.arun.eamsrest.utils.LeaveStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Leave {

    @JsonIgnore
    @Id
    @GeneratedValue
    private long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private LocalDate date;

    private String reason;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    @JsonIgnore
    @OneToOne(mappedBy = "leave", cascade = CascadeType.ALL)
    private Attendance attendance;


}
