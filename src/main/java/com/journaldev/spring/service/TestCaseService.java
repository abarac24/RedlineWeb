package com.journaldev.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.journaldev.spring.dao.TestCaseRepositoryInterface;
import com.journaldev.spring.model.TestCase;

@Service
public class TestCaseService implements TestCaseServiceInterface {

	private TestCaseRepositoryInterface repositoryCase;
	
	

	public void setRepositoryCase(TestCaseRepositoryInterface repositoryCase) {
		this.repositoryCase = repositoryCase;
	}

	@Override
	@Transactional
	public void addTestCase(TestCase t) {
		// TODO Auto-generated method stub
		repositoryCase.addTestCase(t);
		
	}

	@Override
	@Transactional
	public List<TestCase> listTestCases() {
		// TODO Auto-generated method stub
		return this.repositoryCase.listTestCase();
	}

	@Override
	@Transactional
	public void updateTestCase(TestCase t) {
		// TODO Auto-generated method stub
		this.repositoryCase.updateTestCase(t);
		
	}

	@Override
	@Transactional
	public TestCase getTestCaseById(int id) {
		// TODO Auto-generated method stub
		return this.repositoryCase.getTestCaseById(id);
	}

	@Override
	@Transactional
	public void removeTestCase(int id) {
		// TODO Auto-generated method stub
		this.repositoryCase.removeTestCase(id);
	}

	@Override
	@Transactional
	public List<TestCase> getTestCaseByProject(String project) {
		// TODO Auto-generated method stub
		return this.repositoryCase.getTestCaseByProject(project);
	}


	

}
