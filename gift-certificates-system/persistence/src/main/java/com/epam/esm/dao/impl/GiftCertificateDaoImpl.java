package com.epam.esm.dao.impl;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.creator.GiftCertificateQueryCreator;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateSearchParameters;
import com.epam.esm.entity.GiftCertificateSearchQuery;

/**
 * Class is implementation of interface {@link GiftCertificateDao} and intended
 * to work with gift_certificate table
 *
 * @author Aleksandra Podgayskaya
 * @version 1.0
 */
@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

	private static final String INSERT_GIFT_CERTIFICATE_SQL = "INSERT INTO GIFT_CERTIFICATE (NAME, "
			+ "DESCRIPTION, PRICE, DURATION, CREATE_DATE, LAST_UPDATE_DATE) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SELECT_GIFT_CERTIFICATE_BY_ID_SQL = "SELECT ID, NAME, DESCRIPTION, PRICE, "
			+ "DURATION, CREATE_DATE, LAST_UPDATE_DATE, DELETED FROM GIFT_CERTIFICATE WHERE ID = ? "
			+ "AND DELETED = FALSE";
	private static final String SELECT_GIFT_CERTIFICATE_BY_NAME_SQL = "SELECT ID, NAME, DESCRIPTION, PRICE, "
			+ "DURATION, CREATE_DATE, LAST_UPDATE_DATE, DELETED FROM GIFT_CERTIFICATE WHERE NAME = ? "
			+ "AND DELETED = FALSE";
	private static final String UPDATE_GIFT_CERTIFICATE_SQL = "UPDATE GIFT_CERTIFICATE SET NAME = ?, "
			+ "DESCRIPTION = ?, PRICE = ?, DURATION = ?, LAST_UPDATE_DATE = ? WHERE ID = ? ";
	private static final String DELETE_GIFT_CERTIFICATE_SQL = "UPDATE GIFT_CERTIFICATE SET DELETED = TRUE "
			+ "WHERE ID = ? AND DELETED = FALSE";

	private final JdbcTemplate jdbcTemplate;
	private final GiftCertificateMapper giftCertificateMapper;
	private final GiftCertificateQueryCreator queryCreator;

	@Autowired
	public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateMapper giftCertificateMapper,
			GiftCertificateQueryCreator queryCreator) {
		this.jdbcTemplate = jdbcTemplate;
		this.giftCertificateMapper = giftCertificateMapper;
		this.queryCreator = queryCreator;
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
	public List<GiftCertificate> findBySearchParameters(
			GiftCertificateSearchParameters giftCertificateSearchParameters) {
		GiftCertificateSearchQuery query = queryCreator.createQuery(giftCertificateSearchParameters);
		return jdbcTemplate.query(query.getQuery(), giftCertificateMapper, query.getParameters().toArray());
	}

	@Override
	public Optional<GiftCertificate> findByName(String name) {
		return jdbcTemplate.queryForStream(SELECT_GIFT_CERTIFICATE_BY_NAME_SQL, giftCertificateMapper, name)
				.findFirst();
	}

	@Override
	public Optional<GiftCertificate> findById(long id) {
		return jdbcTemplate.queryForStream(SELECT_GIFT_CERTIFICATE_BY_ID_SQL, giftCertificateMapper, id).findFirst();
	}

	@Override
	public GiftCertificate update(GiftCertificate giftCertificate) {
		jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE_SQL, giftCertificate.getName(), giftCertificate.getDescription(),
				giftCertificate.getPrice(), giftCertificate.getDuration(), giftCertificate.getLastUpdateDate(),
				giftCertificate.getId());
		return giftCertificate;
	}

	@Override
	public boolean delete(long id) {
		return jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_SQL, id) > 0;
	}
}
