package com.marklagman.spring.jpa.h2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marklagman.spring.jpa.h2.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{
    List<Course> findByFull(boolean full);

    List<Course> findByTitleContaining(String title);
    
}
