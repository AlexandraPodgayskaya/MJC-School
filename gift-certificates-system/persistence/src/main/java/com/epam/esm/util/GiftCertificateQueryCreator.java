package com.epam.esm.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.epam.esm.entity.GiftCertificateSearchParameters;

@Component
public class GiftCertificateQueryCreator {

	private static final String SELECT_GIFT_CERTIFICATES_BY_TAG_NAME = "SELECT * FROM GIFT_CERTIFICATE WHERE ID IN (SELECT GIFT_CERTIFICATE_TAG_CONNECTION.GIFT_CERTIFICATE_ID FROM GIFT_CERTIFICATE_TAG_CONNECTION JOIN TAG ON GIFT_CERTIFICATE_TAG_CONNECTION.TAG_ID = TAG.ID WHERE TAG.NAME = ? AND TAG.DELETED = FALSE) AND DELETED = FALSE ";
	private static final String SELECT_GIFT_CERTIFICATES = "SELECT * FROM GIFT_CERTIFICATE WHERE DELETED = FALSE ";
	private static final String PART_NAME_OR_DESCRIPTION_CONDITION = " AND (GIFT_CERTIFICATE.NAME OR GIFT_CERTIFICATE.DESCRIPTION LIKE ? ) ";
	private static final String ZERO_OR_MORE_CHARACTERS = "%";
	private static final String ORDER_BY = " ORDER BY ";

	public String createQuery(GiftCertificateSearchParameters searchParameters, List<String> parametersList) {
		StringBuilder query = new StringBuilder();
		if (StringUtils.isNotBlank(searchParameters.getTagName())) {
			query.append(SELECT_GIFT_CERTIFICATES_BY_TAG_NAME);
			parametersList.add(searchParameters.getTagName());
		} else {
			query.append(SELECT_GIFT_CERTIFICATES);
		}
		if (StringUtils.isNotBlank(searchParameters.getPartNameOrDescription())) {
			query.append(PART_NAME_OR_DESCRIPTION_CONDITION);
			parametersList.add(
					ZERO_OR_MORE_CHARACTERS + searchParameters.getPartNameOrDescription() + ZERO_OR_MORE_CHARACTERS);
		}
		if (searchParameters.getSortType() != null) {
			query.append(ORDER_BY + searchParameters.getSortType());
			if (searchParameters.getOrderType() != null) {
				query.append(StringUtils.SPACE + searchParameters.getOrderType());
			}
		}
		return query.toString();
	}
}