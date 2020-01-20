package com.family.tree.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.family.tree.model.ComponentConfig;
import com.family.tree.repository.ComponentConfigDao;

@Service
public class ComponentConfigServiceImpl implements ComponentConfigService
{
	@Autowired
	private ComponentConfigDao componentConfigDao;

	private static final Logger logger = LogManager.getLogger("ComponentConfigServiceImpl");

	@Override
	@Cacheable(value="config", key="#inputRequest")
	public ComponentConfig getComponentConfig(String inputRequest) 
	{
		logger.info("getComponentConfig start, inputRequest = " + inputRequest);
		ComponentConfig componentConfig = componentConfigDao.getComponent(inputRequest);
		logger.info("getComponentConfig end, componentConfig = " + componentConfig);
		return componentConfig;
	}


}
