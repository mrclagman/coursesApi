package com.marklagman.spring.jpa.h2.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<Object> createInstructor(@RequestBody Instructor instructor) {
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

	@GetMapping("/{id}")
	public ResponseEntity<Instructor> getInstructorById(@PathVariable (value = "id") Long instructorId){

		Optional<Instructor> instructor = instructorRepository.findById(instructorId);

		if (instructor.isPresent()) {
			return new ResponseEntity<>(instructor.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateInstructor (@PathVariable("id") long id, @RequestBody Instructor instructor) {
		Map <String, Object> map = new HashMap<>();

		Optional<Instructor> instructorData = instructorRepository.findById(id);

		if (instructorData.isPresent()) {
			Instructor _inst = instructorData.get();
			_inst.setFullName(instructor.getFullName());
			_inst.setDepartment(instructor.getDepartment());
			instructorRepository.save(_inst);

			map.put("message", "Instructor updated successfully!");
			map.put("id", _inst.getId());

			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteInstructor(@PathVariable("id") long instructorId) {
		Optional<Instructor> inst = instructorRepository.findById(instructorId);
		Map <String, String> map = new HashMap<>();


		if (inst.isPresent()) {
			instructorRepository.deleteById(instructorId);

			map.put("message", "Instructor deleted successfully!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			map.put("message", "Entity not found!");
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}
	}
}
