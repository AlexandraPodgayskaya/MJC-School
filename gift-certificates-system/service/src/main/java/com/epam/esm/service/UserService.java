package com.epam.esm.service;

import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.dto.UserDto;

public interface UserService {

	/**
	 * Find all users
	 *
	 * @param paginationDto the information about pagination
	 * @return the page with found users and total number of positions
	 */
	PageDto<UserDto> findAllUsers(PaginationDto paginationDto);

	/**
	 * Find user by id
	 * 
	 * @param id the id of user to find
	 * @return the found user
	 */
	UserDto findUserById(long id);

}
