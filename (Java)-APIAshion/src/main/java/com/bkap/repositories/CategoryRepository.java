package com.bkap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bkap.entities.CategoryProduct;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryProduct, Integer>,JpaSpecificationExecutor<CategoryProduct> {
	CategoryProduct findBySlug(String slug );
	CategoryProduct findByCategoryName(String categoryName);
	CategoryProduct findByDisplayNo(int displayNo);
}
