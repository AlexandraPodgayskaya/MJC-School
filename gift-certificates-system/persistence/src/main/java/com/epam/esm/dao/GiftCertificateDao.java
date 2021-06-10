package com.epam.esm.dao;

import java.util.Optional;

import com.epam.esm.entity.GiftCertificate;

public interface GiftCertificateDao {

	Optional<GiftCertificate> findById(Long id);
}
