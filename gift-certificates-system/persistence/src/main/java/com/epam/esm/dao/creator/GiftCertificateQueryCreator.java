package com.epam.esm.dao.creator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.epam.esm.entity.GiftCertificateSearchParameters;
import com.epam.esm.entity.GiftCertificateSearchQuery;

/**
 * Class designed to create query
 *
 * @author Aleksandra Podgayskaya
 * @version 1.0
 */
@Component
public class GiftCertificateQueryCreator {

	private static final String SELECT_GIFT_CERTIFICATES = "SELECT * FROM GIFT_CERTIFICATE WHERE DELETED = FALSE ";
	private static final String TAG_NAME_CONDITION = " AND ID IN (SELECT GIFT_CERTIFICATE_TAG_CONNECTION.GIFT_CERTIFICATE_ID FROM GIFT_CERTIFICATE_TAG_CONNECTION JOIN TAG ON GIFT_CERTIFICATE_TAG_CONNECTION.TAG_ID = TAG.ID WHERE TAG.NAME = ? AND TAG.DELETED = FALSE) ";
	private static final String PART_NAME_OR_DESCRIPTION_CONDITION = " AND (GIFT_CERTIFICATE.NAME OR GIFT_CERTIFICATE.DESCRIPTION LIKE ? ) ";
	private static final String ZERO_OR_MORE_CHARACTERS = "%";
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
			query.append(TAG_NAME_CONDITION);
			giftCertificateSearchQuery.addParameter(searchParameters.getTagName());
		}
		if (StringUtils.isNotBlank(searchParameters.getPartNameOrDescription())) {
			query.append(PART_NAME_OR_DESCRIPTION_CONDITION);
			giftCertificateSearchQuery.addParameter(
					ZERO_OR_MORE_CHARACTERS + searchParameters.getPartNameOrDescription() + ZERO_OR_MORE_CHARACTERS);
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
}