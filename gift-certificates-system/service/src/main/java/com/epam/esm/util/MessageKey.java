package com.epam.esm.util;

/**
 * Class presents keys by which messages will be taken from properties files
 *
 * @author Aleksandra Podgayskaya
 * @version 1.0
 */
public final class MessageKey {

	public static final String GIFT_CERTIFICATE_NOT_FOUND_BY_ID = "giftCertificate.notFoundById";
	public static final String TAG_NOT_FOUND_BY_ID = "tag.notFoundById";
	public static final String TAG_NOT_FOUND = "tag.notFound";
	public static final String ORDER_NOT_FOUND_BY_ID = "order.notFoundById";
	public static final String USER_NOT_FOUND_BY_ID = "user.notFoundById";
	public static final String INCORRECT_PARAMETER_VALUE = "incorrectParameterValue";
	public static final String INCORRECT_SORTING_PARAMETERS = "incorrectSortingParameters";
	public static final String INCORRECT_PARAMETER_TYPE = "incorrectParameterType";
	public static final String INTERNAL_ERROR = "internalError";
	public static final String PARAMETER_NAME = "parameter.name";
	public static final String PARAMETER_REPEATED_NAME = "parameter.repeatedName";
	public static final String PARAMETER_ID = "parameter.id";
	public static final String PARAMETER_DESCRIPTION = "parameter.description";
	public static final String PARAMETER_PRICE = "parameter.price";
	public static final String PARAMETER_DURATION = "parameter.duration";
	public static final String PARAMETER_PAGE_NUMBER = "parameter.pageNumber";
	public static final String PARAMETER_PAGE_SIZE = "parameter.pageSize";
	public static final String PARAMETER_ORDERED_GIFT_CERTIFICATES = "parameter.orderedGiftCertificates";
	public static final String PARAMETER_GIFT_CERTIFICATE = "parameter.giftCertificate";
	public static final String PARAMETER_NUMBER = "parameter.number";
	public static final String PARAMETER_DUPLICATE_GIFT_CERTIFICATES = "parameter.duplicateGiftCertificates";
	public static final String TYPE_ID = "type.id";
	public static final String TYPE_PRICE = "type.price";
	public static final String TYPE_DURATION = "type.duration";
	public static final String TYPE_DATA = "type.data";

	private MessageKey() {
	}

}
