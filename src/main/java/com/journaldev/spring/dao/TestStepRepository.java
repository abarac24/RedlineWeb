package com.journaldev.spring.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.journaldev.spring.model.TestCase;
import com.journaldev.spring.model.TestStep;
import com.journaldev.spring.model.TestSuite;

@Repository
public class TestStepRepository implements TestStepRepositoryInterface {

	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory
			.getLogger(TestStepRepository.class);

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addTest(TestStep test) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(test);
		logger.info("Test saved successfully, Test Details=" + test);
	}

	@Override
	public List<TestStep> getAll(Integer testId) {
		logger.debug("Retrieving all test steps for testcase");

		// Retrieve session from Hibernate
		Session session = this.sessionFactory.getCurrentSession();

		// Create a Hibernate query (HQL)

		/*
		 * Query query = session.createQuery(
		 * "FROM TestCase as t LEFT JOIN FETCH  t.listSteps WHERE t.id="
		 * +testId);
		 * 
		 * TestCase t = (TestCase) query.uniqueResult();
		 * 
		 * // Retrieve all return new ArrayList<TestStep>(t.getListSteps());
		 */

		  Query query = session.createQuery("FROM TestCase as t WHERE t.id="+testId);
		   
		  TestCase t = (TestCase) query.uniqueResult();
		   
		  // Retrieve all
		  return  new ArrayList<TestStep>(t.getListSteps());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TestStep> listTestSteps() {
		Session session = this.sessionFactory.getCurrentSession();
		List<TestStep> testList = session.createQuery("from TestStep").list();
		for (TestStep t : testList) {
			logger.info("TestStep List::" + t);
		}
		return testList;
	}

	@Override
	public void updateTestStep(TestStep teststep) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(teststep);
		logger.info("TestStep updated successfully, TestStep Details="
				+ teststep);

	}

	@Override
	public TestStep getTestStepById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		TestStep t = (TestStep) session.load(TestStep.class, new Integer(id));
		logger.info("TestStep loaded successfully, TestStep details=" + t);
		return t;
	}

	@Override
	public void removeTestStep(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		
	     // Delete reference to foreign key credit card first
		  // We need a SQL query instead of HQL query here to access the third table
		     Query query = session.createSQLQuery("DELETE FROM TestCaseTestSteps " +
		       "WHERE TESTSTEP_ID="+id);
		      
		     query.executeUpdate();
		
		TestStep t = (TestStep) session.load(TestStep.class, new Integer(id));
		if (null != t) {
			session.delete(t);
		}
		logger.info("Step deleted successfully, teststep details=" + t);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TestSuite> listNameSuite() {
		Session session = this.sessionFactory.getCurrentSession();
		// Query query =
		// session.createQuery("select s.suiteName from TestSuite s");
		Query query = session.createQuery("from TestSuite");
		List<TestSuite> suiteList = query.list();
		for (TestSuite t : suiteList) {
			logger.info("TestSuite List::" + t.getSuiteName());
		}
		return suiteList;
	}

	@Override
	public void addStepsToTest(Integer testId, TestStep testStep) {
		logger.debug("Adding new teststep ");

		// Retrieve session from Hibernate
		Session session = this.sessionFactory.getCurrentSession();

		// Persists to db
		session.save(testStep);

		// Add to person as well
		// Retrieve existing test case via id
		TestCase existingTest = (TestCase) session.get(TestCase.class, testId);

		// Assign updated values to this testcase

		existingTest.getListSteps().add(testStep);

		// Save updates
		session.save(existingTest);
	}
	
	 public void delete(Integer id) {
		  logger.debug("Deleting existing test step");
		   
		  // Retrieve session from Hibernate
		  Session session = sessionFactory.getCurrentSession();
		   
		     // Delete reference to foreign key credit card first
		  // We need a SQL query instead of HQL query here to access the third table
		     Query query = session.createSQLQuery("DELETE FROM TestCaseTestSteps " +
		       "WHERE TESTSTEP_ID="+id);
		      
		     query.executeUpdate();
		      
		  // Retrieve existing credit card
		  TestStep testStep = (TestStep) session.get(TestStep.class, id);
		   
		  // Delete 
		  session.delete(testStep);
		 }

}
