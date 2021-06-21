package com.epam.esm.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateSearchParametersDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateSearchParameters;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.util.MessageKey;
import com.epam.esm.validator.GiftCertificateValidator;

/**
 * Class is implementation of interface {@link GiftCertificateService} and
 * intended to work with gift certificate
 *
 * @author Aleksandra Podgayskaya
 * @version 1.0
 */
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

	@Transactional
	@Override
	public GiftCertificateDto createGiftCertificate(GiftCertificateDto giftCertificateDto)
			throws IncorrectParameterValueException {
		giftCertificateValidator.validate(giftCertificateDto);
		checkUniquenessName(giftCertificateDto.getName());
		GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
		LocalDateTime currentTime = LocalDateTime.now();
		giftCertificate.setCreateDate(currentTime);
		giftCertificate.setLastUpdateDate(currentTime);
		GiftCertificate createdGiftCertificate = giftCertificateDao.create(giftCertificate);
		GiftCertificateDto newGiftCertificateDto = modelMapper.map(createdGiftCertificate, GiftCertificateDto.class);
		if (giftCertificateDto.getTags() != null) {
			List<Tag> tags = createTags(giftCertificateDto.getTags());
			giftCertificateTagDao.createConnection(createdGiftCertificate, tags);
			newGiftCertificateDto
					.setTags(tags.stream().map(tag -> modelMapper.map(tag, TagDto.class)).collect(Collectors.toList()));
		}
		return newGiftCertificateDto;
	}

	@Override
	public List<GiftCertificateDto> findGiftCertificates(GiftCertificateSearchParametersDto searchParametersDto) {
		GiftCertificateSearchParameters searchParameters = modelMapper.map(searchParametersDto,
				GiftCertificateSearchParameters.class);
		List<GiftCertificate> foundGiftCertificates = giftCertificateDao.findBySearchParameters(searchParameters);
		return foundGiftCertificates.stream().map(this::convertGiftCertificateAndSetTags).collect(Collectors.toList());
	}

	@Override
	public GiftCertificateDto findGiftCertificateById(long id)
			throws IncorrectParameterValueException, ResourceNotFoundException {
		giftCertificateValidator.validateId(id);
		Optional<GiftCertificate> foundGiftCertificate = giftCertificateDao.findById(id);
		return foundGiftCertificate.map(this::convertGiftCertificateAndSetTags)
				.orElseThrow(() -> new ResourceNotFoundException("no gift certificate by id",
						MessageKey.GIFT_CERTIFICATE_NOT_FOUND_BY_ID, String.valueOf(id),
						ErrorCode.GIFT_CERTIFICATE.getCode()));
	}

	@Transactional
	@Override
	public GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto) {
		GiftCertificateDto foundGiftCertificateDto = findGiftCertificateById(giftCertificateDto.getId());
		String giftCertificateName = giftCertificateDto.getName();
		if (giftCertificateName != null && !giftCertificateName.equals(foundGiftCertificateDto.getName())) {
			checkUniquenessName(giftCertificateDto.getName());
		}
		updateFields(foundGiftCertificateDto, giftCertificateDto);
		giftCertificateValidator.validate(foundGiftCertificateDto);
		GiftCertificate updatedGiftCertificate = giftCertificateDao
				.update(modelMapper.map(foundGiftCertificateDto, GiftCertificate.class));
		GiftCertificateDto updatedGiftCertificateDto = modelMapper.map(updatedGiftCertificate,
				GiftCertificateDto.class);
		if (giftCertificateDto.getTags() != null) {
			List<Tag> tags = createTags(giftCertificateDto.getTags());
			giftCertificateTagDao.deleteConnectionByGiftCertificateId(updatedGiftCertificate.getId());
			giftCertificateTagDao.createConnection(updatedGiftCertificate, tags);
			updatedGiftCertificateDto
					.setTags(tags.stream().map(tag -> modelMapper.map(tag, TagDto.class)).collect(Collectors.toList()));
		} else {
			updatedGiftCertificateDto.setTags(foundGiftCertificateDto.getTags());
		}
		return updatedGiftCertificateDto;
	}

	@Transactional
	@Override
	public void deleteGiftCertificate(long id) throws IncorrectParameterValueException, ResourceNotFoundException {
		giftCertificateValidator.validateId(id);
		if (!giftCertificateDao.delete(id)) {
			throw new ResourceNotFoundException("no gift certificate to remove by id",
					MessageKey.GIFT_CERTIFICATE_NOT_FOUND_BY_ID, String.valueOf(id),
					ErrorCode.GIFT_CERTIFICATE.getCode());
		}
		giftCertificateTagDao.deleteConnectionByGiftCertificateId(id);
	}

	private void checkUniquenessName(String name) {
		Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.findByName(name);
		if (giftCertificateOptional.isPresent()) {
			Map<String, String> incorrectParameter = new HashMap<>();
			incorrectParameter.put(MessageKey.PARAMETER_REPEATED_NAME, name);
			throw new IncorrectParameterValueException("repeated gift certificate name", incorrectParameter,
					ErrorCode.GIFT_CERTIFICATE.getCode());
		}
	}

	private List<Tag> createTags(List<TagDto> tagsToCreate) {
		return tagsToCreate.stream().distinct().map(tag -> modelMapper.map(tagService.createTag(tag), Tag.class))
				.collect(Collectors.toList());
	}

	private GiftCertificateDto convertGiftCertificateAndSetTags(GiftCertificate giftCertificate) {
		GiftCertificateDto giftCertificateDto = modelMapper.map(giftCertificate, GiftCertificateDto.class);
		List<Tag> tags = giftCertificateTagDao.findTagsByCiftCertificateId(giftCertificate.getId());
		List<TagDto> tagsDto = tags.stream().map(tag -> modelMapper.map(tag, TagDto.class))
				.collect(Collectors.toList());
		giftCertificateDto.setTags(tagsDto);
		return giftCertificateDto;
	}

	private void updateFields(GiftCertificateDto foundGiftCertificate, GiftCertificateDto receivedGiftCertificate) {
		if (receivedGiftCertificate.getName() != null) {
			foundGiftCertificate.setName(receivedGiftCertificate.getName());
		}
		if (receivedGiftCertificate.getDescription() != null) {
			foundGiftCertificate.setDescription(receivedGiftCertificate.getDescription());
		}
		if (receivedGiftCertificate.getPrice() != null) {
			foundGiftCertificate.setPrice(receivedGiftCertificate.getPrice());
		}
		if (receivedGiftCertificate.getDuration() != null) {
			foundGiftCertificate.setDuration(receivedGiftCertificate.getDuration());
		}
		foundGiftCertificate.setLastUpdateDate(LocalDateTime.now());
	}
}
