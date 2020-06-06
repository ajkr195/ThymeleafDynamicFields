package com.thymeleaf.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thymeleaf.demo.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository <Contact, Long>{
}
