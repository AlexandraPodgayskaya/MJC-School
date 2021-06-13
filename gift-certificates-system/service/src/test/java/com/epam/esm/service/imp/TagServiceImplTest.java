package com.epam.esm.service.imp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.validator.TagValidator;

public class TagServiceImplTest {

	private static TagDao tagDao;
	private static GiftCertificateTagDao giftCertificateTagDao;
	private static TagValidator tagValidator;
	private static ModelMapper modelMapper;
	private static TagService tagService;

	private static TagDto tagDto1;
	private static TagDto tagDto2;
	private static Tag tag1;

	@BeforeAll
	public static void setUp() {
		tagDao = mock(TagDaoImpl.class);
		giftCertificateTagDao = mock(GiftCertificateTagDao.class);
		tagValidator = mock(TagValidator.class);
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setFieldMatchingEnabled(true)
				.setSkipNullEnabled(true).setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
		tagService = new TagServiceImpl(tagDao, giftCertificateTagDao, tagValidator, modelMapper);
		tagDto1 = new TagDto("travel");
		tagDto2 = new TagDto(1L, "travel");
		tag1 = new Tag(1L, "travel", Boolean.FALSE);
	}

	@AfterAll
	public static void tearDown() {
		tagDao = null;
		giftCertificateTagDao = null;
		tagValidator = null;
		modelMapper = null;
		tagService = null;
		tagDto1 = null;
		tagDto1 = null;
		tag1 = null;
	}

	@Test
	public void createTagPositiveTest01() {
		when(tagDao.findByName(anyString())).thenReturn(Optional.empty());
		when(tagDao.create(isA(Tag.class))).thenReturn(tag1);

		TagDto actual = tagService.createTag(tagDto1);

		assertEquals(tagDto2, actual);
		verify(tagValidator, times(1)).validateName(anyString());
	}
}
