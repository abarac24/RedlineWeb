package com.journaldev.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="TESTSUITE")
@NamedQuery(name = "TestSuite.findById", query = "SELECT t FROM TestSuite t WHERE t.id = :id")
public class TestSuite {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;


	private String suiteName;

	private String testName;

	private String testPageTitle;

	private String testPageXpath;
	/*
	@ManyToMany
	@JoinTable(name="suite_test_join",joinColumns= @JoinColumn(name="suite_fk"),inverseJoinColumns=@JoinColumn(name="test_fk"))
	private List<TestStep> teststep;
	*/
	public int getId() {
		return id;
	}

	public String getSuiteName() {
		return suiteName;
	}

	public String getTestName() {
		return testName;
	}

	public String getTestPageTitle() {
		return testPageTitle;
	}

	public String getTestPageXpath() {
		return testPageXpath;
	}

	/*public void setExercises(List<TestStep> exercises) {
		this.exercises = exercises;
	}*/

	public void setId(int id) {
		this.id = id;
	}

	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
	}


	public void setTestName(String testName) {
		this.testName = testName;
	}

	public void setTestPageTitle(String testPageTitle) {
		this.testPageTitle = testPageTitle;
	}

	public void setTestPageXpath(String testPageXpath) {
		this.testPageXpath = testPageXpath;
	}
	
	

}
