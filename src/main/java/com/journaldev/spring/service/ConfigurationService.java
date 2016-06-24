package com.journaldev.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.journaldev.spring.dao.ConfigurationRepositoryInterface;
import com.journaldev.spring.model.Configuration;

@Service
public class ConfigurationService implements ConfigurationServiceInterface {

	private ConfigurationRepositoryInterface repositoryConfiguration;
	

	public void setRepositoryConfiguration(
			ConfigurationRepositoryInterface repositoryConfiguration) {
		this.repositoryConfiguration = repositoryConfiguration;
	}

	@Override
	@Transactional
	public void addConfiguration(Configuration t) {
		// TODO Auto-generated method stub
		repositoryConfiguration.addConfiguration(t);
		
	}

	@Override
	@Transactional
	public List<Configuration> listConfigurations() {
		// TODO Auto-generated method stub
		return this.repositoryConfiguration.listConfiguration();
	}

	@Override
	@Transactional
	public void updateConfiguration(Configuration t) {
		// TODO Auto-generated method stub
		this.repositoryConfiguration.updateConfiguration(t);
		
	}

	@Override
	@Transactional
	public Configuration getConfigurationById(int id) {
		// TODO Auto-generated method stub
		return this.repositoryConfiguration.getConfigurationById(id);
	}

	@Override
	@Transactional
	public void removeConfiguration(int id) {
		// TODO Auto-generated method stub
		this.repositoryConfiguration.removeConfiguration(id);
	}

	
	

}
