package com.epam.esm.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

/**
 * Class is implementation of interface {@link GiftCertificateTagDao} and
 * intended to work with gift_certificate_tag_connection table
 *
 * @author Aleksandra Podgayskaya
 * @version 1.0
 */
@Repository
public class GiftCertificateTagDaoImpl implements GiftCertificateTagDao {

	private static final String INSERT_GIFT_CERTIFICATE_TAG_CONNECTION_SQL = "INSERT INTO GIFT_CERTIFICATE_TAG_CONNECTION (GIFT_CERTIFICATE_ID, TAG_ID) VALUES (?, ?)";
	private static final String SELECT_TAGS_BY_GIFT_CERTIFICATE_ID_SQL = "SELECT TAG.ID, TAG.NAME, TAG.DELETED FROM GIFT_CERTIFICATE_TAG_CONNECTION JOIN TAG ON GIFT_CERTIFICATE_TAG_CONNECTION.TAG_ID = TAG.ID WHERE GIFT_CERTIFICATE_ID = ? AND TAG.DELETED = FALSE";
	private static final String DELETE_CONNECTION_BY_TAG_ID_SQL = "DELETE FROM GIFT_CERTIFICATE_TAG_CONNECTION WHERE TAG_ID = ?";
	private static final String DELETE_CONNECTION_BY_GIFT_CERTIFICATE_ID_SQL = "DELETE FROM GIFT_CERTIFICATE_TAG_CONNECTION WHERE GIFT_CERTIFICATE_ID = ?";

	private final JdbcTemplate jdbcTemplate;
	private final TagMapper tagMapper;

	@Autowired
	public GiftCertificateTagDaoImpl(JdbcTemplate jdbcTemplate, TagMapper tagMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.tagMapper = tagMapper;
	}

	@Override
	public void createConnection(GiftCertificate giftCertificate) {
		giftCertificate.getTags().forEach(tag -> jdbcTemplate.update(INSERT_GIFT_CERTIFICATE_TAG_CONNECTION_SQL,
				giftCertificate.getId(), tag.getId()));
	}

	@Override
	public List<Tag> findTagsByCiftCertificateId(long giftCertificateId) {
		return jdbcTemplate.query(SELECT_TAGS_BY_GIFT_CERTIFICATE_ID_SQL, tagMapper, giftCertificateId);
	}

	@Override
	public boolean deleteConnectionByGiftCertificateId(long id) {
		return jdbcTemplate.update(DELETE_CONNECTION_BY_GIFT_CERTIFICATE_ID_SQL, id) > 0;
	}

	@Override
	public boolean deleteConnectionByTagId(long id) {
		return jdbcTemplate.update(DELETE_CONNECTION_BY_TAG_ID_SQL, id) > 0;
	}

}
