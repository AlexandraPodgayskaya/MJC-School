package com.epam.esm.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.esm.configuration.TestConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateSearchParameters;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class GiftCertificateDaoImplTest {

	private static GiftCertificate giftCertificate;
	private static GiftCertificate giftCertificate1;
	private static GiftCertificate giftCertificate2;
	private static GiftCertificate giftCertificate3;
	private static GiftCertificate giftCertificate4;
	private static GiftCertificateSearchParameters searchParameters1;
	private static GiftCertificateSearchParameters searchParameters2;
	private final GiftCertificateDao giftCertificateDao;

	@Autowired
	public GiftCertificateDaoImplTest(GiftCertificateDao giftCertificateDao) {
		this.giftCertificateDao = giftCertificateDao;
	}

	@BeforeAll
	public static void setUp() {
		giftCertificate = new GiftCertificate(null, "Cinema", "Best cinema in the city", new BigDecimal(100), 5,
				LocalDateTime.of(2020, 12, 12, 12, 0, 0), LocalDateTime.of(2020, 12, 13, 12, 0, 0),
				Collections.emptyList(), Boolean.FALSE);
		giftCertificate1 = new GiftCertificate(4L, "Cinema", "Best cinema in the city", new BigDecimal(100), 5,
				LocalDateTime.of(2020, 12, 12, 12, 0, 0), LocalDateTime.of(2020, 12, 13, 12, 0, 0),
				Collections.emptyList(), Boolean.FALSE);
		giftCertificate2 = new GiftCertificate(1L,
				"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
				"You will like it", new BigDecimal(100), 10, LocalDateTime.of(2020, 12, 12, 12, 0, 0),
				LocalDateTime.of(2020, 12, 13, 12, 0, 0), Collections.emptyList(), Boolean.FALSE);
		giftCertificate3 = new GiftCertificate(3L, "Horse ride", "Horseback ride for lovers - a Hollywood-style date",
				new BigDecimal("500.00"), 2, LocalDateTime.of(2017, 05, 22, 12, 46, 31),
				LocalDateTime.of(2020, 03, 20, 16, 34, 49), null, Boolean.FALSE);
		giftCertificate4 = new GiftCertificate(1L, "Travel to German", "You will like it", new BigDecimal("100.00"), 10,
				LocalDateTime.of(2020, 12, 12, 12, 0, 0), LocalDateTime.of(2020, 12, 13, 12, 0, 0),
				Collections.emptyList(), Boolean.FALSE);
		searchParameters1 = new GiftCertificateSearchParameters("travel", StringUtils.EMPTY);
		searchParameters2 = new GiftCertificateSearchParameters("holiday", "good");
	}

	@AfterAll
	public static void tearDown() {
		giftCertificate = null;
		giftCertificate1 = null;
		giftCertificate2 = null;
		giftCertificate3 = null;
		giftCertificate4 = null;
		searchParameters1 = null;
		searchParameters2 = null;
	}

	@Test
	public void createPositiveTest() {
		GiftCertificate actual = giftCertificateDao.create(giftCertificate);
		assertEquals(giftCertificate1, actual);
	}

	@Test
	public void createThrowDataIntegrityViolationExceptionTest() {
		assertThrows(DataIntegrityViolationException.class, () -> giftCertificateDao.create(giftCertificate2));
	}

	@Test
	public void findBySearchParametersPositiveTest() {
		final int expectedNumberGiftCertificates = 1;
		List<GiftCertificate> actual = giftCertificateDao.findBySearchParameters(searchParameters1);
		assertEquals(expectedNumberGiftCertificates, actual.size());
	}

	@Test
	public void findBySearchParametersNegativeTest() {
		List<GiftCertificate> actual = giftCertificateDao.findBySearchParameters(searchParameters2);
		assertTrue(actual.isEmpty());
	}

	@Test
	public void findByIdPositiveTest() {
		final long id = 3;
		Optional<GiftCertificate> actual = giftCertificateDao.findById(id);
		assertEquals(Optional.of(giftCertificate3), actual);
	}

	@Test
	public void findByIdNegativeTest() {
		final long id = 10;
		Optional<GiftCertificate> actual = giftCertificateDao.findById(id);
		assertFalse(actual.isPresent());
	}

	@Test
	public void updatePositiveTest() {
		GiftCertificate actual = giftCertificateDao.update(giftCertificate4);
		assertEquals(giftCertificate4, actual);
	}

	@Test
	public void updateThrowDataIntegrityViolationExceptionTest() {
		assertThrows(DataIntegrityViolationException.class, () -> giftCertificateDao.update(giftCertificate2));
	}

	@Test
	public void deletePositiveTest() {
		final long id = 2;
		assertTrue(giftCertificateDao.delete(id));
	}

	@Test
	public void deleteNegativeTest() {
		final long id = 0;
		assertFalse(giftCertificateDao.delete(id));
	}

}
