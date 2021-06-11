package com.epam.esm.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
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
	public TagDto createTag(TagDto tagDto) throws IncorrectParameterValueException {
		tagValidator.validateName(tagDto.getName());
		Optional<Tag> tagOptional = tagDao.findByName(tagDto.getName());
		Tag createdTag = tagOptional.orElseGet(() -> tagDao.create(modelMapper.map(tagDto, Tag.class)));
		tagDto.setId(createdTag.getId());//TODO or return modelMapper.map(tag. TagDto.class)
		return tagDto;
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
						ExceptionMessageKey.TAG_NOT_FOUND_BY_ID, String.valueOf(id), ErrorCode.TAG.getCode()));
	}
}
