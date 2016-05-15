package com.cookie.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Error {
	private List<Error> errors = new ArrayList<>();
	private ERROR_CODE code;
	private String message;

	public enum ERROR_CODE {
		USER_NOT_FOUND, INVALID_USER, INVALID_FIELD, ERROR;
	}

	public void addError(Error error) {
		errors.add(error);
	}

	public void addError(ERROR_CODE code, String message) {
		Error error = new Error();
		error.setCode(code);
		error.setMessage(message);
		addError(error);
	}

	public void addError(ERROR_CODE code, Exception e) {
		Error error = new Error();
		error.setCode(code);
		error.setMessage(e.getMessage());
		addError(error);
	}

}
