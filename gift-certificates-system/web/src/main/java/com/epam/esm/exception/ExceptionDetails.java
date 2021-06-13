package com.epam.esm.exception;

public final class ExceptionDetails {

	private final String errorMessage;
	private final String errorCode;

	public ExceptionDetails(String errorMessage, String errorCode) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

}
