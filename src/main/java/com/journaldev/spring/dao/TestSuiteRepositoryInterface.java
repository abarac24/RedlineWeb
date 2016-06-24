package com.journaldev.spring.dao;

import java.util.List;

import com.journaldev.spring.model.TestSuite;



public interface TestSuiteRepositoryInterface {

	public void addTestSuite(TestSuite testsuite);
	public List<TestSuite> listTestSuite();
	
	public void updateTestSuite(TestSuite testsuite);
	public TestSuite getTestSuiteById(int id);
	public void removeTestSuite(int id);


}
