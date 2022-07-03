package com.bkap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bkap.entities.CategoryBlog;

@Repository
public interface CategoryBlogRepository extends JpaRepository<CategoryBlog ,Integer>,JpaSpecificationExecutor<CategoryBlog>  {
	CategoryBlog findByCategoryName(String categoryName);
	CategoryBlog findBySlug(String slug);
}
