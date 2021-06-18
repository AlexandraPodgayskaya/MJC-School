package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateSearchParameters;

/**
 * Interface for working with gift_certificate table in database
 * 
 * @author Aleksandra Podgayskaya
 * @version 1.0
 */
public interface GiftCertificateDao {

	/**
	 * Add gift certificate to database
	 * 
	 * @param giftCertificate gift certificate to add
	 * @return the added gift certificate
	 */
	GiftCertificate create(GiftCertificate giftCertificate);

	/**
	 * Find gift certificates by query parameters in database
	 * 
	 * @param giftCertificateSearchParameters the gift certificate query parameters
	 * @return the list of found gift certificates if gift certificates are found,
	 *         else emptyList
	 */
	List<GiftCertificate> findBySearchParameters(GiftCertificateSearchParameters giftCertificateSearchParameters);

	/**
	 * Find gift certificate in database by id
	 * 
	 * @param id the id of gift certificate to find
	 * @return the Optional of found gift certificate
	 */
	Optional<GiftCertificate> findById(long id);

	/**
	 * Find gift certificate in database by name
	 * 
	 * @param name the name of gift certificate to find
	 * @return the Optional of found gift certificate
	 */
	Optional<GiftCertificate> findByName(String name);

	/**
	 * Update gift certificate in database
	 * 
	 * @param giftCertificate the gift certificate to update
	 * @return the updated gift certificate
	 */
	GiftCertificate update(GiftCertificate giftCertificate);

	/**
	 * Remove gift certificate from database
	 * 
	 * @param id the id of gift certificate to remove
	 * @return boolean true if everything go correct, else false
	 */
	boolean delete(long id);
}
