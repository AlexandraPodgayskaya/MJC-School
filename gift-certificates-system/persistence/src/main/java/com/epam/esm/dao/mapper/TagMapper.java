package com.epam.esm.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.epam.esm.entity.Tag;
import com.epam.esm.util.ColumnName;

@Component
public class TagMapper implements RowMapper<Tag> {

	@Override
	public Tag mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Tag tag = new Tag();
		tag.setId(resultSet.getLong(ColumnName.COLUMN_ID));
		tag.setName(resultSet.getString(ColumnName.COLUMN_NAME));
		tag.setDeleted(resultSet.getBoolean(ColumnName.COLUMN_DELETED));
		return tag;
	}

}
