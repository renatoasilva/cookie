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
		USER_NOT_FOUND("User not found"),
		USER_ALREADY_EXISTS("User already exists"),
		INVALID_USER("Invalid user"),
		INVALID_FIELD("Invalid field"),
		ERROR("Unexpected Error");

		private String description;

		private ERROR_CODE(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
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
