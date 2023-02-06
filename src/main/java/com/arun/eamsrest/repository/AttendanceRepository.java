package com.arun.eamsrest.repository;

import com.arun.eamsrest.entity.attendance.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
}
