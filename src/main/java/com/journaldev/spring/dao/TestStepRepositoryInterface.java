package com.journaldev.spring.dao;

import java.util.List;

import com.journaldev.spring.model.TestStep;
import com.journaldev.spring.model.TestSuite;



public interface TestStepRepositoryInterface {

	public void addTest(TestStep teststep);
	public List<TestStep> listTestSteps();
	public void updateTestStep(TestStep teststep);
	public TestStep getTestStepById(int id);
	public void removeTestStep(int id);
	public List<TestSuite> listNameSuite();
	public void addStepsToTest(Integer testId, TestStep testStep);
	public List<TestStep> getAll(Integer testId);




}
