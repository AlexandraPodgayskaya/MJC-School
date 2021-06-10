package com.epam.esm.dao;

import java.util.Optional;

import com.epam.esm.entity.Tag;

public interface TagDao {

	void create (Tag tag);
	Optional<Tag> findById(Long id);
	Optional<Tag> findByName(String tagName);

}
