package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "student")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String stdId;
	private String stdName;
	private String stdSurName;
	private String stdEmail;
	
	public Student() {
		super();
	}
	public Student(String stdId, String stdName, String stdSurName, String stdEmail) {
		super();
		this.stdId = stdId;
		this.stdName = stdName;
		this.stdSurName = stdSurName;
		this.stdEmail = stdEmail;
	}
	public String getStdId() {
		return stdId;
	}
	public void setStdId(String stdId) {
		this.stdId = stdId;
	}
	public String getStdName() {
		return stdName;
	}
	public void setStdName(String stdName) {
		this.stdName = stdName;
	}
	public String getStdSurName() {
		return stdSurName;
	}
	public void setStdSurName(String stdSurName) {
		this.stdSurName = stdSurName;
	}
	public String getStdEmail() {
		return stdEmail;
	}
	public void setStdEmail(String stdEmail) {
		this.stdEmail = stdEmail;
	}
	public Integer getId() {
		return id;
	}
	
}
