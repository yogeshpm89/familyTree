package com.family.tree.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.family.tree.model.CrudConfig;
import com.family.tree.repository.CrudConfigDao;


@Service
public class CrudConfigServiceImpl implements CrudConfigService
{
	@Autowired
	private CrudConfigDao crudConfigDao;

	@Override
	@Cacheable(value="config", key="#inputRequest")
	public CrudConfig getCrudConfig(String inputRequest) 
	{
		CrudConfig crudConfig = crudConfigDao.getCrudConfig(inputRequest);
		return crudConfig;
	}


}
