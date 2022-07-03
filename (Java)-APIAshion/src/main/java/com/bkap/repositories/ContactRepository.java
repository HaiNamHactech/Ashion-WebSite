package com.bkap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bkap.entities.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact ,Integer>,JpaSpecificationExecutor<Contact>  {

}
