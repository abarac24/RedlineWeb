package com.journaldev.spring.dao;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.journaldev.spring.model.TestSuite;


@Repository
public class TestSuiteRepository implements TestSuiteRepositoryInterface {


	private SessionFactory sessionRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(TestSuiteRepository.class);

	
	@Override
	public void addTestSuite(TestSuite testsuite) {
		Session session = this.sessionRepository.getCurrentSession();
		session.persist(testsuite);
		logger.info("Test saved successfully, Test Details="+testsuite);
	}


	public SessionFactory getSessionRepository() {
		return sessionRepository;
	}


	public void setSessionRepository(SessionFactory sessionRepository) {
		this.sessionRepository = sessionRepository;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TestSuite> listTestSuite() {
		Session session = this.sessionRepository.getCurrentSession();
		List<TestSuite> testList = session.createQuery("from TestSuite").list();
		for(TestSuite t : testList){
			logger.info("TestSuite List::"+t);
		}
		return testList;
	}
	


	


	@Override
	public void updateTestSuite(TestSuite testsuite) {
		Session session = this.sessionRepository.getCurrentSession();
		session.update(testsuite);
		logger.info("TestSuite updated successfully, TestSuite Details="+testsuite);
		
	}


	@Override
	public TestSuite getTestSuiteById(int id) {
		Session session = this.sessionRepository.getCurrentSession();		
		TestSuite t = (TestSuite) session.load(TestSuite.class, new Integer(id));
		logger.info("TestSuite loaded successfully, TestSuite details="+t);
		return t;
	}


	@Override
	public void removeTestSuite(int id) {
		Session session = this.sessionRepository.getCurrentSession();
		TestSuite t = (TestSuite) session.load(TestSuite.class, new Integer(id));
		if(null != t){
			session.delete(t);
		}
		logger.info("Step deleted successfully, TestSuite details="+t);
		
	}




}
