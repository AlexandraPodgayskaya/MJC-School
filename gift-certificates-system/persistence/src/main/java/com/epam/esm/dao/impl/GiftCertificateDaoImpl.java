package com.epam.esm.dao.impl;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Statement;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

	private static final String INSERT_GIFT_CERTIFICATE_SQL = "INSERT INTO GIFT_CERTIFICATE (NAME, DESCRIPTION, PRICE, DURATION, CREATE_DATE, LAST_UPDATE_DATE) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SELECT_GIFT_CERTIFICATE_BY_ID_SQL = "SELECT * FROM GIFT_CERTIFICATE WHERE ID = ? AND DELETED = FALSE";

	private final JdbcTemplate jdbcTemplate;
	private final GiftCertificateMapper giftCertificateMapper;

	@Autowired
	public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateMapper giftCertificateMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.giftCertificateMapper = giftCertificateMapper;
	}

	@Override
	public GiftCertificate create(GiftCertificate giftCertificate) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(INSERT_GIFT_CERTIFICATE_SQL,
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, giftCertificate.getName());
			statement.setString(2, giftCertificate.getDescription());
			statement.setBigDecimal(3, giftCertificate.getPrice());
			statement.setInt(4, giftCertificate.getDuration());
			statement.setTimestamp(5, Timestamp.valueOf(giftCertificate.getCreateDate()));
			statement.setTimestamp(6, Timestamp.valueOf(giftCertificate.getLastUpdateDate()));
			return statement;
		}, keyHolder);
		if (keyHolder.getKey() != null) {
			giftCertificate.setId(keyHolder.getKey().longValue());
		}
		return giftCertificate;
	}

	@Override
	public Optional<GiftCertificate> findById(long id) {
		return jdbcTemplate.queryForStream(SELECT_GIFT_CERTIFICATE_BY_ID_SQL, giftCertificateMapper, id).findFirst();
	}

}
