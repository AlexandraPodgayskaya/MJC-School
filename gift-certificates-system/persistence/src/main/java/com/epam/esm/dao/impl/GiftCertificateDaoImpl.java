package com.epam.esm.dao.impl;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateSearchParameters;
import com.epam.esm.util.GiftCertificateQueryCreator;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

	private static final String INSERT_GIFT_CERTIFICATE_SQL = "INSERT INTO GIFT_CERTIFICATE (NAME, DESCRIPTION, PRICE, DURATION, CREATE_DATE, LAST_UPDATE_DATE) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SELECT_GIFT_CERTIFICATE_BY_ID_SQL = "SELECT * FROM GIFT_CERTIFICATE WHERE ID = ? AND DELETED = FALSE";
	private static final String SELECT_GIFT_CERTIFICATES_BY_SEARCH_PARAMETERS_SQL = "SELECT DISTINCT GIFT_CERTIFICATE.ID, GIFT_CERTIFICATE.NAME, GIFT_CERTIFICATE.DESCRIPTION, GIFT_CERTIFICATE.PRICE, GIFT_CERTIFICATE.DURATION, GIFT_CERTIFICATE.CREATE_DATE, GIFT_CERTIFICATE.LAST_UPDATE_DATE, GIFT_CERTIFICATE.DELETED FROM GIFT_CERTIFICATE LEFT JOIN GIFT_CERTIFICATE_TAG_CONNECTION ON GIFT_CERTIFICATE.ID = GIFT_CERTIFICATE_TAG_CONNECTION.GIFT_CERTIFICATE_ID LEFT JOIN TAG ON GIFT_CERTIFICATE_TAG_CONNECTION.TAG_ID = TAG.ID WHERE GIFT_CERTIFICATE.DELETED = FALSE AND GIFT_CERTIFICATE_TAG_CONNECTION.DELETED = FALSE AND TAG.DELETED = FALSE ";
	private static final String UPDATE_GIFT_CERTIFICATE_SQL = "UPDATE GIFT_CERTIFICATE SET NAME = ?, DESCRIPTION = ?, PRICE = ?, DURATION = ?, LAST_UPDATE_DATE = ? WHERE ID = ? ";
	private static final String DELETE_GIFT_CERTIFICATE_SQL = "UPDATE GIFT_CERTIFICATE SET DELETED = TRUE WHERE ID = ? AND DELETED = FALSE";

	// TODO SELECT * FROM GIFT_CERTIFICATE LEFT JOIN gift_certificate_tag_connection
	// ON gift_certificate.id = gift_certificate_tag_connection.gift_certificate_id
	// LEFT JOIN tag ON gift_certificate_tag_connection.tag_id = tag.id WHERE
	// gift_certificate.deleted = false AND gift_certificate_tag_connection.deleted
	// = false AND tag.deleted = false
	// SELECT * FROM GIFT_CERTIFICATE LEFT JOIN gift_certificate_tag_connection ON
	// gift_certificate.id = gift_certificate_tag_connection.gift_certificate_id
	// LEFT JOIN tag ON gift_certificate_tag_connection.tag_id = tag.id WHERE
	// gift_certificate.deleted = false AND gift_certificate_tag_connection.deleted
	// = false AND tag.deleted = false AND tag.name like 'travel' AND
	// (gift_certificate.name like "%ma%" or gift_certificate.description like
	// "%ga%") order by 'name' desc
	
	private final JdbcTemplate jdbcTemplate;
	private final GiftCertificateMapper giftCertificateMapper;
	private final GiftCertificateQueryCreator queryCreator;

	@Autowired
	public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateMapper giftCertificateMapper, GiftCertificateQueryCreator queryCreator) {
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
		List <String> parametersList = new ArrayList<>();
		String condition = queryCreator.createQuery(giftCertificateSearchParameters, parametersList);
		String query1 = SELECT_GIFT_CERTIFICATES_BY_SEARCH_PARAMETERS_SQL + condition;
		return jdbcTemplate.query(query1, giftCertificateMapper, parametersList.toArray());
	}

	@Override
	public Optional<GiftCertificate> findById(long id) {
		return jdbcTemplate.queryForStream(SELECT_GIFT_CERTIFICATE_BY_ID_SQL, giftCertificateMapper, id).findFirst();
	}

	@Override
	public List<GiftCertificate> findAll(GiftCertificateSearchParameters giftCertificateSearchParameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GiftCertificate> findByTagName(GiftCertificateSearchParameters giftCertificateSearchParameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GiftCertificate> findByTagAndPartNameOrDescription(
			GiftCertificateSearchParameters giftCertificateSearchParameters) {
		// TODO Auto-generated method stub
		return null;
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
