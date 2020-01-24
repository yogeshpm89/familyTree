package com.family.tree.resources;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.family.tree.service.DocumentService;
import com.google.gson.Gson;

@javax.annotation.Generated(value = "com.rfidcollect.codegen.v3.generators.java.SpringCodegen", date = "2019-06-06T16:11:15.514Z[GMT]")
@Controller
public class FileApiController implements FileApi {

	private static final Logger logger = LoggerFactory.getLogger(LoginApiController.class);

	@Autowired
	private DocumentService documentService;

	@Override
	public ResponseEntity<Map<String, Object>> save(MultipartFile file, String details, HttpServletRequest request) {
		logger.info("uploadFile start, request = " + file.getName());
		Map<String, Object> res = new HashMap<String, Object>();
		HttpStatus httpStatus;
		
		Gson gson = new Gson();
		Map<String, Object> detailsMap = gson.fromJson(details, Map.class);
		if (!file.isEmpty()) {
			try {
				Map<String, Object> fields = new HashMap<String, Object>();
				fields.put("fileName", detailsMap.get("imageName"));
				fields.put("fileDescription", file.getOriginalFilename());
				fields.put("file", file.getBytes());
				res = documentService.save(fields);
				if ((boolean) res.get("status")) {
					Map<String, Object> saveDetailsRes = documentService.saveDetails(detailsMap, request.getUserPrincipal().getName());
					res.putAll(saveDetailsRes);
				}
				res.put("message", "File saved successfully.");
				httpStatus = HttpStatus.OK;
			} catch (Exception e) {
				try {
					documentService.delete((String)detailsMap.get("imageName"));
					res.put("message", "File already exists, file deleted successfully.");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				logger.error("uploadFile, Exception = ", e);
				res.put("status", false);
				res.put("message", e.getMessage());
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			httpStatus = HttpStatus.BAD_REQUEST;
			res.put("message", "File is empty.");
		}
		logger.info("uploadFile end, response = " + res);
		return new ResponseEntity<Map<String, Object>>(res, httpStatus);
	}

	@Override
	public ResponseEntity<Map<String, Object>> get(String fileName, HttpServletRequest request) {
		logger.info("get file start, request = " + fileName);
		Map<String, Object> res = new HashMap<String, Object>();
		HttpStatus httpStatus;
		try {
			res = documentService.get(fileName);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get file, Exception = ", e);
			res.put("status", false);
			res.put("message", e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("get file end, response = " + res);
		return new ResponseEntity<Map<String, Object>>(res, httpStatus);
	}

}
