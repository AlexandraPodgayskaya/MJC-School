package com.epam.esm.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.IncorrectParameterValueException;
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
	public TagDto addTag(String tagName) throws IncorrectParameterValueException {
		if (!tagValidator.validateName(tagName)) {
			throw new IncorrectParameterValueException("tag name validation error",
					ExceptionMessageKey.GIFT_CERTIFICATE_INCORRECT_DATA, tagName, ErrorCode.TAG.getCode());
		}
		Optional<Tag> tagOptional = tagDao.findByName(tagName);
		//Tag addedTag = tagOptional.orElseGet(() -> tagDao.add(tagName));
		return null;//modelMapper.map(addedTag, TagDto.class);
	}

}
