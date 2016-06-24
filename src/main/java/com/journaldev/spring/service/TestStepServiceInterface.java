/**
 * 
 */
package com.journaldev.spring.service;

import java.util.List;

import com.journaldev.spring.model.TestStep;
import com.journaldev.spring.model.TestSuite;



/**
 * @author abarac
 *
 */
public interface TestStepServiceInterface {
	
	public void addTest(TestStep t);
	public List<TestStep> listTestSteps();
	public List<TestSuite> listNameSuite();
	public void updateTestStep(TestStep t);
	public TestStep getTestStepById(int id);
	public void removeTestStep(int id);
	public void addStepsToTest(Integer testId, TestStep testStep);
	public List<TestStep> getAll(Integer testId);


}
