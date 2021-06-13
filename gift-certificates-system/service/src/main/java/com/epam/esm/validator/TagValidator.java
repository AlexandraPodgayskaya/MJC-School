package com.epam.esm.validator;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.util.MessageKey;
import com.epam.esm.util.ValidValue;

@Component
public class TagValidator {

	private static final Logger logger = LogManager.getLogger();

	public void validateName(String tagName) throws IncorrectParameterValueException {
		if (StringUtils.isBlank(tagName) || tagName.length() > ValidValue.MAX_LENGTH_NAME) {
			logger.debug("tag name error");
			Map<String, String> incorrectParameter = new HashMap<>();
			incorrectParameter.put(MessageKey.PARAMETER_NAME, tagName);
			throw new IncorrectParameterValueException("tag name validation error", incorrectParameter,
					ErrorCode.TAG.getCode());
		}
	}

	public void validateId(long id) throws IncorrectParameterValueException {
		if (id < ValidValue.MIN_ID) {
			logger.debug("tag id error");
			Map<String, String> incorrectParameter = new HashMap<>();
			incorrectParameter.put(MessageKey.PARAMETER_ID, String.valueOf(id));
			throw new IncorrectParameterValueException("tag id validation error", incorrectParameter,
					ErrorCode.TAG.getCode());
		}
	}
}
