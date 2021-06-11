package com.epam.esm.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.util.ExceptionMessageKey;
import com.epam.esm.util.ValidValue;

@Component
public class GiftCertificateValidator {

	private static final Logger logger = LogManager.getLogger();

	public void validate(GiftCertificateDto giftCertificateDto) throws IncorrectParameterValueException {

		List<String> incorrectParameters = new ArrayList<>();

		String name = giftCertificateDto.getName();
		if (StringUtils.isBlank(name) || name.length() > ValidValue.MAX_LENGTH_NAME) {
			incorrectParameters.add(name);
			logger.debug("name error");
		}

		String description = giftCertificateDto.getDescription();
		if (StringUtils.isBlank(description) || description.length() > ValidValue.MAX_LENGTH_DESCRIPTION) {
			incorrectParameters.add(description);
			logger.debug("description error");
		}

		BigDecimal price = giftCertificateDto.getPrice();
		if (price == null || price.scale() > ValidValue.MAX_SCALE || price.compareTo(ValidValue.MIN_PRICE) < 0
				|| price.compareTo(ValidValue.MAX_PRICE) > 0) {
			incorrectParameters.add(price.toString());
			logger.debug("price error");
		}

		int duration = giftCertificateDto.getDuration();
		if (duration < ValidValue.MIN_DURATION || duration > ValidValue.MAX_DURATION) {
			incorrectParameters.add(String.valueOf(duration));
			logger.debug("duration error");
		}

		if (!incorrectParameters.isEmpty()) {
			throw new IncorrectParameterValueException("error in validating the parameters of the gift certificate",
					ExceptionMessageKey.INCORRECT_PARAMETER_VALUE,
					StringUtils.join(incorrectParameters, StringUtils.SPACE), ErrorCode.GIFT_CERTIFICATE.getCode());
		}
	}

	public void validateId(long id) throws IncorrectParameterValueException {
		if (id < ValidValue.MIN_ID) {
			logger.debug("id error");
			throw new IncorrectParameterValueException("id validation error",
					ExceptionMessageKey.INCORRECT_ID, String.valueOf(id),
					ErrorCode.GIFT_CERTIFICATE.getCode());
		}
	}
}
