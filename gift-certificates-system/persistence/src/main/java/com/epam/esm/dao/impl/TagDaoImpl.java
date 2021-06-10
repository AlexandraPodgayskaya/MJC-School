package com.epam.esm.dao.impl;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.Tag;

@Repository
public class TagDaoImpl implements TagDao {

	private static final String INSERT_TAG_SQL = "INSERT INTO TAG (NAME) VALUES (?)";
	private static final String SELECT_TAG_BY_ID_SQL = "SELECT * FROM TAG WHERE ID = ? AND DELETED = FALSE";
	private static final String SELECT_TAG_BY_NAME_SQL = "SELECT * FROM TAG WHERE NAME = ? AND DELETED = FALSE";

	private final JdbcTemplate jdbcTemplate;
	private final TagMapper tagMapper;

	@Autowired
	public TagDaoImpl(JdbcTemplate jdbcTemplate, TagMapper tagMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.tagMapper = tagMapper;
	}

	@Override
	public void create(Tag tag) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(INSERT_TAG_SQL, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, tag.getName());
			return statement;
		}, keyHolder);
		if (keyHolder.getKey() != null) {
			tag.setId(keyHolder.getKey().longValue());
		}
	}

	@Override
	public Optional<Tag> findById(Long id) {
		return jdbcTemplate.queryForStream(SELECT_TAG_BY_ID_SQL, tagMapper, id).findFirst();
	}

	@Override
	public Optional<Tag> findByName(String tagName) {
		return jdbcTemplate.queryForStream(SELECT_TAG_BY_NAME_SQL, tagMapper, tagName).findFirst();
	}

}
