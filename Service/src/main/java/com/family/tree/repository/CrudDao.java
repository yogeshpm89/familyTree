package com.family.tree.repository;

import java.util.List;
import java.util.Map;

import com.family.tree.model.CrudConfig;

public interface CrudDao 
{
	public Map<String, Object> addDomain(CrudConfig crudConfig, Map<String, Object>  crudAttributes)  throws Exception;
	public Map<String, Object> bulkAddDomain(CrudConfig crudConfig, Map<String, Object>[]  crudAttributes)  throws Exception;
	public Map<String, Object> updateDomain(CrudConfig crudConfig, Map<String, Object> crudAttributes, Map<String, Object> filters)  throws Exception;
	public Map<String, Object> deleteDomain(CrudConfig crudConfig, Map<String, Object> filters)  throws Exception;
	public List<Integer> getVersionOfCrud(CrudConfig crudConfig, Map<String, Object> filters);
	public void generateHistory(CrudConfig crudConfigForHist, CrudConfig crudConfig, Map<String, Object> historyCrudAttributes, Map<String, Object> filters) throws Exception;
	
	public void deleteAnswer(Map<String, Object> request);
	
	public Map<String, Object> getLastMopNumber();
}
