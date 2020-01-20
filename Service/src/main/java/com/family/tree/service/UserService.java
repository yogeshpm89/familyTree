package com.family.tree.service;

import java.util.List;
import java.util.Map;

public interface UserService {
	public Map<String, Object> addUser(Map<String, Object> request, String user) throws Exception;
	public Map<String, Object> updateUser(Long userId, Map<String, Object> request, String loggedinUser) throws Exception;
	public Map<String, Object> forgotPassword(Map<String, Object> request) throws Exception;
	public Map<String, Object> resetPassword(Map<String, Object> request) throws Exception;
	
	
	public Map<String, Object> getUserByEmail(String emailAddress) throws Exception;
	public Map<String, Object> addUserRole(Map<String, Object> request, Map<String, Object> activity) throws Exception;
	public Map<String, Object> deleteUserRole(Map<String, Object> request, Map<String, Object> activity) throws Exception;
	
	public Map<String, Object> sendUserRegistrationMail(Map<String, Object> request) throws Exception;
	// public Map<String, Object> addUserPassword(Map<String, Object> request, Map<String, Object> activity) throws Exception;
	
	public Map<String, Object> deleteUser(Map<String, Object> request) throws Exception;
	
	;
	public Map<String, Object> getUserById(long userId) throws Exception;
	public List<Object> getUserByIds(List<Long> userIdList) throws Exception;
	Map<String, Object> deactivateUser(long userId, String user) throws Exception;
	Map<String, Object> reactivateUser(long userId, String user) throws Exception;
	
}
