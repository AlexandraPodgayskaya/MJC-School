package com.epam.esm.dao;

import java.util.Optional;

import com.epam.esm.entity.GiftCertificate;

public interface GiftCertificateDao {

	GiftCertificate create(GiftCertificate giftCertificate);

	Optional<GiftCertificate> findById(long id);

	GiftCertificate update(GiftCertificate giftCertificate);

	boolean delete(long id);
}
