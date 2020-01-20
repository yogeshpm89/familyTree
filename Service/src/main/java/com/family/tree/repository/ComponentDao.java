package com.family.tree.repository;

import java.util.Map;

import com.family.tree.model.Component;


public interface ComponentDao 
{
	public Component getComponent(Map<String, Object> componentSqlDeatils) throws Exception;

}
