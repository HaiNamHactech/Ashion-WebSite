package com.bkap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bkap.entities.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer>,JpaSpecificationExecutor<UserRole>{

}
