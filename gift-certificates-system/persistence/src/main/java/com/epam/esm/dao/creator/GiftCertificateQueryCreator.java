package com.epam.esm.dao.creator;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Class designed to create query
 *
 * @author Aleksandra Podgayskaya
 * @version 1.0
 */
@Component
public class GiftCertificateQueryCreator {

	private static final String SELECT_GIFT_CERTIFICATES = "SELECT ID, NAME, DESCRIPTION, PRICE, DURATION, "
			+ "CREATE_DATE, LAST_UPDATE_DATE, DELETED FROM GIFT_CERTIFICATE WHERE DELETED = FALSE ";
	private static final String TAG_NAME_CONDITION = " AND ID IN "
			+ "(SELECT GIFT_CERTIFICATE_TAG_CONNECTION.GIFT_CERTIFICATE_ID FROM "
			+ "GIFT_CERTIFICATE_TAG_CONNECTION JOIN TAG ON GIFT_CERTIFICATE_TAG_CONNECTION.TAG_ID "
			+ "= TAG.ID WHERE TAG.DELETED = FALSE AND TAG.NAME IN (?  ";
	private static final String PART_NAME_OR_DESCRIPTION_CONDITION = " AND (GIFT_CERTIFICATE.NAME LIKE ?  "
			+ "OR GIFT_CERTIFICATE.DESCRIPTION LIKE ? ) ";
	private static final String ZERO_OR_MORE_CHARACTERS = "%";
	private static final String SEPARATOR_TAGS = ",";
	private static final String ADDITIONAL_REQUEST_PARAMETER = ", ?";
	private static final String END_QUERY = ")) ";
	private static final String ORDER_BY = " ORDER BY ";

	/**
	 * Create query
	 * 
	 * @param searchParameters the gift certificate query parameters
	 * @return the created query
	 */
	public GiftCertificateSearchQuery createQuery(GiftCertificateSearchParameters searchParameters) {
		GiftCertificateSearchQuery giftCertificateSearchQuery = new GiftCertificateSearchQuery();
		StringBuilder query = new StringBuilder();
		query.append(SELECT_GIFT_CERTIFICATES);
		if (StringUtils.isNotBlank(searchParameters.getTagName())) {
			createTagNameCondition(searchParameters.getTagName(), query, giftCertificateSearchQuery);
		}
		if (StringUtils.isNotBlank(searchParameters.getPartNameOrDescription())) {
			createPartNameOrDescriptionCondition(searchParameters.getPartNameOrDescription(), query,
					giftCertificateSearchQuery);
		}
		if (searchParameters.getSortType() != null) {
			query.append(ORDER_BY + searchParameters.getSortType());
			if (searchParameters.getOrderType() != null) {
				query.append(StringUtils.SPACE + searchParameters.getOrderType());
			}
		}
		giftCertificateSearchQuery.setQuery(query.toString());
		return giftCertificateSearchQuery;
	}

	private void createTagNameCondition(String tagName, StringBuilder query,
			GiftCertificateSearchQuery giftCertificateSearchQuery) {
		query.append(TAG_NAME_CONDITION);
		if (tagName.contains(SEPARATOR_TAGS)) {
			List<String> tags = Arrays.asList(tagName.split(SEPARATOR_TAGS));
			tags.forEach(tag -> giftCertificateSearchQuery.addParameter(tag.trim()));
			final int numberAdditionalTagNames = tags.size() - 1;
			query.append(StringUtils.repeat(ADDITIONAL_REQUEST_PARAMETER, numberAdditionalTagNames));
		} else {
			giftCertificateSearchQuery.addParameter(tagName);
		}
		query.append(END_QUERY);
	}

	private void createPartNameOrDescriptionCondition(String partNameOrDescription, StringBuilder query,
			GiftCertificateSearchQuery giftCertificateSearchQuery) {
		query.append(PART_NAME_OR_DESCRIPTION_CONDITION);
		giftCertificateSearchQuery
				.addParameter(ZERO_OR_MORE_CHARACTERS + partNameOrDescription + ZERO_OR_MORE_CHARACTERS);
		giftCertificateSearchQuery
				.addParameter(ZERO_OR_MORE_CHARACTERS + partNameOrDescription + ZERO_OR_MORE_CHARACTERS);
	}
}