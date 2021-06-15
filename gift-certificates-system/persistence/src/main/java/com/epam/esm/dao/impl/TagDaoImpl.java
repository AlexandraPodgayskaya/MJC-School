package com.epam.esm.dao.impl;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.Tag;

/**
 * Class is implementation of interface {@link TagDao} and intended to work with
 * tag table
 *
 * @author Aleksandra Podgayskaya
 * @version 1.0
 */
@Repository
public class TagDaoImpl implements TagDao {

	private static final String INSERT_TAG_SQL = "INSERT INTO TAG (NAME) VALUES (?)";
	private static final String SELECT_ALL_TAGS_SQL = "SELECT * FROM TAG WHERE DELETED = FALSE";
	private static final String SELECT_TAG_BY_ID_SQL = "SELECT * FROM TAG WHERE ID = ? AND DELETED = FALSE";
	private static final String SELECT_TAG_BY_NAME_SQL = "SELECT * FROM TAG WHERE NAME = ? AND DELETED = FALSE";
	private static final String DELETE_TAG_SQL = "UPDATE TAG SET DELETED = TRUE WHERE ID = ? AND DELETED = FALSE";

	private final JdbcTemplate jdbcTemplate;
	private final TagMapper tagMapper;

	@Autowired
	public TagDaoImpl(JdbcTemplate jdbcTemplate, TagMapper tagMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.tagMapper = tagMapper;
	}

	@Override
	public Tag create(Tag tag) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(INSERT_TAG_SQL, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, tag.getName());
			return statement;
		}, keyHolder);
		if (keyHolder.getKey() != null) {
			tag.setId(keyHolder.getKey().longValue());
		}
		return tag;
	}

	@Override
	public List<Tag> findAll() {
		return jdbcTemplate.query(SELECT_ALL_TAGS_SQL, tagMapper);
	}

	@Override
	public Optional<Tag> findById(long id) {
		return jdbcTemplate.queryForStream(SELECT_TAG_BY_ID_SQL, tagMapper, id).findFirst();
	}

	@Override
	public Optional<Tag> findByName(String tagName) {
		return jdbcTemplate.queryForStream(SELECT_TAG_BY_NAME_SQL, tagMapper, tagName).findFirst();
	}

	@Override
	public boolean delete(long id) {
		return jdbcTemplate.update(DELETE_TAG_SQL, id) > 0;
	}

}
