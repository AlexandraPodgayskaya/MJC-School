package com.epam.esm.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.ColumnName;

@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {

	@Override
	public GiftCertificate mapRow(ResultSet resultSet, int rowNum) throws SQLException {

		GiftCertificate giftCertificate = new GiftCertificate();
		giftCertificate.setId(resultSet.getLong(ColumnName.COLUMN_ID));
		giftCertificate.setName(resultSet.getString(ColumnName.COLUMN_NAME));
		giftCertificate.setDescription(resultSet.getString(ColumnName.COLUMN_DESCRIPTION));
		giftCertificate.setPrice(resultSet.getBigDecimal(ColumnName.COLUMN_PRICE));
		giftCertificate.setDuration(resultSet.getInt(ColumnName.COLUMN_DURATION));
		giftCertificate.setCreateDate(resultSet.getObject(ColumnName.COLUMN_CREATE_DATE, LocalDateTime.class));
		giftCertificate.setLastUpdateDate(resultSet.getObject(ColumnName.COLUMN_LAST_UPDATE_DATE, LocalDateTime.class));

		return giftCertificate;
	}
}
