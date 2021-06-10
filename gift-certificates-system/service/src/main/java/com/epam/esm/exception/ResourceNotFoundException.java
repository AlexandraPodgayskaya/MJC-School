package com.epam.esm.exception;

public class ResourceNotFoundException extends ServiceException {
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
		super();
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}

	public ResourceNotFoundException(Exception e) {
		super(e);
	}

	public ResourceNotFoundException(String message, Exception e) {
		super(message, e);
	}

	public ResourceNotFoundException(String message, String messageKey, String messageParameter, String errorCode) {
		super(message, messageKey, messageParameter, errorCode);
	}
}
