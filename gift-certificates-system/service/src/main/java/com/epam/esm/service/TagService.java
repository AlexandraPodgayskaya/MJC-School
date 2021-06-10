package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

public interface TagService {

	void createTag(TagDto tagDto);

	TagDto findTagById(long id);

}
