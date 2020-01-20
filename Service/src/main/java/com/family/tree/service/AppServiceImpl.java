package com.family.tree.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.family.tree.constant.ComponentConfigFilter;
import com.family.tree.model.Component;

@Service
public class AppServiceImpl implements AppService {

	@Autowired
	ComponentService componentService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	CrudService crudService;
	

	@Override
	// @Cacheable("config")
	public Map<String, String> getMailSettings() throws Exception {
		Map<String, Object> map = new HashMap<>();
        map.put("inputRequest", "/component/mailSettings");
        
        List<Map<String, Object>> filters = new ArrayList<>();
        map.put(ComponentConfigFilter.FILTER.getName(), filters);
        
        com.family.tree.model.Component<Object> component = componentService.getComponent(map);
        List<Object> list = component.getData();
        
        Map<String, String> responseMap = new HashMap<String, String>();
        Iterator<Object> iterator = list.iterator();
        while(iterator.hasNext()) {
        	Map<String, Object> object = (Map<String, Object>) iterator.next();
        	
        	responseMap.put((String)object.get("propertyName"), (String)object.get("propertyValue"));
        }
		return responseMap;
	}
	
	
	@Override
	// @Cacheable("config")
	public Map<String, String> getApplicationURL() throws Exception {
		Map<String, Object> map = new HashMap<>();
        map.put("inputRequest", "/component/appUrl");
        
        List<Map<String, Object>> filters = new ArrayList<>();
        map.put(ComponentConfigFilter.FILTER.getName(), filters);
        
        Component<Object> component = componentService.getComponent(map);
        List<Object> list = component.getData();
        
        Map<String, String> responseMap = new HashMap<String, String>();
        Iterator<Object> iterator = list.iterator();
        while(iterator.hasNext()) {
        	Map<String, Object> object = (Map<String, Object>) iterator.next();
        	responseMap.put((String)object.get("propertyName"), (String)object.get("propertyValue"));
        }
		return responseMap;
	}

	/*@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
	public Map<String, Object> saveMailSettings(Map<String, Object> request) throws Exception {
		String loggedinUser = (String) request.get("loggedinUser");
		Map<String, Object> response = new HashMap<String, Object>();
		
		List<Map<String, Object>> properties = (List<Map<String, Object>>) request.get("properties");
		Map<String, Object> activity = crudService.generateActivity("ADD / UPDATE MAIL SETTINGS", loggedinUser);
		
		Map<String, Object> requestFilters = new HashMap<String, Object>();
		
		// 	TODO - ADD FOLLOWING VALDIATIONS
		*//**
		 * Properties table contains sensitive data
		 * Add validation to check if property name can only be 
		 * 	1. APP_MAIL_SMTP_HOST
			2. APP_MAIL_SMTP_USER
			3. APP_MAIL_SMTP_PASSWORD
			4. APP_MAIL_SMTP_PORT
			5. APP_MAIL_SMTP_AUTH
			6. APP_MAIL_SMTP_STARTTLS_ENABLE
		 *//*
		for (Map<String, Object> property: properties) {
			requestFilters.clear();
			String propertyName = (String) property.get("propertyName");
			requestFilters.put("propertyName", propertyName);
			response = crudService.updateDomain(CrudComponent.APPPROPERTIES, property, requestFilters, activity);
			
			if (response.get("status") == null || Boolean.FALSE.equals(response.get("status"))) {
				throw new Exception(response.toString());
			}
		}
		response.put("status", Boolean.TRUE);
		return response;
	}*/

	@Override
	public Map<String, Object> sendTestMail(String to) throws Exception {
		String[] toA = new String[1];
		toA[0] = to;
		emailService.sendMail(toA, "MOPStar Test Mail Subject", "MOPStar Test Mail Body");
		return null;
	}

}
