package com.epam.esm.converter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.epam.esm.dto.PaginationDto;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.util.MessageKey;

@Component
public class ParametersToDtoConverter {

	private static final String NUMBER_AND_SIZE_PATTERN = "^[1-9]+$";
	private static final String NUMBER = "number";
	private static final String SIZE = "size";
	private static final String DEFAULT_NUMBER = "1";
	private static final String DEFAULT_SIZE = "5";

	public PaginationDto getPaginationDto(Map<String, String> parameters) {
		String number = parameters.get(NUMBER) == null ? DEFAULT_NUMBER : parameters.get(NUMBER);
		String size = parameters.get(SIZE) == null ? DEFAULT_SIZE : parameters.get(SIZE);
		checkParametersValue(number, size);
		int offset = (Integer.valueOf(number) - 1) * Integer.valueOf(size);
		int limit = Integer.valueOf(size);
		return new PaginationDto(offset, limit);

	}

	private void checkParametersValue(String number, String size) {
		if (!number.matches(NUMBER_AND_SIZE_PATTERN)) {
			Map<String, String> incorrectParameters = new HashMap<>();
			incorrectParameters.put(MessageKey.PARAMETER_PAGE_NUMBER, number);
			if (!size.matches(NUMBER_AND_SIZE_PATTERN)) {
				incorrectParameters.put(MessageKey.PARAMETER_PAGE_SIZE, size);
			}
			throw new IncorrectParameterValueException("error checking page parameters", incorrectParameters,
					ErrorCode.DEFAULT.getCode());
		}
	}

}
