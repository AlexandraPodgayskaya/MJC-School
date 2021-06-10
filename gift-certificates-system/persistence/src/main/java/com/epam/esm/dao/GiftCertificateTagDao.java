package com.epam.esm.dao;

import java.util.List;

import com.epam.esm.entity.Tag;

public interface GiftCertificateTagDao {

	List<Tag> findTagsByCiftCertificateId(long giftCertificateId);

}
