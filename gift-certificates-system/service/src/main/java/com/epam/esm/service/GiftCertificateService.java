package com.epam.esm.service;

import java.util.List;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateSearchParametersDto;

/**
 * Interface for working with gift certificate
 *
 * @author Aleksandra Podgayskaya
 * @version 1.0
 */
public interface GiftCertificateService {

	/**
	 * Add new gift certificate and records with gift certificate id and tags id to
	 * connection table. If gift certificate has new tags they will be added.
	 * 
	 * @param giftCertificateDto the gift certificate dto which will be added
	 * @return the added gift certificate dto
	 */
	GiftCertificateDto createGiftCertificate(GiftCertificateDto giftCertificateDto);

	/**
	 * Find gift certificates by search parameters
	 *
	 * @param giftCertificateSearchParametersDto parameters according to which will
	 *                                           be found gift certificates
	 * @return the list of found gift certificates
	 */
	List<GiftCertificateDto> findGiftCertificates(
			GiftCertificateSearchParametersDto giftCertificateSearchParametersDto);

	/**
	 * Find gift certificate by id
	 * 
	 * @param id the id of gift certificate which will be searched
	 * @return the found gift certificate dto
	 */
	GiftCertificateDto findGiftCertificateById(long id);

	/**
	 * Update gift certificate by fields, if field value is null it will be not
	 * updated, if gift certificate has new tags they will be added
	 * 
	 * @param giftCertificateDto the gift certificate dto which will be updated
	 * @return the updated gift certificate dto
	 */
	GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto);

	/**
	 * Remove gift certificate with all records in connection table
	 * 
	 * @param id the id of gift certificate which will be removed
	 */
	void deleteGiftCertificate(long id);

}
