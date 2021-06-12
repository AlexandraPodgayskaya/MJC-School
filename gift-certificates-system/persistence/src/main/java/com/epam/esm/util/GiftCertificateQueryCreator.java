package com.epam.esm.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.epam.esm.entity.GiftCertificateSearchParameters;

@Component
public class GiftCertificateQueryCreator {

	private static final String TAG_NAME_CONDITION = " AND TAG.NAME = ? ";
	private static final String PART_NAME_OR_DESCRIPTION_CONDITION = " AND GIFT_CERTIFICATE.NAME OR GIFT_CERTIFICATE.DESCRIPTION LIKE ? ";
	private static final String ZERO_OR_MORE_CHARACTERS = "%";
	private static final String ORDER_BY = " ORDER BY ";

	public String createQuery(GiftCertificateSearchParameters searchParameters, List<String> parametersList) {
		StringBuilder condition = new StringBuilder();
		if (StringUtils.isNotBlank(searchParameters.getTagName())) {
			condition.append(TAG_NAME_CONDITION);
			parametersList.add(searchParameters.getTagName());
		}
		if (StringUtils.isNotBlank(searchParameters.getPartNameOrDescription())) {
			condition.append(PART_NAME_OR_DESCRIPTION_CONDITION);
			parametersList.add(
					ZERO_OR_MORE_CHARACTERS + searchParameters.getPartNameOrDescription() + ZERO_OR_MORE_CHARACTERS);
		}
		if (searchParameters.getSortType() != null) {
			condition.append(ORDER_BY + searchParameters.getSortType());
			if (searchParameters.getOrderType() != null) {
				condition.append(StringUtils.SPACE + searchParameters.getOrderType());
			}
		}
		return condition.toString();
	}
}