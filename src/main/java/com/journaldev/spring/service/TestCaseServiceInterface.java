package com.journaldev.spring.service;

import java.util.List;

import com.journaldev.spring.model.TestCase;

public interface TestCaseServiceInterface {

	public void addTestCase(TestCase t);
	public List<TestCase> listTestCases();
	public void updateTestCase(TestCase t);
	public TestCase getTestCaseById(int id);
	public void removeTestCase(int id);
	//public void addTestCaseToSteps(int testId,int stepId);
	
	public List<TestCase> getTestCaseByProject(String project);

	

}
