package com.family.tree.service;

import java.util.Map;

public interface AppService {

	public Map<String, String> getMailSettings() throws Exception;

	// public Map<String, Object> saveMailSettings(Map<String, Object> request) throws Exception;

	Map<String, String> getApplicationURL() throws Exception;

	Map<String, Object> sendTestMail(String to) throws Exception;
}
