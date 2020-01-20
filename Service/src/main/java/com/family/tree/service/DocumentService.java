package com.family.tree.service;

import java.util.Map;

public interface DocumentService {

	public Map<String, Object> save(Map<String, Object> fields) throws Exception;
	public Map<String, Object> delete(String fileName) throws Exception;
	public Map<String, Object> saveDetails(Map<String, Object> fields, String loggedinuser) throws Exception;
}
