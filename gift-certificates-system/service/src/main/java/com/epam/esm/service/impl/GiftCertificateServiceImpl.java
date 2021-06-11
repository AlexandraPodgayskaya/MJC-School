package com.epam.esm.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
import com.epam.esm.service.TagService;
import com.epam.esm.util.ExceptionMessageKey;
import com.epam.esm.validator.GiftCertificateValidator;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

	private final GiftCertificateDao giftCertificateDao;
	private final GiftCertificateTagDao giftCertificateTagDao;
	private final GiftCertificateValidator giftCertificateValidator;
	private final TagService tagService;
	private final ModelMapper modelMapper;

	@Autowired
	public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao,
			GiftCertificateTagDao giftCertificateTagDao, GiftCertificateValidator giftCertificateValidator,
			TagService tagService, ModelMapper modelMapper) {
		this.giftCertificateDao = giftCertificateDao;
		this.giftCertificateTagDao = giftCertificateTagDao;
		this.giftCertificateValidator = giftCertificateValidator;
		this.tagService = tagService;
		this.modelMapper = modelMapper;
	}

	// TODO @Transactional
	@Override
	public GiftCertificateDto createGiftCertificate(GiftCertificateDto giftCertificateDto)
			throws IncorrectParameterValueException {
		giftCertificateValidator.validate(giftCertificateDto);
		GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
		LocalDateTime currentTime = LocalDateTime.now(ZoneOffset.UTC); // TODO Zone?
		giftCertificate.setCreateDate(currentTime);
		giftCertificate.setLastUpdateDate(currentTime);
		giftCertificateDao.create(giftCertificate);
		List<Tag> tags = giftCertificateDto.getTags().stream().distinct()
				.map(tag -> modelMapper.map(tagService.createTag(tag), Tag.class)).collect(Collectors.toList());
		giftCertificate.setTags(tags);
		giftCertificateTagDao.createConnection(giftCertificate);

		return modelMapper.map(giftCertificate, GiftCertificateDto.class);
	}

	@Override
	public GiftCertificateDto findGiftCertificateById(long id)
			throws IncorrectParameterValueException, ResourceNotFoundException {
		giftCertificateValidator.validateId(id);
		Optional<GiftCertificate> foundGiftCertificate = giftCertificateDao.findById(id);
		return foundGiftCertificate.map(this::convertGiftCertificateAndSetTags)
				.orElseThrow(() -> new ResourceNotFoundException("no gift certificate by id",
						ExceptionMessageKey.GIFT_CERTIFICATE_NOT_FOUND_BY_ID, String.valueOf(id),
						ErrorCode.GIFT_CERTIFICATE.getCode()));
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
