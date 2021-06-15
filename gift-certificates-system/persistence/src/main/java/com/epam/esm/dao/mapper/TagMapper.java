package com.epam.esm.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.epam.esm.entity.Tag;
import com.epam.esm.util.ColumnName;

/**
 * Class used by JdbcTemplate for mapping rows of a ResultSet on {@link Tag}.
 *
 * @author Aleksandra Podgayskaya
 * @version 1.0
 */
@Component
public class TagMapper implements RowMapper<Tag> {

	/**
	 * Get tag from ResultSet
	 * 
	 * @param resultSet the ResultSet to map (pre-initialized for the current row)
	 * @param rowNum    the number of the current row
	 * @return the result object for the current row
	 * @throws SQLException if an SQLException is encountered getting column values
	 */
	@Override
	public Tag mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Tag tag = new Tag();
		tag.setId(resultSet.getLong(ColumnName.COLUMN_ID));
		tag.setName(resultSet.getString(ColumnName.COLUMN_NAME));
		tag.setDeleted(resultSet.getBoolean(ColumnName.COLUMN_DELETED));
		return tag;
	}

}
