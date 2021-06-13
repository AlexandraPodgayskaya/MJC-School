package com.epam.esm.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.util.MessageKey;
import com.epam.esm.validator.TagValidator;

@Service
public class TagServiceImpl implements TagService {

	private final TagDao tagDao;
	private final GiftCertificateTagDao giftCertificateTagDao;
	private final TagValidator tagValidator;
	private final ModelMapper modelMapper;

	@Autowired
	public TagServiceImpl(TagDao tagDao, GiftCertificateTagDao giftCertificateTagDao, TagValidator tagValidator,
			ModelMapper modelMapper) {
		this.tagDao = tagDao;
		this.giftCertificateTagDao = giftCertificateTagDao;
		this.tagValidator = tagValidator;
		this.modelMapper = modelMapper;
	}

	@Override
	public TagDto createTag(TagDto tagDto) throws IncorrectParameterValueException {
		tagValidator.validateName(tagDto.getName());
		Optional<Tag> tagOptional = tagDao.findByName(tagDto.getName());
		Tag createdTag = tagOptional.orElseGet(() -> tagDao.create(modelMapper.map(tagDto, Tag.class)));
		return modelMapper.map(createdTag, TagDto.class);
	}

	@Override
	public List<TagDto> findAllTags() {
		List<Tag> foundTags = tagDao.findAll();
		return foundTags.stream().map(foundTag -> modelMapper.map(foundTag, TagDto.class)).collect(Collectors.toList());
	}

	@Override
	public TagDto findTagById(long id) throws IncorrectParameterValueException, ResourceNotFoundException {
		tagValidator.validateId(id);
		Optional<Tag> foundTag = tagDao.findById(id);
		return foundTag.map(tag -> modelMapper.map(tag, TagDto.class))
				.orElseThrow(() -> new ResourceNotFoundException("no tag by id",
						MessageKey.TAG_NOT_FOUND_BY_ID, String.valueOf(id), ErrorCode.TAG.getCode()));
	}

	@Transactional
	@Override
	public void deleteTag(long id) throws IncorrectParameterValueException, ResourceNotFoundException {
		tagValidator.validateId(id);
		if (!tagDao.delete(id)) {
			throw new ResourceNotFoundException("no tag to remove by id", MessageKey.TAG_NOT_FOUND_BY_ID,
					String.valueOf(id), ErrorCode.TAG.getCode());
		}
		giftCertificateTagDao.deleteConnectionByTagId(id);
	}
}
