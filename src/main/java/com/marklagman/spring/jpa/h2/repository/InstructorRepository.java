package com.marklagman.spring.jpa.h2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marklagman.spring.jpa.h2.model.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Long>{

    List<Instructor> findByFullNameContaining(String fullName);

    List<Instructor> findAll();

    
}
