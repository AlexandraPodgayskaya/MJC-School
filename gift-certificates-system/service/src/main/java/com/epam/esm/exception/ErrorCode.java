package com.epam.esm.exception;

public enum ErrorCode {

	GIFT_CERTIFICATE("01"), TAG("02");

	private final String code;

	private ErrorCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
