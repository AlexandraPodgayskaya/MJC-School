package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;

public interface GiftCertificateService {

	GiftCertificateDto createGiftCertificate(GiftCertificateDto giftCertificateDto);

	GiftCertificateDto findGiftCertificateById(long id);
	
	GiftCertificateDto updateGiftCertificate (GiftCertificateDto giftCertificateDto);

	void deleteGiftCertificate(long id);

}
