package com.epam.esm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;

@RestController
@RequestMapping("/tags")
public class TagController {

	private final TagService tagService;

	@Autowired
	public TagController(TagService tagService) {
		this.tagService = tagService;
	}

	@GetMapping
	public List<TagDto> getTags() {
		return tagService.findAllTags();
	}

	@GetMapping("/{id}")
	public TagDto getTagById(@PathVariable long id) {
		return tagService.findTagById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TagDto addTag(@RequestBody TagDto tagDto) {
		return tagService.createTag(tagDto);
	}
}
