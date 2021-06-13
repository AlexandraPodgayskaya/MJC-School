package com.epam.esm.service.imp;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.exception.ResourceNotFoundException;
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
	private static TagDto tagDto3;
	private static Tag tag1;
	private static Tag tag2;

	@BeforeAll
	public static void setUp() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setFieldMatchingEnabled(true)
				.setSkipNullEnabled(true).setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
		tagDto1 = new TagDto("travel");
		tagDto2 = new TagDto(1L, "travel");
		tagDto3 = new TagDto("");
		tag1 = new Tag(1L, "travel", Boolean.FALSE);
		tag2 = new Tag(2L, "gift", Boolean.FALSE);
	}

	@BeforeEach
	public void init() {
		tagDao = mock(TagDaoImpl.class);
		giftCertificateTagDao = mock(GiftCertificateTagDao.class);
		tagValidator = mock(TagValidator.class);
		tagService = new TagServiceImpl(tagDao, giftCertificateTagDao, tagValidator, modelMapper);
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
		tagDto3 = null;
		tag1 = null;
		tag2 = null;
	}

	@Test
	public void createTagPositiveTest() {
		doNothing().when(tagValidator).validateName(anyString());
		when(tagDao.findByName(anyString())).thenReturn(Optional.empty());
		when(tagDao.create(isA(Tag.class))).thenReturn(tag1);
		TagDto actual = tagService.createTag(tagDto1);
		assertEquals(tagDto2, actual);
	}

	@Test // TODO (static and @BeforeAll)
	public void createTagThrowIncorrectParameterValueExceptionTest() {
		doThrow(new IncorrectParameterValueException()).when(tagValidator).validateName(anyString());
		// TODO надо ли предопределять другое поведение mock когда знаю, что на
		// валидаторе выполнение остановится
		when(tagDao.findByName(anyString())).thenReturn(Optional.empty());
		when(tagDao.create(isA(Tag.class))).thenReturn(tag1);
		assertThrows(IncorrectParameterValueException.class, () -> tagService.createTag(tagDto3));
	}

	@Test
	public void findAllTagsPositiveTest() {
		final int expectedNumberTags = 2;
		when(tagDao.findAll()).thenReturn(Arrays.asList(tag1, tag2));
		List<TagDto> actual = tagService.findAllTags();
		assertEquals(expectedNumberTags, actual.size());
	}

	@Test
	public void findTagByIdPositiveTest() {
		final long id = 1L;
		doNothing().when(tagValidator).validateId(anyLong());
		when(tagDao.findById(anyLong())).thenReturn(Optional.of(tag1));
		TagDto actual = tagService.findTagById(id);
		assertEquals(tagDto2, actual);
	}

	@Test
	public void findTagByIdThrowIncorrectParameterValueExceptionTest() {
		final long id = 0;
		doThrow(new IncorrectParameterValueException()).when(tagValidator).validateId(anyLong());// TODO
		when(tagDao.findById(anyLong())).thenReturn(Optional.empty());
		assertThrows(IncorrectParameterValueException.class, () -> tagService.findTagById(id));
	}

	@Test
	public void findTagByIdThrowResourceNotFoundExceptionTest() {
		final long id = 5L;
		doNothing().when(tagValidator).validateId(anyLong());
		when(tagDao.findById(anyLong())).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> tagService.findTagById(id));
	}

	@Test
	public void deleteTagPositiveTest() {
		final long id = 1;
		doNothing().when(tagValidator).validateId(anyLong());
		when(tagDao.delete(anyLong())).thenReturn(Boolean.TRUE);
		when(giftCertificateTagDao.deleteConnectionByTagId(anyLong())).thenReturn(Boolean.TRUE);
		assertDoesNotThrow(() -> tagService.deleteTag(id));
	}

	@Test
	public void deleteTagThrowIncorrectParameterValueExceptionTest() {
		final long id = -5;
		doThrow(new IncorrectParameterValueException()).when(tagValidator).validateId(anyLong());// TODO
		when(tagDao.delete(anyLong())).thenReturn(Boolean.FALSE);
		when(giftCertificateTagDao.deleteConnectionByTagId(anyLong())).thenReturn(Boolean.FALSE);
		assertThrows(IncorrectParameterValueException.class, () -> tagService.deleteTag(id));
	}

	@Test
	public void deleteTagThrowResourceNotFoundExceptionTest() {
		final long id = 10;
		doNothing().when(tagValidator).validateId(anyLong());
		when(tagDao.delete(anyLong())).thenReturn(Boolean.FALSE);
		when(giftCertificateTagDao.deleteConnectionByTagId(anyLong())).thenReturn(Boolean.FALSE);
		assertThrows(ResourceNotFoundException.class, () -> tagService.deleteTag(id));
	}
}
