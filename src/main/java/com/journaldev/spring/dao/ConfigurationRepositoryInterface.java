package com.journaldev.spring.dao;

import java.util.List;

import com.journaldev.spring.model.Configuration;



public interface ConfigurationRepositoryInterface {

	public void addConfiguration(Configuration configuration);
	public List<Configuration> listConfiguration();
	
	public void updateConfiguration(Configuration configuration);
	public Configuration getConfigurationById(int id);
	public void removeConfiguration(int id);

}
