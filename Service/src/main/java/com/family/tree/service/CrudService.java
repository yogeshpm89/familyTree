package com.family.tree.service;

import java.util.List;
import java.util.Map;

import com.family.tree.constant.CrudComponent;

public interface CrudService 
{
	public Map<String, Object> addDomain(CrudComponent crudComponent, Map<String, Object>  crudAttributes, Map<String, Object> activity)  throws Exception;
	public Map<String, Object> bulkAddDomain(CrudComponent crudComponent, List<Map<String, Object>> crudAttributeList, Map<String, Object> activity)  throws Exception;
	public Map<String, Object> updateDomain(CrudComponent crudComponent, Map<String, Object> crudAttributes, Map<String, Object> filters, Map<String, Object> activity)  throws Exception;
	public Map<String, Object> deleteDomain(CrudComponent crudComponent, Map<String, Object> filters, String version, Map<String, Object> activity)  throws Exception;
	public Map<String, Object> generateActivity(String activityType, String user) throws Exception;
}
