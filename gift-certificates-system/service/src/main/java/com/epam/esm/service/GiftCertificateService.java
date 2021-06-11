package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;

public interface GiftCertificateService {

	GiftCertificateDto createGiftCertificate(GiftCertificateDto giftCertificateDto);

	GiftCertificateDto findGiftCertificateById(long id);

	void deleteGiftCertificate(long id);

}
