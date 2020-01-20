package com.family.tree.service;


import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.family.tree.constant.Activity;
import com.family.tree.constant.Application;
import com.family.tree.constant.ComponentConfigFilter;
import com.family.tree.constant.CrudComponent;
import com.family.tree.constant.CrudConfigConstant;
import com.family.tree.constant.EmailBody;
import com.family.tree.constant.RFIDCollectMessages;
import com.family.tree.constant.ValidationMessage;
import com.family.tree.model.Component;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private CrudService crudService;
	
	@Autowired
	private ComponentService componentService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
 
	
	@Autowired
	private EmailService emailService;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
	public Map<String, Object> addUser(Map<String, Object> request, String loggedinUser)
			throws Exception {
		Map<String, Object> userMap = getUserMapFromRequest(request);
		Map<String, Object> userDetails = getUserByEmail((String) userMap.get("email"));
		if (userDetails != null) {
			throw new Exception(ValidationMessage.USER_ALREADY_PRESENT.getName());
		}
		
		Map<String, Object> activity = crudService.generateActivity(Activity.ADD_USER.getName(), loggedinUser);
		Map<String, Object> addUserResponse = crudService.addDomain(CrudComponent.USER, userMap, activity);
		
		if(addUserResponse.get(CrudConfigConstant.STATUS.getName()) == null || 
				Boolean.FALSE.equals(addUserResponse.get(CrudConfigConstant.STATUS.getName()))) {
			throw new Exception(addUserResponse.toString());
		}
		
		String[] to = new String[1];
		to[0] = (String) userMap.get("email");
		String mailBody = EmailBody.USER_FORGOT_PASSWORD_MAIL_BODY.getBody();
		String username = "";
		if (userMap.containsKey("displayName") && !"".equalsIgnoreCase(userMap.get("displayName").toString())) {
			username = username + "Hi " + userMap.get("displayName").toString() + ",";
		}
		mailBody = mailBody.replaceAll("###USER_NAME###", username);
		mailBody = mailBody.replaceAll("###email###", Base64.getEncoder().encodeToString(to[0].getBytes()));
		emailService.sendMail(to, EmailBody.USER_REGISTRATION_MAIL_SUBJECT.getBody(), mailBody);
		
		return addUserResponse;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
	public Map<String, Object> updateUser(Long userId, Map<String, Object> request, String loggedinUser)
			throws Exception {
		Map<String, Object> requestFilters = new HashMap<>();
		requestFilters.put("userId", userId);
		
		Map<String, Object> userMap = getUserMapFromRequest(request);
		userMap.remove("email");
		Map<String, Object> activity = crudService.generateActivity(Activity.UPDATE_USER.getName(), loggedinUser);
		Map<String, Object> response = crudService.updateDomain(CrudComponent.USER, userMap, requestFilters, activity);
		
		if(response.get(CrudConfigConstant.STATUS.getName()) == null || Boolean.FALSE.equals(response.get(CrudConfigConstant.STATUS.getName()))) {
			throw new Exception(response.toString());
		}
		// Map<String, Object> userRoleMap = getUserRoleMapFromRequest(request);
		// requestFilters.clear();
		// addToDomainMap(request, requestFilters, "userId");
		// addToDomainMap(request, userRoleMap, "userId");
		// Map<String, Object> responseForChild = crudService.updateDomain(CrudComponent.USERROLE, userRoleMap, requestFilters, activity);
		return response;
	}
	
	Map<String, Object> getUserMapFromRequest(Map<String, Object> request) {
		Map<String, Object> userMap = new HashMap<String, Object>(10);
		addToDomainMap(request, userMap, "firstName");
		addToDomainMap(request, userMap, "middleName");
		addToDomainMap(request, userMap, "lastName");
		addToDomainMap(request, userMap, "displayName");
		addToDomainMap(request, userMap, "email");
		addToDomainMap(request, userMap, "address");
		addToDomainMap(request, userMap, "phone");
		return userMap;
	}
	
	Map<String, Object> getUserRoleMapFromRequest(Map<String, Object> request) {
		Map<String, Object> userRoleMap = new HashMap<String, Object>(10);
		addToDomainMap(request, userRoleMap, "userRoleId", "roleId");
		return userRoleMap;
	}
	
	void addToDomainMap(Map<String, Object> request, Map<String, Object> domainMap, String key) {
		if (request.containsKey(key)) {
			domainMap.put(key, request.get(key));
		}
	}
	
	void addToDomainMap(Map<String, Object> request, Map<String, Object> domainMap, String requestKey, String domainKey) {
		if (request.containsKey(requestKey)) {
			domainMap.put(domainKey, request.get(requestKey));
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
	public Map<String, Object> sendUserRegistrationMail(Map<String, Object> request) throws Exception {
		String email = (String) request.get("email");
		Map<String, Object> response = new HashMap<String, Object>();
		if (email != null) {
			String[] to = new String[1];
			to[0] = email;
			String mailBody = EmailBody.USER_REGISTRATION_MAIL_BODY.getBody(); // RFIDCollectMessages.getMessages("USER_REGISTRATION_MAIL_BODY");
			String username = "";
			if (request.containsKey("displayName") && !"".equalsIgnoreCase(request.get("displayName").toString())) {
				username = username + "Hi " + request.get("displayName").toString() + ",";
			}
			mailBody = mailBody.replaceAll("###USER_NAME###", username);
			mailBody = mailBody.replaceAll("###email###", Base64.getEncoder().encodeToString(to[0].getBytes()));
			emailService.sendMail(to, EmailBody.USER_REGISTRATION_MAIL_SUBJECT.getBody(), mailBody);
			response.put(CrudConfigConstant.STATUS.getName(), true);
		} else {
			response.put(CrudConfigConstant.STATUS.getName(), false);
			response.put("reason", "Email address is empty");
		}
		return response;
	}
	
	
	/*@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
	public Map<String, Object> addUserPassword(Map<String, Object> request, Map<String, Object> activity) throws Exception {

		request.get("username");
		String password = UUID.randomUUID().toString().substring(0, 32);
		
		Map<String, Object> updatedFields = new HashMap<String, Object>();
		updatedFields.put("userId", request.get("user_id"));
		updatedFields.put("password", password);
		updatedFields.put("isActive", 1);
		updatedFields.put("isFirst", 1);
		
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("userId", request.get("user_id"));
		
		Map<String, Object> response = crudService.addDomain(CrudComponent.USERPASSWORD, updatedFields, activity);
		
		if(response.get("status") == null || Boolean.FALSE.equals(response.get("status"))) {
			throw new Exception(response.toString());
		}
		
		return response;
	
	}*/
	
	

	@Override
	public Map<String, Object> deleteUser(Map<String, Object> request)
			throws Exception {
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
	public Map<String, Object> deactivateUser(long userId, String user) throws Exception{
		Map<String, Object> activity = crudService.generateActivity("DELETE USER", user);
		
		Map<String, Object> updatedFields = new HashMap<String, Object>(2);
		updatedFields.put("isActive", 0);
		
		Map<String, Object> filter = new HashMap<String, Object>(2);
		filter.put("userId", userId);
		
		Map<String, Object> response = crudService.updateDomain(CrudComponent.USER, updatedFields, filter, activity);
		if (response.get(CrudConfigConstant.STATUS.getName()) == null || Boolean.FALSE.equals(response.get(CrudConfigConstant.STATUS.getName()))) {
			throw new Exception(response.toString());
		}
		return response;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
	public Map<String, Object> resetPassword(Map<String, Object> request) throws Exception {
		String username = (String) request.get("username");
		String password = (String) request.get("password");
		
		Map<String, Object> user = getUser(username);
		
		if (user == null) {
			throw new Exception(RFIDCollectMessages.getMessages("USER_NOT_EXISTS"));
		}
		
		Map<String, Object> updatedFields = new HashMap<String, Object>();
		updatedFields.put("userId", user.get("userId"));
		// updatedFields.put("password", password); // passwordEncoder.encode(password));
		updatedFields.put("password", passwordEncoder.encode(password));
		// updatedFields.put("isActive", 1);
		updatedFields.put("isFirst", 0);
		
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("userId", user.get("userId"));
		
		Map<String, Object> activity = crudService.generateActivity(Activity.UPDATE_USER_PASSWORD.getName(), Application.SYSTEM_USER.getValue());
		Map<String, Object> response = crudService.updateDomain(CrudComponent.USER, updatedFields, filters, activity);
		
		if(response.get(CrudConfigConstant.STATUS.getName()) == null || Boolean.FALSE.equals(response.get(CrudConfigConstant.STATUS.getName()))) {
			throw new Exception(response.toString());
		}
		
		return response;
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
	public Map<String, Object> forgotPassword(Map<String, Object> request) throws Exception {
		String username = (String) request.get("username");
		Map<String, Object> response = new HashMap<String, Object>();
		
		Map<String, Object> user = getUser(username);
		
		if (user == null) {
			throw new Exception(RFIDCollectMessages.getMessages("USER_NOT_EXISIS"));
		}
		
		String mailBody = EmailBody.USER_FORGOT_PASSWORD_MAIL_BODY.getBody().replaceAll("###username###", Base64.getEncoder().encodeToString(username.getBytes()));
		boolean flag = emailService.sendMail(new String[]{username}, RFIDCollectMessages.getMessages("USER_FORGOT_PASSWORD_MAIL_SUBJECT"), mailBody);
	
		if (flag) {
			response.put(CrudConfigConstant.STATUS.getName(), true);
			response.put("reason", EmailBody.MAIL_SENT.getBody());
		} else {
			response.put(CrudConfigConstant.STATUS.getName(), false);
			response.put("reason", EmailBody.MAIL_SENT_ERROR.getBody());
		}
		
		return response;
	}
	
	private Map<String, Object> getUser(String username) throws Exception {
		Map<String, Object> map = new HashMap<>();
        map.put("inputRequest", "/component/users");
        
        Map<String, Object> userFilter = new HashMap<>();
        userFilter.put(ComponentConfigFilter.KEY.getName(), "email");
        userFilter.put(ComponentConfigFilter.VALUE.getName(), username);
        userFilter.put(ComponentConfigFilter.TYPE.getName(), ComponentConfigFilter.CHARS.getName());
        
        List<Map<String, Object>> filters = new ArrayList<>();
        filters.add(userFilter);
        map.put(ComponentConfigFilter.FILTER.getName(), filters);
        
        Component<Object> component =  componentService.getComponent(map);
        
        List<Object> data = component.getData();
        
        if (data.isEmpty()) return null;
        
        Map<String, Object> userDetails = (Map<String, Object>) data.get(0);
        
        return userDetails;
        
	}
	
	
	private Map<String, Object> getUserRole(Long userId, Long userRoleId, Long mopId) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("inputRequest", "/component/MopUserRoles");
        
        Map<String, Object> userIdFilter = new HashMap<>();
        userIdFilter.put(ComponentConfigFilter.KEY.getName(), "userId");
        userIdFilter.put(ComponentConfigFilter.VALUE.getName(), userId);
        userIdFilter.put(ComponentConfigFilter.TYPE.getName(), ComponentConfigFilter.NUMBER.getName());
        
        Map<String, Object> userRoleIdFilter = new HashMap<>();
        userRoleIdFilter.put(ComponentConfigFilter.KEY.getName(), "roleId");
        userRoleIdFilter.put(ComponentConfigFilter.VALUE.getName(), userRoleId);
        userRoleIdFilter.put(ComponentConfigFilter.TYPE.getName(), ComponentConfigFilter.NUMBER.getName());
        
        Map<String, Object> mopIdFilter = new HashMap<>();
        mopIdFilter.put(ComponentConfigFilter.KEY.getName(), "mopId");
        mopIdFilter.put(ComponentConfigFilter.VALUE.getName(), mopId);
        mopIdFilter.put(ComponentConfigFilter.TYPE.getName(), ComponentConfigFilter.NUMBER.getName());
        
        List<Map<String, Object>> filters = new ArrayList<>();
        filters.add(userIdFilter);
        filters.add(userRoleIdFilter);
        filters.add(mopIdFilter);
        map.put(ComponentConfigFilter.FILTER.getName(), filters);
        
        Component<Object> component =  componentService.getComponent(map);
        
        List<Object> data = component.getData();
        
        if (data.isEmpty()) return null;
        
        Map<String, Object> userRoleDetails = (Map<String, Object>) data.get(0);
        
        return userRoleDetails;
        
	}


	@Override
	public Map<String, Object> getUserByEmail(String emailAddress) throws Exception {
		Map<String, Object> map = new HashMap<>();
        map.put("inputRequest", "/component/users");
        
        Map<String, Object> userFilter = new HashMap<>();
        userFilter.put(ComponentConfigFilter.KEY.getName(), "email");
        userFilter.put(ComponentConfigFilter.VALUE.getName(), emailAddress);
        userFilter.put(ComponentConfigFilter.TYPE.getName(), ComponentConfigFilter.CHARS.getName());
        
        List<Map<String, Object>> filters = new ArrayList<>();
        filters.add(userFilter);
        map.put(ComponentConfigFilter.FILTER.getName(), filters);
        
        Component<Object> component =  componentService.getComponent(map);
        
        List<Object> data = component.getData();
        
        if (data.isEmpty()) return null;
        
        Map<String, Object> userDetails = (Map<String, Object>) data.get(0);
        
        return userDetails;
        
	}
	
	
	@Override
	public Map<String, Object> getUserById(long userId) throws Exception {
		Map<String, Object> map = new HashMap<>();
        map.put("inputRequest", "/component/users");
        
        Map<String, Object> userFilter = new HashMap<>();
        userFilter.put(ComponentConfigFilter.KEY.getName(), "userId");
        userFilter.put(ComponentConfigFilter.VALUE.getName(), userId);
        userFilter.put(ComponentConfigFilter.TYPE.getName(), ComponentConfigFilter.NUMBER.getName());
        
        List<Map<String, Object>> filters = new ArrayList<>();
        filters.add(userFilter);
        map.put(ComponentConfigFilter.FILTER.getName(), filters);
        
        Component<Object> component =  componentService.getComponent(map);
        
        List<Object> data = component.getData();
        
        if (data.isEmpty()) return null;
        
        Map<String, Object> userDetails = (Map<String, Object>) data.get(0);
        
        return userDetails;
        
	}
	
	
	@Override
	public List<Object> getUserByIds(List<Long> userIdList) throws Exception {
		Map<String, Object> map = new HashMap<>();
        map.put("inputRequest", "/component/userId");
        
        Map<String, Object> userFilter = new HashMap<>();
        userFilter.put(ComponentConfigFilter.KEY.getName(), "userId");
        userFilter.put(ComponentConfigFilter.VALUE.getName(), userIdList);
        userFilter.put(ComponentConfigFilter.TYPE.getName(), ComponentConfigFilter.LIST.getName());
        
        List<Map<String, Object>> filters = new ArrayList<>();
        filters.add(userFilter);
        map.put(ComponentConfigFilter.FILTER.getName(), filters);
        
        Component<Object> component =  componentService.getComponent(map);
        
        List<Object> data = component.getData();
        
        if (data.isEmpty()) return null;
        
        return data;
        
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
	public Map<String, Object> addUserRole(Map<String, Object> request, Map<String, Object> activity) throws Exception {
		Long userId = (Long) request.get("userId");
		Long userRoleId = (Long) request.get("roleId");
		Long mopId = (Long) request.get("mopId");
		
		Map<String, Object> userRoleDetails = getUserRole(userId, userRoleId, mopId);
		
		Map<String, Object> response = null;
		if (userRoleDetails == null) {
			
			if (activity == null) {
				activity = crudService.generateActivity("ADD USER ROLE", "SYSTEM");
			}
			response = crudService.addDomain(CrudComponent.USER, request, activity);
		} else {
			if (activity == null) {
				activity = crudService.generateActivity("UPDATE USER ROLE", "SYSTEM");
			}
			Map<String, Object> filter = new HashMap<String, Object>();
			filter.putAll(request);
			request.put("isActive", 1);
			response = crudService.updateDomain(CrudComponent.USER, request, filter, activity);
		}
		return response;
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
	public Map<String, Object> deleteUserRole(Map<String, Object> request, Map<String, Object> activity) throws Exception {
		/*Long userId = (Long) request.get("userId");
		Long userRoleId = (Long) request.get("roleId");
		Long mopId = (Long) request.get("mopId");*/
		Map<String, Object> response = new HashMap<String, Object>();
		
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.putAll(request);
		
		request.put("isActive", 0);
		if (activity == null) {
				activity = crudService.generateActivity("DELETE USER ROLE", "SYSTEM");
		}
		response = crudService.updateDomain(CrudComponent.USER, request, filter, activity);
			
		return response;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
	public Map<String, Object> inactivateUserRole(Map<String, Object> request) throws Exception{
		String loggedinUser = (String) request.get("loggedinUser");
		Map<String, Object> requestFilters = null;
		Map<String, Object> requestFiltersRoles = new HashMap<>();
		if(request.get("filters") != null && request.get("filters") instanceof Map) {
			requestFilters = (Map<String, Object>)request.get("filters");
			requestFiltersRoles.putAll(requestFilters);
			Iterator<String> itr = requestFiltersRoles.keySet().iterator();
			while(itr.hasNext()) {
				if(itr.next().equalsIgnoreCase("version")) {
					itr.remove();
				}
			}
		}
		
		Map<String, Object> updatedFields = null;
		Map<String, Object> updatedFieldsRole = new HashMap<>();
		if(request.get("updatedFields") != null && request.get("updatedFields") instanceof Map)
		{
			updatedFields = (Map<String, Object>)request.get("updatedFields");
			updatedFieldsRole.putAll(updatedFields);
		}
		
		Map<String, Object> activity = crudService.generateActivity("ACTIVATE / INACTIVATE USER", loggedinUser);
		
		Map<String, Object> response = crudService.updateDomain(CrudComponent.USER, updatedFields, requestFilters, activity);
		
		response = crudService.updateDomain(CrudComponent.USER, updatedFieldsRole, requestFiltersRoles, activity);
		
		
		return response;
	}
	
	public Map<String, Object> updateUserRole(Map<String, Object> request, Map<String, Object> activity) throws Exception {
		request.get("userId");
		request.get("userRoleId");
		request.get("mopId");
		
		Map<String, Object> userRoleDetails = null; //getUserRole(userId, userRoleId, mopId);
		
		Map<String, Object> response = null;
		if (userRoleDetails == null) {
			
			if (activity == null) {
				activity = crudService.generateActivity("ADD USER ROLE", "SYSTEM");
			}
			response = crudService.addDomain(CrudComponent.USER, request, activity);
			
		}
		return response;
	}

	@Override
	public Map<String, Object> reactivateUser(long userId, String user) throws Exception {
		Map<String, Object> activity = crudService.generateActivity("DELETE USER", user);
		
		Map<String, Object> updatedFields = new HashMap<String, Object>(2);
		updatedFields.put("isActive", 1);
		
		Map<String, Object> filter = new HashMap<String, Object>(2);
		filter.put("userId", userId);
		
		Map<String, Object> response = crudService.updateDomain(CrudComponent.USER, updatedFields, filter, activity);
		if (response.get(CrudConfigConstant.STATUS.getName()) == null || Boolean.FALSE.equals(response.get(CrudConfigConstant.STATUS.getName()))) {
			throw new Exception(response.toString());
		}
		return response;
	}

}
