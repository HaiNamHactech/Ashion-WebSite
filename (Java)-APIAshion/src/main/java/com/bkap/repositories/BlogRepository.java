package com.bkap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bkap.entities.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer>,JpaSpecificationExecutor<Blog> {

}
