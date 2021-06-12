package com.epam.esm.converter;

import org.springframework.core.convert.converter.Converter;

import com.epam.esm.dto.GiftCertificateSearchParametersDto.OrderType;

public class StringToOrderTypeConverter implements Converter<String, OrderType> {

	@Override
	public OrderType convert(String source) {
		return OrderType.valueOf(source.toUpperCase());
	}

}
