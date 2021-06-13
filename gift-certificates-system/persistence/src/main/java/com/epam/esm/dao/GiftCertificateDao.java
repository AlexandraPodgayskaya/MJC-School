package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateSearchParameters;

public interface GiftCertificateDao {

	GiftCertificate create(GiftCertificate giftCertificate);

	List<GiftCertificate> findBySearchParameters(GiftCertificateSearchParameters giftCertificateSearchParameters);

	Optional<GiftCertificate> findById(long id);

	GiftCertificate update(GiftCertificate giftCertificate);

	boolean delete(long id);
}
