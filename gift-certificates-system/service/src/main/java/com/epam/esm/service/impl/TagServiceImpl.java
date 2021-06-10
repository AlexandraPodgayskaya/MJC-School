package com.epam.esm.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ExceptionMessageKey;
import com.epam.esm.validator.TagValidator;

@Service
public class TagServiceImpl implements TagService {

	private final TagDao tagDao;
	private final TagValidator tagValidator;
	private final ModelMapper modelMapper;

	@Autowired
	public TagServiceImpl(TagDao tagDao, TagValidator tagValidator, ModelMapper modelMapper) {
		this.tagDao = tagDao;
		this.tagValidator = tagValidator;
		this.modelMapper = modelMapper;
	}

	// TODO @Transactional
	@Override
	public void createTag(TagDto tagDto) throws IncorrectParameterValueException {
		if (!tagValidator.validateName(tagDto.getName())) {
			throw new IncorrectParameterValueException("tag name validation error",
					ExceptionMessageKey.INCORRECT_PARAMETER_VALUE, tagDto.getName(), ErrorCode.TAG.getCode());
		}
		Optional<Tag> tagOptional = tagDao.findByName(tagDto.getName());
		Tag tag;
		if (tagOptional.isEmpty()) {
			tag = modelMapper.map(tagDto, Tag.class);
			tagDao.create(tag);
		} else {
			tag = tagOptional.get();
		}
		tagDto.setId(tag.getId());
		// TODO Tag createdTag = tagOptional.orElseGet(() ->
		// tagDao.create(modelMapper.map(tagDto, Tag.class)));
		// return modelMapper.map(addedTag, TagDto.class);
	}

	@Override
	public TagDto findTagById(long id) throws IncorrectParameterValueException, ResourceNotFoundException {
		if (!tagValidator.validateId(id)) {
			throw new IncorrectParameterValueException("id validation error",
					ExceptionMessageKey.INCORRECT_PARAMETER_VALUE, String.valueOf(id), ErrorCode.TAG.getCode());
		}
		Optional<Tag> foundTag = tagDao.findById(id);
		return foundTag.map(tag -> modelMapper.map(tag, TagDto.class))
				.orElseThrow(() -> new ResourceNotFoundException("no tag by id",
						ExceptionMessageKey.TAG_NOT_FOUND_BY_ID, String.valueOf(id), ErrorCode.TAG.getCode()));

	}

}
