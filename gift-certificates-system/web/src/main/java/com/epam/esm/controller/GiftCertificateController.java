package com.epam.esm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateSearchParametersDto;
import com.epam.esm.service.GiftCertificateService;

/**
 * Class is an endpoint of the API which allows to perform CRUD operations on
 * gift certificates
 * 
 * @author Aleksandra Podgayskaya
 * @version 1.0
 */

@RestController
@RequestMapping("/gift-certificates")
public class GiftCertificateController {

	private final GiftCertificateService giftCertificateService;

	@Autowired
	public GiftCertificateController(GiftCertificateService giftCertificateService) {
		this.giftCertificateService = giftCertificateService;
	}

	/**
	 * Get gift certificates by parameters, processes GET requests at
	 * /gift-certificates
	 * 
	 * @param giftCertificateSearchParametersDto the gift certificate search
	 *                                           parameters
	 * @return the list of found gift certificates dto
	 */
	@GetMapping
	public List<GiftCertificateDto> getGiftCertificates(
			GiftCertificateSearchParametersDto giftCertificateSearchParametersDto) {
		return giftCertificateService.findGiftCertificates(giftCertificateSearchParametersDto);
	}

	/**
	 * Get gift certificate by id, processes GET requests at /gift-certificates/{id}
	 * 
	 * @param id the gift certificate id which will be found
	 * @return the found gift certificate dto
	 */
	@GetMapping("/{id}")
	public GiftCertificateDto getGiftCertificateById(@PathVariable long id) {
		return giftCertificateService.findGiftCertificateById(id);
	}

	/**
	 * Add new gift certificate, processes POST requests at /gift-certificates
	 * 
	 * @param giftCertificateDto the new gift certificate which will be added
	 * @return the added gift certificate dto
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GiftCertificateDto createGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
		return giftCertificateService.createGiftCertificate(giftCertificateDto);
	}

	/**
	 * Update gift certificate, processes PATCH requests at /gift-certificates/{id}
	 * 
	 * @param id                 the gift certificate id which will be updated
	 * @param giftCertificateDto the gift certificate dto with updated fields
	 * @return the updated gift certificate dto
	 */
	@PatchMapping("/{id}")
	public GiftCertificateDto updateGiftCertificate(@PathVariable long id,
			@RequestBody GiftCertificateDto giftCertificateDto) {
		giftCertificateDto.setId(id);
		return giftCertificateService.updateGiftCertificate(giftCertificateDto);
	}

	/**
	 * Delete gift certificate by id, processes DELETE requests at
	 * /gift-certificates/{id}
	 * 
	 * @param id the gift certificate id which will be deleted
	 */
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteGiftCertificate(@PathVariable long id) {
		giftCertificateService.deleteGiftCertificate(id);
	}
}
