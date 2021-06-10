package com.epam.esm.dao.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

	private static final String SELECT_GIFT_CERTIFICATE_BY_ID = "SELECT * FROM GIFT_CERTIFICATE WHERE ID = ?";

	private final JdbcTemplate jdbcTemplate;
	private final GiftCertificateMapper giftCertificateMapper;

	@Autowired
	public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateMapper giftCertificateMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.giftCertificateMapper = giftCertificateMapper;
	}

	@Override
	public Optional<GiftCertificate> findById(Long id) {
		return jdbcTemplate.queryForStream(SELECT_GIFT_CERTIFICATE_BY_ID, giftCertificateMapper, id).findFirst();
	}
}
