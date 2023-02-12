package com.arun.eamsrest.repository;

import com.arun.eamsrest.entity.attendance.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {


    @Query(value = "SELECT * FROM attendance WHERE employee_id=:id AND date =:date",nativeQuery = true)
    Attendance checkEmployeeAttendanceExists(@Param("id") long employeeId,@Param("date") Date date);

    @Modifying
    @Query(value = "UPDATE attendance SET status=:status where employee_id=:id AND date =:date",nativeQuery = true)
    int changeAttendanceStatus(@Param("id") long employeeId,@Param("date") Date date, @Param("status") int ordinal);

    @Modifying
    @Query(value = "UPDATE attendance SET leave_id=:leaveId,status=null where employee_id=:id AND date =:date",nativeQuery = true)
    void updateLeaveId(@Param("leaveId") long id,@Param("id") long employeeId, @Param("date") Date date);



    @Modifying
    @Query(nativeQuery = true,value = "UPDATE attendance SET leave_id=null,status=0 WHERE employee_id=:id AND date =:date")
    void deleteLeave(@Param("id") long employeeId,@Param("date") Date date);

    List<Attendance> findByDateBetween(LocalDate from, LocalDate to);

    List<Attendance> findByEmployee_idAndDateBetween(long employeeId,LocalDate from, LocalDate to);
}
