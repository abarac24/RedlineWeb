package com.journaldev.spring.service;

import java.util.List;

import com.journaldev.spring.model.Configuration;

public interface ConfigurationServiceInterface {

	public void addConfiguration(Configuration t);
	public List<Configuration> listConfigurations();
	public void updateConfiguration(Configuration t);
	public Configuration getConfigurationById(int id);
	public void removeConfiguration(int id);

}
