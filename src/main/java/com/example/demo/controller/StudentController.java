package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;

@RestController
@CrossOrigin(origins = "*")
public class StudentController {
	@Autowired
	StudentRepository studentRepository;

	@GetMapping("/students")
	public ResponseEntity<Object> getStudents() {
		try {
			List<Student> students = studentRepository.findAll();
			return new ResponseEntity<>(students, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>("Internal Sever Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/students")
	public ResponseEntity<Object> addStudents(@RequestBody Student body) {
		try {
			Student student = studentRepository.save(body);
			return new ResponseEntity<>(student, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Internal Sever Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/students/{id}")
	public ResponseEntity<Object> updatStudents(@PathVariable Integer id, @RequestBody Student body) {
		Optional<Student> student = studentRepository.findById(id);

		try {
			if (student.isPresent()) {
				student.get().setStdId(body.getStdId());
				student.get().setStdName(body.getStdName());
				student.get().setStdSurName(body.getStdSurName());
				student.get().setStdEmail(body.getStdEmail());
				studentRepository.save(student.get());
				return new ResponseEntity<>(student.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Student not found", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Internal Sever Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/students/{id}")
	public ResponseEntity<Object> deleteStudents(@PathVariable Integer id) {
		Optional<Student> student = studentRepository.findById(id);
		try {
			if (student.isPresent()) {
				studentRepository.delete(student.get());
				return new ResponseEntity<>("Deleted successful", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Student not found", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Internal Sever Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
