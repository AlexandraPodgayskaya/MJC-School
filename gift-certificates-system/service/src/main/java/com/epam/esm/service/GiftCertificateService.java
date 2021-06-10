package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.ServiceException;

public interface GiftCertificateService {

	GiftCertificateDto findGiftCertificateById(long id) throws ServiceException;

	GiftCertificateDto createGiftCertificate(GiftCertificateDto giftCertificateDto);

}
