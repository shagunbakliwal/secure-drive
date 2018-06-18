package com.ssp.storage.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssp.storage.domain.User;
import com.ssp.storage.error.AcgError;
import com.ssp.storage.exception.UserException;
import com.ssp.storage.service.IUserService;
import com.ssp.storage.web.ResponseEntity;

import io.swagger.annotations.Api;

@Api
@RestController
@RequestMapping("/api/user")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Value("${server.port}")
	private int port;

	@Autowired
	IUserService userService;

	@GetMapping("/")
	public ResponseEntity<?> authenticateUser(@RequestHeader(name = "username") String username,
			@RequestHeader(name = "password") String password) {
		try {
			return new ResponseEntity<>(userService.authenticate(username, password), HttpStatus.OK);
		} catch (UserException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(new AcgError(e.getErrorCode(), e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/addUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addCustomer(@Valid @RequestBody User user) {
		try {
			return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
		} catch (UserException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(new AcgError(e.getErrorCode(), e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
