package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

import com.epam.esm.entity.Tag;

public interface TagDao {

	Tag create(Tag tag);

	List<Tag> findAll();

	Optional<Tag> findById(long id);

	Optional<Tag> findByName(String tagName);

	boolean delete(long id);

}
