package com.epam.esm.exception;

public class IncorrectParameterValueException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public IncorrectParameterValueException() {
		super();
	}

	public IncorrectParameterValueException(String message) {
		super(message);
	}

	public IncorrectParameterValueException(Exception e) {
		super(e);
	}

	public IncorrectParameterValueException(String message, Exception e) {
		super(message, e);
	}

	public IncorrectParameterValueException(String message, String messageKey, String messageParameter, String errorCode) {
		super(message, messageKey, messageParameter, errorCode);
	}

}
