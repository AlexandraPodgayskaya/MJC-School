package com.epam.esm.validator;

import org.apache.commons.lang3.StringUtils;

public final class TagValidator {

	private static final int MAX_LENGTH_NAME = 100;

	private TagValidator() {
	}

	public static boolean validateName(String tagName) {
		return (!StringUtils.isBlank(tagName) && tagName.length() <= MAX_LENGTH_NAME);
	}

}
