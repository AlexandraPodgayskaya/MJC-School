package com.epam.esm.dao;

import java.util.List;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

/**
 * Interface for working with gift_certificate_tag_connection table in database
 * 
 * @author Aleksandra Podgayskaya
 * @version 1.0
 */
public interface GiftCertificateTagDao {

	/**
	 * Add gift certificate and tag connections in database
	 * 
	 * @param giftCertificate gift certificate with tags to add connections
	 */
	void createConnection(GiftCertificate giftCertificate);

	/**
	 * Find tags by gift certificate id in database
	 * 
	 * @param giftCertificateId the id of gift certificate to find
	 * @return the list of found tags if tags are found, else emptyList
	 */
	List<Tag> findTagsByCiftCertificateId(long giftCertificateId);

	/**
	 * Remove gift certificate and tag connections in database by gift certificate
	 * id
	 * 
	 * @param id the id of gift certificate to remove connection
	 * @return boolean true if everything go correct, else false
	 */
	boolean deleteConnectionByGiftCertificateId(long id);

	/**
	 * Remove gift certificate and tag connections in database by tag id
	 * 
	 * @param id the id of tag to remove connection
	 * @return boolean true if everything go correct, else false
	 */
	boolean deleteConnectionByTagId(long id);

}
