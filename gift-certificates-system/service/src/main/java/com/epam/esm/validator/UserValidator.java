package com.epam.esm.validator;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.util.MessageKey;
import com.epam.esm.util.ValidValue;

@Component
public class UserValidator {

	private static final Logger logger = LogManager.getLogger();

	// TODO Abstract validator - validate id если сделать установку кода на
	// ExceptionHandlere в зависимости от uri
	public void validateId(long id) throws IncorrectParameterValueException {
		if (id < ValidValue.MIN_ID) {
			Map<String, String> incorrectParameter = new HashMap<>();
			incorrectParameter.put(MessageKey.PARAMETER_ID, String.valueOf(id));
			logger.error("id error");
			throw new IncorrectParameterValueException("id validation error", incorrectParameter,
					ErrorCode.USER.getCode());
		}
	}

}
