package com.arun.eamsrest.repository;

import com.arun.eamsrest.entity.Leave;
import com.arun.eamsrest.payload.request.AttendanceRequest;
import com.arun.eamsrest.utils.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;

@Repository
public interface LeaveRepository extends JpaRepository<Leave,Long> {

    @Modifying
    @Query(nativeQuery = true,value = "DELETE FROM leave WHERE employee_id=:id AND date =:date")
    void deleteLeave(@Param("id") long employeeId, @Param("date") Date date);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,value = "UPDATE leave SET status=:status WHERE employee_id=:id AND date =:date")
    int updateLeaveStatus(@Param("status") String approved,@Param("id") long employeeId,@Param("date") LocalDate date);


}
