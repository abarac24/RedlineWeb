package com.journaldev.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TESTCASE")
@NamedQueries({
	@NamedQuery(name = "TestCase.findById", query = "SELECT t FROM TestCase t WHERE t.id = :id"),
	@NamedQuery(name = "TestCase.findByProject", query = "SELECT t FROM TestCase t WHERE t.project = :project")
	})

public class TestCase {
	
	@Id
	@Column(name="test_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;
	
	private String description;
	
	private String project;

	
	/*
	@OneToMany(mappedBy="testCase",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private List<TestStep> listSteps; 
	*/
	
	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(
	            name="TestCaseTestSteps",
	            joinColumns = @JoinColumn( name="TESTCASE_ID"),
	            inverseJoinColumns = @JoinColumn( name="TESTSTEP_ID"))
	private List<TestStep> listSteps=new ArrayList<TestStep>(); 
	
	public List<TestStep> getListSteps() {
		return listSteps;
	}

	public void setListSteps(List<TestStep> listSteps) {
		this.listSteps = listSteps;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "TestCase [id=" + id + ", name=" + name + ", description="
				+ description + ", listSteps=" + listSteps + "]";
	}


	

}
