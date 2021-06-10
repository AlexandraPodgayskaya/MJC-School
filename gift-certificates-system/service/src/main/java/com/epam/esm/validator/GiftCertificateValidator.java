package com.epam.esm.validator;

import org.springframework.stereotype.Component;

@Component
public class GiftCertificateValidator {

	private final int MIN_ID = 1;

	public boolean validateId(long id) {
		return id >= MIN_ID;
	}

}
