package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;

@RestController
public class EmployeeController {
	private List<Employee> data = new ArrayList<Employee>();

	@GetMapping("/employee")
	public List<Employee> getEmployee() {
		return data;
	}
	@GetMapping("/employee/{id}")
	public Employee getEmployee(@PathVariable Integer id) {
		System.out.print("Employee Id="+id);
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getEmployeeId() == id) {
				return data.get(i);
			}
		}
		return null;
	}

	@PostMapping("/employee")
	public Employee addEmployee(@RequestBody Employee body) {
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getEmployeeId() == body.getEmployeeId()) {
				return null;
			}
		}

		data.add(body);
		return body;
	}
	
	@PutMapping("/employee/{id}")
	public Employee updatEmployee(@PathVariable Integer id,@RequestBody Employee body) {
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getEmployeeId() == id) {
				data.get(i).setFirstName(body.getFirstName());
				data.get(i).setLastName(body.getLastName());
				data.get(i).setSalary(body.getSalary());
				return data.get(i);
			}
		}
		return null;
	}
	
	@DeleteMapping("/employee/{id}")
	public String deleteEmployee(@PathVariable Integer id) {
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getEmployeeId() == id) {
				data.remove(i);
				return "Deleted success!";
			}
		}
		return "Employee not found!";
	}

}
