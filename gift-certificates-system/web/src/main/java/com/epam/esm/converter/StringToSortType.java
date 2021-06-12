package com.epam.esm.converter;

import org.springframework.core.convert.converter.Converter;

import com.epam.esm.entity.SortType;

public class StringToSortType implements Converter<String, SortType> {

	@Override
	public SortType convert(String source) {
		return SortType.valueOf(source.toUpperCase());
	}

}
