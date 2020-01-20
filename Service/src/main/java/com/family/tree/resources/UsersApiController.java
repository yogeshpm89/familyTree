package com.family.tree.resources;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.family.tree.constant.ExceptionMessage;
import com.family.tree.constant.Response;
import com.family.tree.model.User;
import com.family.tree.service.UserService;

@Controller
public class UsersApiController implements UsersApi {

	private static final Logger LOGGER = LoggerFactory.getLogger(UsersApiController.class);

	@Autowired
	UserService userService;

	public ResponseEntity<Map<String, Object>> usersPost(@RequestBody Map<String, Object> body,
			HttpServletRequest request) {
		Map<String, Object> response = null;
		HttpStatus httpStatus;
		try {
			response = userService.addUser(body, request.getUserPrincipal().getName());
			httpStatus = HttpStatus.CREATED;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionMessage.ADD_USER.getName(), e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.put(Response.STATUS.getName(), false);
			response.put(Response.REASON.getName(), e.getMessage());
		}
		return new ResponseEntity<Map<String, Object>>(response, httpStatus);
	}

	public ResponseEntity<Void> usersUserIdDelete(@PathVariable("userId") Long userId, HttpServletRequest request) {
		return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
	}

	public ResponseEntity<Map<String, Object>> usersUserIdPut(@PathVariable("userId") Long userId,
			@RequestBody Map<String, Object> body, HttpServletRequest request) {
		Map<String, Object> response = null;
		HttpStatus httpStatus;
		try {
			response = userService.updateUser(userId, body, request.getUserPrincipal().getName());
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionMessage.UPDATE_USER.getName(), e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.put(Response.STATUS.getName(), false);
			response.put(Response.REASON.getName(), e.getMessage());
		}
		return new ResponseEntity<Map<String, Object>>(response, httpStatus);
	}

	public ResponseEntity<Map<String, Object>> usersForgotPasswordPost(@RequestBody Map<String, Object> body,
			HttpServletRequest request) {
		Map<String, Object> response = new HashMap<String, Object>();
		HttpStatus httpStatus;
		try {
			response = userService.forgotPassword(body);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionMessage.FORGOT_PASSWORD.getName(), e);
			response.put(Response.STATUS.getName(), false);
			response.put(Response.REASON.getName(), e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(response, httpStatus);
	}

	@Override
	public ResponseEntity<Map<String, Object>> usersResetPasswordPost(Map<String, Object> body,
			HttpServletRequest request) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			response = userService.resetPassword(body);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionMessage.FORGOT_PASSWORD.getName(), e);
			response.put(Response.STATUS.getName(), false);
			response.put(Response.REASON.getName(), e.getMessage());
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
