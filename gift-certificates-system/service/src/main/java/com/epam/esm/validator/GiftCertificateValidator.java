package com.epam.esm.validator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.util.MessageKey;
import com.epam.esm.util.ValidValue;

@Component
public class GiftCertificateValidator {

	private static final Logger logger = LogManager.getLogger();

	public void validate(GiftCertificateDto giftCertificateDto) throws IncorrectParameterValueException {

		Map<String, String> incorrectParameters = new LinkedHashMap<>();

		String name = giftCertificateDto.getName();
		if (StringUtils.isBlank(name) || name.length() > ValidValue.MAX_LENGTH_NAME) {
			incorrectParameters.put(MessageKey.PARAMETER_NAME, name);
			logger.debug("gift certificate name error");
		}

		String description = giftCertificateDto.getDescription();
		if (StringUtils.isBlank(description) || description.length() > ValidValue.MAX_LENGTH_DESCRIPTION) {
			incorrectParameters.put(MessageKey.PARAMETER_DESCRIPTION, description);
			logger.debug("gift certificate description error");
		}

		BigDecimal price = giftCertificateDto.getPrice();
		if (price == null || price.scale() > ValidValue.MAX_SCALE || price.compareTo(ValidValue.MIN_PRICE) < 0
				|| price.compareTo(ValidValue.MAX_PRICE) > 0) {
			incorrectParameters.put(MessageKey.PARAMETER_PRICE, price == null ? null : price.toString());
			logger.debug("gift certificate price error");
		}

		int duration = giftCertificateDto.getDuration();
		if (duration < ValidValue.MIN_DURATION || duration > ValidValue.MAX_DURATION) {
			incorrectParameters.put(MessageKey.PARAMETER_DURATION, String.valueOf(duration));
			logger.debug("gift certificate duration error");
		}

		if (!incorrectParameters.isEmpty()) {
			throw new IncorrectParameterValueException("error in validating the parameters of the gift certificate",
					incorrectParameters, ErrorCode.GIFT_CERTIFICATE.getCode());
		}
	}

	public void validateId(long id) throws IncorrectParameterValueException {
		if (id < ValidValue.MIN_ID) {
			Map<String, String> incorrectParameter = new HashMap<>();
			incorrectParameter.put(MessageKey.PARAMETER_ID, String.valueOf(id));
			logger.debug("id error");
			throw new IncorrectParameterValueException("id validation error", incorrectParameter,
					ErrorCode.GIFT_CERTIFICATE.getCode());
		}
	}
}
