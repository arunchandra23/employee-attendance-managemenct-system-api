package com.arun.eamsrest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {

    @JsonIgnore
    @Id
    @GeneratedValue
    private long id;

    private String title;

    @JsonIgnore
    @OneToOne(mappedBy = "job")
    private Salary salary;


}
