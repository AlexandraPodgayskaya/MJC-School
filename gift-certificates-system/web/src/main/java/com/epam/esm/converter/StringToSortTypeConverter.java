package com.epam.esm.converter;

import org.springframework.core.convert.converter.Converter;

import com.epam.esm.dto.GiftCertificateSearchParametersDto.SortType;

public class StringToSortTypeConverter implements Converter<String, SortType> {

	@Override
	public SortType convert(String source) {
		return SortType.valueOf(source.toUpperCase());
	}

}
