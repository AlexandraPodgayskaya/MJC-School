package com.epam.esm.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class TagValidator {

	private final int MIN_ID = 1;
	private final int MAX_LENGTH_NAME = 100;

	public boolean validateId(long id) {
		return id >= MIN_ID;
	}

	public boolean validateName(String tagName) {
		return (!StringUtils.isBlank(tagName) && tagName.length() <= MAX_LENGTH_NAME);
	}

}
