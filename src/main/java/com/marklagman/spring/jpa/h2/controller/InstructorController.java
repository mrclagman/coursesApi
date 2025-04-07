package com.marklagman.spring.jpa.h2.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marklagman.spring.jpa.h2.model.Course;
import com.marklagman.spring.jpa.h2.model.Instructor;
import com.marklagman.spring.jpa.h2.repository.InstructorRepository;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {
    
    @Autowired
    private InstructorRepository instructorRepository;

    @GetMapping("")
    public ResponseEntity<List<Instructor>> getAllInstructors(@RequestParam(required = false) String name) {

        System.out.println("GET /api/instructors");

        try {
			
            List<Instructor> instructors = new ArrayList<Instructor>();

			if (name == null) {
                instructorRepository.findAll().forEach(instructors::add);
                System.out.println(instructors);
            } else {
                instructorRepository.findByFullNameContaining(name).forEach(instructors::add);
            }


			if (instructors.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(instructors, HttpStatus.OK);

		} catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    @PostMapping("")
	public ResponseEntity<Object> createCourse(@RequestBody Instructor instructor) {
		Map <String, Object> map = new HashMap<>();

		try {
			Instructor _instructor = instructorRepository
					.save(new Instructor(instructor.getFullName(), instructor.getDepartment()));

			map.put("message", "Instructor Added successfully!");
			map.put("id", _instructor.getId());

			return new ResponseEntity<>(map, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
