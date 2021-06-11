package com.epam.esm.validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.util.ExceptionMessageKey;
import com.epam.esm.util.ValidValue;

@Component
public class TagValidator {

	private static final Logger logger = LogManager.getLogger();

	public void validateName(String tagName) throws IncorrectParameterValueException {
		if (StringUtils.isBlank(tagName) || tagName.length() > ValidValue.MAX_LENGTH_NAME) {
			logger.debug("id error");
			throw new IncorrectParameterValueException("tag name validation error",
					ExceptionMessageKey.INCORRECT_PARAMETER_VALUE, tagName, ErrorCode.TAG.getCode());
		}
	}

	public void validateId(long id) throws IncorrectParameterValueException {
		if (id < ValidValue.MIN_ID) {
			logger.debug("id error");
			throw new IncorrectParameterValueException("id validation error", ExceptionMessageKey.INCORRECT_ID,
					String.valueOf(id), ErrorCode.TAG.getCode());
		}
	}
}
