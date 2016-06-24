package com.journaldev.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.journaldev.spring.dao.TestStepRepository;
import com.journaldev.spring.dao.TestStepRepositoryInterface;
import com.journaldev.spring.model.TestStep;
import com.journaldev.spring.model.TestSuite;

@Service
public class TestStepService implements TestStepServiceInterface {

	private TestStepRepositoryInterface repository;

	public void setRepository(TestStepRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public void addTest(TestStep teststep) {
		repository.addTest(teststep);
	}

	@Override
	@Transactional
	public List<TestStep> listTestSteps() {
		// TODO Auto-generated method stub
		return this.repository.listTestSteps();

	}

	@Override
	@Transactional
	public void updateTestStep(TestStep t) {
		
		this.repository.updateTestStep(t);
	}

	@Override
	@Transactional
	public TestStep getTestStepById(int id) {
		// TODO Auto-generated method stub
		return this.repository.getTestStepById(id);
	}

	@Override
	@Transactional
	public void removeTestStep(int id) {
		this.repository.removeTestStep(id);
	}

	@Override
	@Transactional
	public List<TestSuite> listNameSuite() {
		// TODO Auto-generated method stub
		return this.repository.listNameSuite();
	}

	@Override
	@Transactional
	public void addStepsToTest(Integer testId, TestStep testStep) {
		// TODO Auto-generated method stub
		this.repository.addStepsToTest(testId, testStep);
	}

	@Override
	@Transactional
	public List<TestStep> getAll(Integer testId) {
		// TODO Auto-generated method stub
		return this.repository.getAll(testId);
	}

	
	

}
