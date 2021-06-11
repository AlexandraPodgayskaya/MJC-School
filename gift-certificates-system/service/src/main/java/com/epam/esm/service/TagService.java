package com.epam.esm.service;

import java.util.List;

import com.epam.esm.dto.TagDto;

public interface TagService {

	TagDto createTag(TagDto tagDto);

	List<TagDto> findAllTags();

	TagDto findTagById(long id);

}
