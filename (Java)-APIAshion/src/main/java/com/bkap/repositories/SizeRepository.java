package com.bkap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bkap.entities.Size;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer>,JpaSpecificationExecutor<Size> {
	Size findBySizeName(String sizeName);
}
