package com.bkap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bkap.entities.Slider;

public interface SliderRepository extends JpaRepository<Slider, Integer>,JpaSpecificationExecutor<Slider> {

}
