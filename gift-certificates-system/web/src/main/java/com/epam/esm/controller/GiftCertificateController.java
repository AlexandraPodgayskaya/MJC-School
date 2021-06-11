package com.epam.esm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;

@RestController
@RequestMapping("/gift-certificates")
public class GiftCertificateController {

	private final GiftCertificateService giftCertificateService;

	@Autowired
	public GiftCertificateController(GiftCertificateService giftCertificateService) {
		this.giftCertificateService = giftCertificateService;
	}

	@GetMapping("/{id}")
	public GiftCertificateDto getGiftCertificateById(@PathVariable long id) {
		return giftCertificateService.findGiftCertificateById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GiftCertificateDto createGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
		return giftCertificateService.createGiftCertificate(giftCertificateDto);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteGiftCertificate(@PathVariable long id) {
		giftCertificateService.deleteGiftCertificate(id);
	}
}
