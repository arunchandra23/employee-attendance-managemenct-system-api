package com.arun.eamsrest.repository;

import com.arun.eamsrest.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartment_id(long departmentId);

    long findSalaryAmountById(long id);

    Employee findByEmail(String email);
}
