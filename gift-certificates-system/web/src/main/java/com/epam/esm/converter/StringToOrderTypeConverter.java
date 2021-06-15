package com.epam.esm.converter;

import org.springframework.core.convert.converter.Converter;

import com.epam.esm.dto.GiftCertificateSearchParametersDto.OrderType;

/**
 * Class converts String to OrderType
 *
 * @author Aleksandra Podgayskaya
 * @version 1.0
 * @see Converter
 *
 */
public class StringToOrderTypeConverter implements Converter<String, OrderType> {

	@Override
	public OrderType convert(String source) {
		return OrderType.valueOf(source.toUpperCase());
	}

}
