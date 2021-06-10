package com.epam.esm.exception;

//TODO конструкторы не используются
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String messageKey;
	private String messageParameter;
	private String errorCode;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Exception e) {
		super(e);
	}

	public ServiceException(String message, Exception e) {
		super(message, e);
	}

	public ServiceException(String message, String messageKey, String messageParameter, String errorCode) {
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
