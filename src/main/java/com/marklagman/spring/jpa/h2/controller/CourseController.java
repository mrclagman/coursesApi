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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marklagman.spring.jpa.h2.model.Course;
import com.marklagman.spring.jpa.h2.repository.CourseRepository;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("")
    public ResponseEntity<List<Course>> getAllCourses(@RequestParam(required = false) String title) {
		// return courseRepository.findAll();
		try {
			List<Course> courses = new ArrayList<Course>();

			if (title == null)
				courseRepository.findAll().forEach(courses::add);
			else
				courseRepository.findByTitleContaining(title).forEach(courses::add);

			if (courses.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(courses, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Course> getCourseById(@PathVariable (value = "id") Long courseId){
		// return courseService.getCourseById(id);

		// return courseRepository.findById(courseId).orElse(null);

		Optional<Course> course = courseRepository.findById(courseId);

		if (course.isPresent()) {
			return new ResponseEntity<>(course.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("")
	public ResponseEntity<Object> createCourse(@RequestBody Course course) {
		Map <String, Object> map = new HashMap<>();

		try {
			Course _course = courseRepository
					.save(new Course(course.getTitle(), course.getDescription(), course.isFull()));

			map.put("message", "Course Added successfully!");
			map.put("id", _course.getId());

			return new ResponseEntity<>(map, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateCourse (@PathVariable("id") long id, @RequestBody Course course) {
		Map <String, Object> map = new HashMap<>();

		Optional<Course> courseData = courseRepository.findById(id);

		if (courseData.isPresent()) {
			Course _course = courseData.get();
			_course.setTitle(course.getTitle());
			_course.setDescription(course.getDescription());
			_course.setFull(course.isFull());

			map.put("message", "Course updated successfully!");
			map.put("id", _course.getId());

			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteCourse(@PathVariable("id") long courseId) {
		Optional<Course> course = courseRepository.findById(courseId);
		Map <String, String> map = new HashMap<>();


		if (course.isPresent()) {
			courseRepository.deleteById(courseId);

			map.put("message", "Course deleted successfully!");


			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			map.put("message", "Entity not found!");
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/full")
	public ResponseEntity<List<Course>> findFull() {
		try {
			List<Course> courses = courseRepository.findByFull(true);

			if (courses.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(courses, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
    

}
