package com.bkap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bkap.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>,JpaSpecificationExecutor<Product> {
//	List<ColorSizeProduct> findByColorSizeProduct(int colorSizeProductId );
	 Product findByProductName (String productName);
}
