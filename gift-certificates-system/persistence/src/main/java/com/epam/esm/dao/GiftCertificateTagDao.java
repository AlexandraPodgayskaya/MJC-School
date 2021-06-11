package com.epam.esm.dao;

import java.util.List;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

public interface GiftCertificateTagDao {

	void createConnection(GiftCertificate giftCertificate);

	List<Tag> findTagsByCiftCertificateId(long giftCertificateId);

}
