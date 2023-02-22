package com.arun.eamsrest.repository;

import com.arun.eamsrest.entity.role.Role;
import com.arun.eamsrest.entity.role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName roleUser);
}
