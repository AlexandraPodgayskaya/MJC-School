package com.epam.esm.service;

import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.dto.TagDto;

/**
 * Interface for working with tag
 *
 * @author Aleksandra Podgayskaya
 * @version 1.0
 */
public interface TagService {

	/**
	 * Add new tag, if tag with such name already exists it will be returns
	 * 
	 * @param tagDto the tag dto which will be added
	 * @return he added tag dto
	 */
	TagDto createTag(TagDto tagDto);

	/**
	 * Find all tags
	 * 
	 * @return the list of all tags dto
	 */
	PageDto<TagDto> findAllTags(PaginationDto paginationDto);

	/**
	 * Find tag by id
	 * 
	 * @param id the id of tag which will be searched
	 * @return the found tag dto
	 */
	TagDto findTagById(long id);

	TagDto findMostPopularTagOfUserWithHighestCostOfAllOrders();

	/**
	 * Remove tag with all records in connection table
	 * 
	 * @param id the id of tag which will be removed
	 */
	void deleteTag(long id);

}
