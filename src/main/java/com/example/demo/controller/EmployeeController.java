package com.example.demo.controller;

import java.util.Iterator;
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

import com.example.demo.model.Employee;
import com.example.demo.model.Role;
import com.example.demo.model.Skill;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.SkillRepository;

@RestController
@CrossOrigin(origins = "*")
public class EmployeeController {
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	SkillRepository skillRepository;

	@GetMapping("/employee")
	public ResponseEntity<Object> getEmployee() {
		List<Employee> employees = employeeRepository.findAll();
		try {
			return new ResponseEntity<>(employees, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Internal Sever Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/employee/{id}")
	public ResponseEntity<Object> getEmployee(@PathVariable Integer id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		try {
			if (employee.isPresent()) {
				return new ResponseEntity<>(employee.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Employee not found!", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Internal Sever Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/employee")
	public ResponseEntity<Object> addEmployee(@RequestBody Employee body) {
		try {
			Optional<Role> role = roleRepository.findById(4);
			body.setRole(role.get());
			Employee employee = employeeRepository.save(body);
			for (Skill skill : body.getSkills()) {
				skill.setEmployee(employee);
				skillRepository.save(skill);
			}
			return new ResponseEntity<>(employee, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Internal Sever Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/employee/{id}")
	public ResponseEntity<Object> updatEmployee(@PathVariable Integer id, @RequestBody Employee body) {
		Optional<Employee> employee = employeeRepository.findById(id);

		try {
			if (employee.isPresent()) {
				employee.get().setFirstName(body.getFirstName());
				employee.get().setLastName(body.getLastName());
				employee.get().setSalary(body.getSalary());
				employeeRepository.save(employee.get());
				return new ResponseEntity<>(employee.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Client Error", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Internal Sever Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/employee/{id}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable Integer id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		try {
			if (employee.isPresent()) {
				employeeRepository.delete(employee.get());
				return new ResponseEntity<>("Deleted", HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>("Client Error", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Internal Sever Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
