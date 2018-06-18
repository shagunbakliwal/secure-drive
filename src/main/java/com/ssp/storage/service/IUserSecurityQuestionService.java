package com.ssp.storage.service;

import java.util.List;

import com.ssp.storage.domain.UserSecurityQuestion;
import com.ssp.storage.exception.UserException;

public interface IUserSecurityQuestionService {

	List<String> getQuestions(String username, String password) throws UserException;

	boolean addSecurityQuestions(String username, String password,
			List<UserSecurityQuestion> userSecurityQuestionsRequest) throws UserException;
}
