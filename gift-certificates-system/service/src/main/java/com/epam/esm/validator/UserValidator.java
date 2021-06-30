package com.epam.esm.validator;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.util.MessageKey;
import com.epam.esm.util.ValidValue;

/**
 * Class provides methods to validate fields of {@link UserDto}.
 *
 * @author Aleksandra Podgayskaya
 * @version 1.0
 */
@Component
public class UserValidator {

	private static final Logger logger = LogManager.getLogger();

	/**
	 * Validate user id
	 * 
	 * @param id the user id for validation
	 * @throws IncorrectParameterValueException in case incorrect id
	 */
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
