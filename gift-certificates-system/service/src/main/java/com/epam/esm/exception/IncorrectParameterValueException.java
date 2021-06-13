package com.epam.esm.exception;

import java.util.Collections;
import java.util.Map;

public class IncorrectParameterValueException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private Map<String, String> parameters;
	private String errorCode;

	public IncorrectParameterValueException(String message, Map<String, String> parameters, String errorCode) {
		super(message);
		this.parameters = parameters;
		this.errorCode = errorCode;
	}

	public Map<String, String> getParameters() {
		return parameters == null ? Collections.emptyMap() : Collections.unmodifiableMap(parameters);
	}

	public String getErrorCode() {
		return errorCode;
	}
}
