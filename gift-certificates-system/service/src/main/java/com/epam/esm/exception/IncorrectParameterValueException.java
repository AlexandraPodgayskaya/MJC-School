package com.epam.esm.exception;

public class IncorrectParameterValueException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public IncorrectParameterValueException(String message, String messageKey, String messageParameter,
			String errorCode) {
		super(message, messageKey, messageParameter, errorCode);
	}

}
