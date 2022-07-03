package com.bkap.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bkap.entities.ColorSizeProduct;

@Repository
public interface ColorSizeRepository extends JpaRepository<ColorSizeProduct, Integer>,JpaSpecificationExecutor<ColorSizeProduct> {
	List<ColorSizeProduct> findByColorColorNameIn(Collection<String> colorName);
	List<ColorSizeProduct> findBySizeSizeNameIn(Collection<String> sizeName);
}
