/**
 * 
 */
package com.journaldev.spring.service;

import java.util.List;

import com.journaldev.spring.model.TestSuite;



/**
 * @author abarac
 *
 */
public interface TestSuiteServiceInterface {
	
	public void addTestSuite(TestSuite t);
	public List<TestSuite> listTestSuites();
	public void updateTestSuite(TestSuite t);
	public TestSuite getTestSuiteById(int id);
	public void removeTestSuite(int id);



}
