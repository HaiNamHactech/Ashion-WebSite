package com.bkap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bkap.entities.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer>,JpaSpecificationExecutor<Users> {
	Users findByFirstName(String firstName);
	
	Users findByEmail(String email);

}
