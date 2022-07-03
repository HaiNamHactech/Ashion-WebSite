package com.bkap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bkap.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer>,JpaSpecificationExecutor<Role> {
	Role findByName(String roleName);
}
