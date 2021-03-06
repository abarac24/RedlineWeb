package com.journaldev.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.journaldev.spring.dao.TestSuiteRepository;
import com.journaldev.spring.dao.TestSuiteRepositoryInterface;
import com.journaldev.spring.model.TestSuite;

@Service
public class TestSuiteService implements TestSuiteServiceInterface {

	private TestSuiteRepositoryInterface repositorySuite;



	public void setRepositorySuite(TestSuiteRepository repositorySuite) {
		this.repositorySuite = repositorySuite;
	}

	@Override
	@Transactional
	public void addTestSuite(TestSuite testsuite) {
		repositorySuite.addTestSuite(testsuite);
	}

	@Override
	@Transactional
	public List<TestSuite> listTestSuites() {
		// TODO Auto-generated method stub
		return this.repositorySuite.listTestSuite();

	}

	@Override
	@Transactional
	public void updateTestSuite(TestSuite t) {
		
		this.repositorySuite.updateTestSuite(t);
	}

	@Override
	@Transactional
	public TestSuite getTestSuiteById(int id) {
		// TODO Auto-generated method stub
		return this.repositorySuite.getTestSuiteById(id);
	}

	@Override
	@Transactional
	public void removeTestSuite(int id) {
		this.repositorySuite.removeTestSuite(id);
	}





}
