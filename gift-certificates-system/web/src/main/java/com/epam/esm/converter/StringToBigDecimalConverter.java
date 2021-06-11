package com.epam.esm.converter;

import java.math.BigDecimal;

import org.springframework.core.convert.converter.Converter;
//TODO delete
public class StringToBigDecimalConverter implements Converter<String, BigDecimal> {

	@Override
	public BigDecimal convert(String source) {	
		return new BigDecimal(source.replace(",", "."));
	}

}
