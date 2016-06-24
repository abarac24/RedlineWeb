package com.journaldev.spring.dao;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.journaldev.spring.model.Configuration;


@Repository
public class ConfigurationRepository implements ConfigurationRepositoryInterface {


	private SessionFactory sessionConfiguration;
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigurationRepository.class);



	public void setSessionConfiguration(SessionFactory sessionConfiguration) {
		this.sessionConfiguration = sessionConfiguration;
	}


	@Override
	public void addConfiguration(Configuration Configuration) {
		Session session = this.sessionConfiguration.getCurrentSession();
		session.persist(Configuration);
		logger.info("Test saved successfully, Test Details="+Configuration);
	}


	public SessionFactory getSessionRepository() {
		return sessionConfiguration;
	}


	public void setSessionRepository(SessionFactory sessionRepository) {
		this.sessionConfiguration = sessionRepository;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Configuration> listConfiguration() {
		Session session = this.sessionConfiguration.getCurrentSession();
		List<Configuration> testList = session.createQuery("from Configuration").list();
		for(Configuration t : testList){
			logger.info("Configuration List::"+t);
		}
		return testList;
	}
	

	@Override
	public void updateConfiguration(Configuration Configuration) {
		Session session = this.sessionConfiguration.getCurrentSession();
		session.update(Configuration);
		logger.info("TestSuite updated successfully, TestSuite Details="+Configuration);
		
	}


	@Override
	public Configuration getConfigurationById(int id) {
		Session session = this.sessionConfiguration.getCurrentSession();		
		Configuration t = (Configuration) session.load(Configuration.class, new Integer(id));
		logger.info("Configuration loaded successfully, TestSuite details="+t);
		return t;
	}


	@Override
	public void removeConfiguration(int id) {
		Session session = this.sessionConfiguration.getCurrentSession();
		Configuration t = (Configuration) session.load(Configuration.class, new Integer(id));
		if(null != t){
			session.delete(t);
		}
		logger.info("Step deleted successfully, Configuration details="+t);
		
	}



}
