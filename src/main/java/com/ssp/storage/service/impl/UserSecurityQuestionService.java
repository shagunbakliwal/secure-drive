package com.ssp.storage.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ssp.storage.constant.ErrorCode;
import com.ssp.storage.domain.User;
import com.ssp.storage.domain.UserSecurityQuestion;
import com.ssp.storage.exception.UserException;
import com.ssp.storage.repository.UserRepository;
import com.ssp.storage.repository.UserSecurityQuestionRepository;
import com.ssp.storage.service.IUserSecurityQuestionService;

@Service
public class UserSecurityQuestionService implements IUserSecurityQuestionService {

	@Autowired
	UserSecurityQuestionRepository userSecurityQuestionRepository;

	@Autowired
	UserRepository userRepository;

	@Value("${server.port}")
	private int port;

	@Override
	public List<String> getQuestions(String username, String password) throws UserException {
		if (!userRepository.existsByUsernameAndPassword(username, password))
			throw new UserException(port + ErrorCode.INVALID_CREDENTIALS.getKey(),
					ErrorCode.INVALID_CREDENTIALS.getValue());
		return userSecurityQuestionRepository.findAllByUserUsername(username).stream().map(a -> a.getQuestion())
				.collect(Collectors.toList());
	}

	@Transactional
	@Override
	public boolean addSecurityQuestions(String username, String password,
			List<UserSecurityQuestion> userSecurityQuestionsRequest) throws UserException {
		if (!userRepository.existsByUsernameAndPassword(username, password))
			throw new UserException(port + ErrorCode.INVALID_CREDENTIALS.getKey(),
					ErrorCode.INVALID_CREDENTIALS.getValue());
		User user = userRepository.findByUsername(username);
		for (UserSecurityQuestion userSecurityQuestion : userSecurityQuestionsRequest) {
			userSecurityQuestion.setUser(user);
		}
		try {
			userSecurityQuestionRepository.deleteByUser(user);
			userSecurityQuestionRepository.saveAll(userSecurityQuestionsRequest);
			return true;
		} catch (DataAccessException e) {
			throw new UserException(port, e.getMessage());
		}
	}

}
