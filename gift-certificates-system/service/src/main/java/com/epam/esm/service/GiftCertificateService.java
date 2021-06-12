package com.epam.esm.service;

import java.util.List;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateSearchParametersDto;

public interface GiftCertificateService {

	GiftCertificateDto createGiftCertificate(GiftCertificateDto giftCertificateDto);

	List <GiftCertificateDto> findGiftCertificates(GiftCertificateSearchParametersDto giftCertificateSearchParametersDto);
	
	GiftCertificateDto findGiftCertificateById(long id);
	
	GiftCertificateDto updateGiftCertificate (GiftCertificateDto giftCertificateDto);

	void deleteGiftCertificate(long id);

}
