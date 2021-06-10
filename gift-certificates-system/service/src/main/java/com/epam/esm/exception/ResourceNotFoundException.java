package com.epam.esm.exception;

public class ResourceNotFoundException extends ServiceException {
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message, String messageKey, String messageParameter, String errorCode) {
		super(message, messageKey, messageParameter, errorCode);
	}
}
