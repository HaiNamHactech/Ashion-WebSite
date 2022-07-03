package com.bkap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bkap.entities.Color;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer>,JpaSpecificationExecutor<Color>{
	Color findByColorName(String colorName);
	Color findByCode(String code);
}
