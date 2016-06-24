package com.journaldev.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CONFIGURATION")
public class Configuration {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	
	private String explorer;
	
	private String deviceIp;
	
	private String userName;
	
	private String password;
	
	Integer testSelected[];

	private String project;

	
	//private List<TestCase> testcase;

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public Integer[] getTestSelected() {
		return testSelected;
	}

	public void setTestSelected(Integer[] testSelected) {
		this.testSelected = testSelected;
	}

	public String getDeviceIp() {
		return deviceIp;
	}

	public String getExplorer() {
		return explorer;
	}

	public int getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public String getUserName() {
		return userName;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	public void setExplorer(String explorer) {
		this.explorer = explorer;
	}
/*
	public List<TestCase> getTestcase() {
		return testcase;
	}

	public void setTestcase(List<TestCase> testcase) {
		this.testcase = testcase;
	}
*/

	public void setId(int id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
