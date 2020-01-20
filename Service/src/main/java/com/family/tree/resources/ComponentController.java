package com.family.tree.resources;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.family.tree.model.Component;
import com.family.tree.service.ComponentService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping(
        value = {"/data"},
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ComponentController
{
	@Autowired
	private ComponentService componentService;
	
	private static final Logger logger = LogManager.getLogger("ComponentController");
	
	@RequestMapping(value = "/component/{componentName}", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	public Component getComponent(@PathVariable("componentName") String componentName, @RequestBody Map<String, Object> request, HttpServletRequest httpServletRequest)
	{
		logger.info("get component start, componentName = " + componentName + ", request = " + request);
		request.put("inputRequest", "/component/" + componentName);
		Component component = null;
		try {
			component = componentService.getComponent(request);
			logger.info("get component end, component.getCount() = " + component.getCount());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("activateInactivateUser, Exception = " + e.getLocalizedMessage());
			component = new Component<String>(); 
			component.setCount(0);
			component.setData(null);
		}
		return component;
	}

}
