package com.bkap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bkap.entities.CommentBlog;

public interface CommentRepository extends JpaRepository<CommentBlog, Integer>,JpaSpecificationExecutor<CommentBlog> {}
