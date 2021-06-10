package com.epam.esm.validator;

public final class GiftCertificateValidator {

	private static final int MIN_ID = 1;

	private GiftCertificateValidator() {
	}

	public static boolean validateId(long id) {
		return id >= MIN_ID;
	}

}
