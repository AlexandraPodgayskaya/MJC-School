package com.epam.esm.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.ColumnName;

/**
 * Class used by JdbcTemplate for mapping rows of a ResultSet on
 * {@link GiftCertificate}.
 *
 * @author Aleksandra Podgayskaya
 * @version 1.0
 */
@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {

	/**
	 * Get gift certificate from ResultSet
	 * 
	 * @param resultSet the ResultSet to map (pre-initialized for the current row)
	 * @param rowNum    the number of the current row
	 * @return the result object for the current row
	 * @throws SQLException if an SQLException is encountered getting column values
	 */
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
		giftCertificate.setDeleted(resultSet.getBoolean(ColumnName.COLUMN_DELETED));
		return giftCertificate;
	}
}
