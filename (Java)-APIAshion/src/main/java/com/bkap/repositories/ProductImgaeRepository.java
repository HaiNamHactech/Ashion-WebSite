package com.bkap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bkap.entities.ProductImage;

@Repository
public interface ProductImgaeRepository extends JpaRepository<ProductImage, Integer>,JpaSpecificationExecutor<ProductImage>  {

}
