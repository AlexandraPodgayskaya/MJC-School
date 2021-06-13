package com.epam.esm.exception;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String messageKey;
	private String messageParameter;
	private String errorCode;

	public ResourceNotFoundException(String message, String messageKey, String messageParameter, String errorCode) {
		super(message);
		this.messageKey = messageKey;
		this.messageParameter = messageParameter;
		this.errorCode = errorCode;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public String getMessageParameter() {
		return messageParameter;
	}

	public String getErrorCode() {
		return errorCode;
	}
}