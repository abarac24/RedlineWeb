package com.journaldev.spring.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.journaldev.spring.model.TestCase;

@Repository
public class TestCaseRepository implements TestCaseRepositoryInterface {
    //TestCase
	private SessionFactory sessionRepositoryCase;

	private static final Logger logger = LoggerFactory
			.getLogger(TestCaseRepository.class);

	public void setSessionRepositoryCase(SessionFactory sessionRepositoryCase) {
		this.sessionRepositoryCase = sessionRepositoryCase;
	}

	@Override
	public void addTestCase(TestCase testcase) {
		Session session = this.sessionRepositoryCase.getCurrentSession();
		session.persist(testcase);
		logger.info("Test saved successfully, Test Details=" + testcase);
	}

	public SessionFactory getSessionRepository() {
		return sessionRepositoryCase;
	}

	public void setSessionRepository(SessionFactory sessionRepository) {
		this.sessionRepositoryCase = sessionRepository;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TestCase> listTestCase() {
		Session session = this.sessionRepositoryCase.getCurrentSession();
		List<TestCase> testList = session.createQuery("from TestCase").list();
		for (TestCase t : testList) {
			logger.info("TestCase List::" + t);
		}
		return testList;
	}

	@Override
	public void updateTestCase(TestCase testcase) {
		Session session = this.sessionRepositoryCase.getCurrentSession();
		TestCase existingtest = (TestCase) session.get(TestCase.class, testcase.getId());
		existingtest.setDescription(testcase.getDescription());
		existingtest.setName(testcase.getName());
		existingtest.setProject(testcase.getProject());
		//existingtest.setListSteps(testcase.getListSteps());
		//session.update(testcase);
		session.update(existingtest);
		logger.info("TestSuite updated successfully, TestSuite Details="
				+ testcase);

	}

	@Override
	public TestCase getTestCaseById(int id) {
		Session session = this.sessionRepositoryCase.getCurrentSession();
		TestCase t = (TestCase) session.load(TestCase.class, new Integer(id));
		t.setListSteps(t.getListSteps());
		t.setName(t.getName());
		t.setProject(t.getProject());
		t.setDescription(t.getDescription());
		logger.info("TestCase loaded successfully, TestSuite details=" + t);
		return t;

	}
	@Override
	public List<TestCase> getTestCaseByProject(String project) {
	    try {
			Session session = this.sessionRepositoryCase.getCurrentSession();
			//List<TestCase> testList = session.createQuery("from TestCase").list();
			Query query = session.getNamedQuery("TestCase.findByProject");
			query.setString("project", project);
	        List<TestCase> testList = query.list();
	        return testList;
	      } catch (NoResultException e) {
	        return null;
	      }
	}

	@Override
	public void removeTestCase(int id) {
		Session session = this.sessionRepositoryCase.getCurrentSession();
		TestCase t = (TestCase) session.load(TestCase.class, new Integer(id));
		if (null != t) {
			session.delete(t);
		}
		logger.info("Step deleted successfully, TestCase details=" + t);

	}

	/*
	@Override
	public void addTestCaseToSteps(int testId, int stepId) {

		Session session = this.sessionRepositoryCase.getCurrentSession();
		
		TestCase t = (TestCase) session.load(TestCase.class, testId);
		
		TestStep s = (TestStep) session.load(TestStep.class, stepId);
		
		List<TestStep> sList= t.getListSteps();
		
		sList.add(s);
		
		t.setListSteps(sList);
		
		s.setTestCase(t);

	} */
	



}
