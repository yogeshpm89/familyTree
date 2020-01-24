package com.family.tree.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.family.tree.constant.Activity;
import com.family.tree.constant.CrudComponent;
import com.family.tree.constant.CrudConfigConstant;
import com.family.tree.constant.EmailBody;
import com.family.tree.constant.ExceptionMessage;
import com.family.tree.constant.ValidationMessage;
import com.family.tree.properties.ApplicationProperties;
import com.family.tree.resources.LoginApiController;

@Service
public class DocumentServiceImpl implements DocumentService {

	private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);
	
	@Autowired
	private ApplicationProperties applicationProperties;
	
	@Autowired
	private CrudService crudService;
	
	@Override
	public Map<String, Object> save(Map<String, Object> fields) throws Exception {
		logger.info("save start");
		Map<String, Object> response = new HashMap<String, Object>();
		File outputDir = getOutputFileDirectory();
		File file = new File(outputDir.getAbsolutePath() + "//" + fields.get("fileName").toString());
		if (file.createNewFile()) {
			OutputStream os = new FileOutputStream(file);
			try {
				 os.write((byte[]) fields.get("file"));
			} finally {
				os.flush();
				os.close();
			}
			response.put("status", true);
			logger.info("saved successfully");
		} else {
			response.put("status", false);
			logger.error("file already present");
		}
		logger.info("save end");
		return response;
	}
	
	@Override
	public Map<String, Object> delete(String fileName) throws Exception {
		logger.info("file delete start, fileName = " + fileName);
		Map<String, Object> response = new HashMap<String, Object>();
		if (fileName != null && !"".equalsIgnoreCase(fileName)) {
			File outputDir = getOutputFileDirectory();
			File file = new File(outputDir.getAbsolutePath() + "//" + fileName);
			if (file.exists()) {
				if (file.delete()) {
					response.put("status", true);
					logger.info("deleted successfully");
				} else {
					response.put("status", false);
					logger.error("delete faile");
				}
			} else {
				logger.info("file does not exists");
			}
		}
		logger.info("file delete end, fileName = " + fileName);
		return response;
	}
	
	private File getOutputFileDirectory() {
		String outputFileDirectory = applicationProperties.getOutputFileDirectory();
		File file = new File(outputFileDirectory);
		if (file.exists()) {
			return file;
		}
		return null;
	}

	@Override
	public Map<String, Object> saveDetails(Map<String, Object> request, String loggedinuser) throws Exception {
		logger.info("saveDetails start");
		Map<String, Object> requestObject = getRequestObject(request);
		Map<String, Object> activity = crudService.generateActivity(Activity.ADD_IMAGE_IMAGE_DETAILS.getName(), loggedinuser);
		Map<String, Object> addResponse = crudService.addDomain(CrudComponent.IMAGEDETAILS, requestObject, activity);
		if(addResponse.get(CrudConfigConstant.STATUS.getName()) == null || 
				Boolean.FALSE.equals(addResponse.get(CrudConfigConstant.STATUS.getName()))) {
			throw new Exception(addResponse.toString());
		}
		logger.info("saveDetails end");
		return addResponse;
	}

	private Map<String, Object> getRequestObject(Map<String, Object> request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] intAttrs = new String[] {"country", "state", "city", "baseCountry", "baseState", "baseCity", "mobile"};
		String[] stringAttrs = new String[] {"firstName", "lastName", "parentName", "parentName1", "parentName2", "baseAddress", "nookh", "currentAddress", "imageName"};
		for (String attr: intAttrs) populateIntegerAttr(request, map, attr);
		for (String attr: stringAttrs) populateStringAttr(request, map, attr);
		return map;
	}
	
	private void populateIntegerAttr(Map<String, Object> request, Map<String, Object> map, String key) {
		if (request.containsKey(key) && request.get(key) != null) {
			Double d = new Double(request.get(key).toString());
			map.put(key, d.intValue());
		}
	}
	
	private void populateStringAttr(Map<String, Object> request, Map<String, Object> map, String key) {
		if (request.containsKey(key) && request.get(key) != null) {
			map.put(key, request.get(key).toString());
		}
	}

	@Override
	public Map<String, Object> get(String fileName) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		String dir = applicationProperties.getOutputFileDirectory();
		File file = new File(dir + "//" + fileName);
		if (file.exists()) {
				byte[] bytesArray = new byte[(int) file.length()]; 
				FileInputStream fis = new FileInputStream(file);
				fis.read(bytesArray); //read file into bytes[]
				fis.close();
				
				response.put("status", true);
				response.put("data", bytesArray);
				
		} else {
			response.put("status", false);
			response.put("message", ExceptionMessage.FILE_NOT_PRESENT);
		}
		return response;
	}

}
