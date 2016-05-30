package com.cookie.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

import com.cookie.model.Error;

import lombok.Getter;

@Getter
public class CustomDataIntegrityViolationException extends DataIntegrityViolationException {

	private static final long serialVersionUID = 6620453291591804066L;
	private Error.ERROR_CODE errorCode;

	public CustomDataIntegrityViolationException(Error.ERROR_CODE errorCode) {
		super(errorCode.getDescription());
		this.errorCode = errorCode;
	}

	public CustomDataIntegrityViolationException(Error.ERROR_CODE errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}

	public CustomDataIntegrityViolationException(String msg) {
		super(msg);
	}

}
