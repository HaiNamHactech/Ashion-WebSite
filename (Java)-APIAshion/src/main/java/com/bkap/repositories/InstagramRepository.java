package com.bkap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bkap.entities.Instagram;

@Repository
public interface InstagramRepository extends JpaRepository<Instagram, Integer>,JpaSpecificationExecutor<Instagram> {

}
