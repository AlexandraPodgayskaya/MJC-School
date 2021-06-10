package com.epam.esm.dao.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;

@Repository
public class TagDaoImpl implements TagDao {

	@Override
	public Optional<Tag> findByName(String tagName) {
		// TODO Auto-generated method stub
		return null;
	}

}
