package com.epam.esm.service.imp;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateSearchParametersDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateSearchParameters;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.validator.GiftCertificateValidator;

public class GiftCertificateServiceImplTest {

	private static GiftCertificateDao giftCertificateDao;
	private static GiftCertificateTagDao giftCertificateTagDao;
	private static GiftCertificateValidator giftCertificateValidator;
	private static TagService tagService;
	private static ModelMapper modelMapper;
	private static GiftCertificateService giftCertificateService;
	private static GiftCertificateDto giftCertificateDto1;
	private static GiftCertificateDto giftCertificateDto2;
	private static GiftCertificateDto giftCertificateDto3;
	private static GiftCertificateDto giftCertificateDto4;
	private static GiftCertificate giftCertificate1;
	private static GiftCertificate giftCertificate2;

	@BeforeAll
	public static void setUp() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setFieldMatchingEnabled(true)
				.setSkipNullEnabled(true).setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
		giftCertificateDto1 = new GiftCertificateDto(null, "Cinema", "Best cinema in the city", new BigDecimal(100), 5,
				LocalDateTime.of(2020, 12, 12, 12, 0, 0), LocalDateTime.of(2020, 12, 13, 12, 0, 0),
				Collections.emptyList());
		giftCertificateDto2 = new GiftCertificateDto(1L, "Cinema", "Best cinema in the city", new BigDecimal(100), 5,
				LocalDateTime.of(2020, 12, 12, 12, 0, 0), LocalDateTime.of(2020, 12, 13, 12, 0, 0),
				Collections.emptyList());
		giftCertificateDto3 = new GiftCertificateDto(1L, "", "", new BigDecimal(100), -5,
				LocalDateTime.of(2020, 12, 12, 12, 0, 0), LocalDateTime.of(2020, 12, 13, 12, 0, 0),
				Collections.emptyList());
		giftCertificateDto4 = new GiftCertificateDto(1L, "Travel to German", "You will like it", new BigDecimal(100),
				10, LocalDateTime.of(2020, 12, 12, 12, 0, 0), LocalDateTime.of(2020, 12, 13, 12, 0, 0),
				Collections.emptyList());
		giftCertificate1 = new GiftCertificate(1L, "Cinema", "Best cinema in the city", new BigDecimal(100), 5,
				LocalDateTime.of(2020, 12, 12, 12, 0, 0), LocalDateTime.of(2020, 12, 13, 12, 0, 0), Boolean.FALSE);
		giftCertificate2 = new GiftCertificate(1L, "Travel to German", "You will like it", new BigDecimal(100), 10,
				LocalDateTime.of(2020, 12, 12, 12, 0, 0), LocalDateTime.of(2020, 12, 13, 12, 0, 0), Boolean.FALSE);
	}

	@BeforeEach
	public void init() {
		giftCertificateDao = mock(GiftCertificateDao.class);
		giftCertificateTagDao = mock(GiftCertificateTagDao.class);
		giftCertificateValidator = mock(GiftCertificateValidator.class);
		tagService = mock(TagService.class);
		giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDao, giftCertificateTagDao,
				giftCertificateValidator, tagService, modelMapper);
	}

	@AfterAll
	public static void tearDown() {
		giftCertificateDao = null;
		giftCertificateTagDao = null;
		giftCertificateValidator = null;
		tagService = null;
		giftCertificateService = null;
		giftCertificateDto1 = null;
		giftCertificateDto2 = null;
		giftCertificateDto3 = null;
		giftCertificateDto4 = null;
		giftCertificate1 = null;
		giftCertificate2 = null;
	}

	@Test
	public void createGiftCertificatePositiveTest() {
		doNothing().when(giftCertificateValidator).validate(isA(GiftCertificateDto.class));
		when(giftCertificateDao.create(isA(GiftCertificate.class))).thenReturn(giftCertificate1);
		GiftCertificateDto actual = giftCertificateService.createGiftCertificate(giftCertificateDto1);
		assertEquals(giftCertificateDto2, actual);
	}

	@Test
	public void createGiftCertificateThrowIncorrectParameterValueExceptionTest() {
		doThrow(new IncorrectParameterValueException()).when(giftCertificateValidator)
				.validate(isA(GiftCertificateDto.class));
		assertThrows(IncorrectParameterValueException.class,
				() -> giftCertificateService.createGiftCertificate(giftCertificateDto3));
	}

	@Test
	public void findGiftCertificatesPositiveTest() {
		final int expectedNumberGiftCertificates = 2;
		when(giftCertificateDao.findBySearchParameters(isA(GiftCertificateSearchParameters.class)))
				.thenReturn(Arrays.asList(giftCertificate1, giftCertificate2));
		when(giftCertificateTagDao.findTagsByCiftCertificateId(anyLong())).thenReturn(Collections.emptyList());
		List<GiftCertificateDto> actual = giftCertificateService
				.findGiftCertificates(new GiftCertificateSearchParametersDto());
		assertEquals(expectedNumberGiftCertificates, actual.size());
	}

	@Test
	void findGiftCertificateByIdPositiveTest() {
		final long id = 1;
		doNothing().when(giftCertificateValidator).validateId(anyLong());
		when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.of(giftCertificate1));
		when(giftCertificateTagDao.findTagsByCiftCertificateId(anyLong())).thenReturn(Collections.emptyList());
		GiftCertificateDto actual = giftCertificateService.findGiftCertificateById(id);
		assertEquals(giftCertificateDto2, actual);
	}

	@Test
	public void findGiftCertificateByIdThrowIncorrectParameterValueExceptionTest() {
		final long id = 0;
		doThrow(new IncorrectParameterValueException()).when(giftCertificateValidator).validateId(anyLong());
		assertThrows(IncorrectParameterValueException.class, () -> giftCertificateService.findGiftCertificateById(id));
	}

	@Test
	public void findGiftCertificateByIdThrowResourceNotFoundExceptionTest() {
		final long id = 5L;
		doNothing().when(giftCertificateValidator).validateId(anyLong());
		when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.findGiftCertificateById(id));
	}

	@Test
	public void updateGiftCertificatePositiveTest() {
		when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.of(giftCertificate2));
		doNothing().when(giftCertificateValidator).validate(isA(GiftCertificateDto.class));
		when(giftCertificateDao.update(isA(GiftCertificate.class))).thenReturn(giftCertificate1);
		GiftCertificateDto actual = giftCertificateService.updateGiftCertificate(giftCertificateDto4);
		assertEquals(giftCertificateDto2, actual);
	}

	@Test
	public void updateGiftCertificateThrowIncorrectParameterValueExceptionTest() {
		when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.of(giftCertificate2));
		doThrow(new IncorrectParameterValueException()).when(giftCertificateValidator)
				.validate(isA(GiftCertificateDto.class));
		assertThrows(IncorrectParameterValueException.class,
				() -> giftCertificateService.updateGiftCertificate(giftCertificateDto3));
	}

	@Test
	public void updateGiftCertificateThrowResourceNotFoundExceptionTest() {
		when(giftCertificateDao.findById(anyLong())).thenThrow(ResourceNotFoundException.class);
		assertThrows(ResourceNotFoundException.class,
				() -> giftCertificateService.updateGiftCertificate(giftCertificateDto2));
	}

	@Test
	public void deleteGiftCertificatePositiveTest() {
		final long id = 1;
		doNothing().when(giftCertificateValidator).validateId(anyLong());
		when(giftCertificateDao.delete(anyLong())).thenReturn(Boolean.TRUE);
		when(giftCertificateTagDao.deleteConnectionByTagId(anyLong())).thenReturn(Boolean.TRUE);
		assertDoesNotThrow(() -> giftCertificateService.deleteGiftCertificate(id));
	}

	@Test
	public void deleteGiftCertificateThrowIncorrectParameterValueExceptionTest() {
		final long id = -5;
		doThrow(new IncorrectParameterValueException()).when(giftCertificateValidator).validateId(anyLong());
		assertThrows(IncorrectParameterValueException.class, () -> giftCertificateService.deleteGiftCertificate(id));
	}

	@Test
	public void deleteGiftCertificateThrowResourceNotFoundExceptionTest() {
		final long id = 10;
		doNothing().when(giftCertificateValidator).validateId(anyLong());
		when(giftCertificateDao.delete(anyLong())).thenReturn(Boolean.FALSE);
		assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.deleteGiftCertificate(id));
	}
}
