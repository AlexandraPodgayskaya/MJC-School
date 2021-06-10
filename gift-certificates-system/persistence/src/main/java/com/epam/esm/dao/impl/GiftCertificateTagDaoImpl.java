package com.epam.esm.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.Tag;

@Repository
public class GiftCertificateTagDaoImpl implements GiftCertificateTagDao {

	private static final String SELECT_TAGS_BY_GIFT_CERTIFICATE_ID = "SELECT TAG.ID, TAG.NAME, TAG.DELETED FROM GIFT_CERTIFICATE_TAG_CONNECTION JOIN TAG ON GIFT_CERTIFICATE_TAG_CONNECTION.TAG_ID = TAG.ID WHERE GIFT_CERTIFICATE_ID = ? AND GIFT_CERTIFICATE_TAG_CONNECTION.DELETED = FALSE AND TAG.DELETED = FALSE";

	private final JdbcTemplate jdbcTemplate;
	private final TagMapper tagMapper;

	@Autowired
	public GiftCertificateTagDaoImpl(JdbcTemplate jdbcTemplate, TagMapper tagMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.tagMapper = tagMapper;
	}

	/*
	 * TODO jdbcTemplate.query(SELECT_TAGS_BY_GIFT_CERTIFICATE_ID, new Object[] {
	 * giftCertificateId }, new int[] { Types.BIGINT }, tagMapper);
	 */
	@Override
	public List<Tag> findTagsByCiftCertificateId(long giftCertificateId) {
		return jdbcTemplate.query(SELECT_TAGS_BY_GIFT_CERTIFICATE_ID, tagMapper, giftCertificateId);
	}

}
