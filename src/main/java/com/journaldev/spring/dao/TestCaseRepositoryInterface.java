package com.journaldev.spring.dao;

import java.util.List;

import com.journaldev.spring.model.TestCase;



public interface TestCaseRepositoryInterface {

	public void addTestCase(TestCase testcase);
	public List<TestCase> listTestCase();
	
	public void updateTestCase(TestCase testcase);
	public TestCase getTestCaseById(int id);
	public void removeTestCase(int id);
	//public void addTestCaseToSteps(int testId,int stepId);
	public List<TestCase> getTestCaseByProject(String project);
}
