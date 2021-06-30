package com.epam.esm.dao.creator;

public enum SortType {

	NAME("name"), CREATE_DATE("createDate");

	private final String sortFieldName;

	private SortType(String sortFieldName) {
		this.sortFieldName = sortFieldName;
	}

	public String getSortFieldName() {
		return sortFieldName;
	}

}
