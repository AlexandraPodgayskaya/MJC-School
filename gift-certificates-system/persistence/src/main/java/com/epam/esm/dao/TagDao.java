package com.epam.esm.dao;

import java.util.Optional;

import com.epam.esm.entity.Tag;

public interface TagDao {

	Optional<Tag> findByName(String tagName);

}
