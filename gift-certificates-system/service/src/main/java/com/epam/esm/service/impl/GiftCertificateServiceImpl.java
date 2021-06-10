package com.epam.esm.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.ExceptionMessageKey;
import com.epam.esm.validator.GiftCertificateValidator;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

	private final GiftCertificateDao giftCertificateDao;
	private final GiftCertificateTagDao giftCertificateTagDao;
	private final GiftCertificateValidator giftCertificateValidator;
	private final ModelMapper modelMapper;

	@Autowired
	public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao,
			GiftCertificateTagDao giftCertificateTagDao, GiftCertificateValidator giftCertificateValidator,
			ModelMapper modelMapper) {
		this.giftCertificateDao = giftCertificateDao;
		this.giftCertificateTagDao = giftCertificateTagDao;
		this.giftCertificateValidator = giftCertificateValidator;
		this.modelMapper = modelMapper;
	}

	@Override
	public GiftCertificateDto findGiftCertificateById(long id)
			throws IncorrectParameterValueException, ResourceNotFoundException {
		if (!giftCertificateValidator.validateId(id)) {
			throw new IncorrectParameterValueException("id validation error",
					ExceptionMessageKey.GIFT_CERTIFICATE_INCORRECT_DATA, String.valueOf(id),
					ErrorCode.GIFT_CERTIFICATE.getCode());
		}
		Optional<GiftCertificate> foundGiftCertificate = giftCertificateDao.findById(id);
		return foundGiftCertificate.map(this::convertGiftCertificateAndSetTags)
				.orElseThrow(() -> new ResourceNotFoundException("no gift certificate by this id",
						ExceptionMessageKey.GIFT_CERTIFICATE_NOT_FOUND_BY_ID, String.valueOf(id),
						ErrorCode.GIFT_CERTIFICATE.getCode()));
	}

	@Override
	public GiftCertificateDto createGiftCertificate(GiftCertificateDto giftCertificateDto) {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO change
	private GiftCertificateDto convertGiftCertificateAndSetTags(GiftCertificate giftCertificate) {
		GiftCertificateDto giftCertificateDto = modelMapper.map(giftCertificate, GiftCertificateDto.class);
		List<Tag> tags = giftCertificateTagDao.findTagsByCiftCertificateId(giftCertificate.getId());
		List<TagDto> tagsDto = tags.stream().map(tag -> modelMapper.map(tag, TagDto.class))
				.collect(Collectors.toList());
		giftCertificateDto.setTags(tagsDto);
		return giftCertificateDto;
	}

}
