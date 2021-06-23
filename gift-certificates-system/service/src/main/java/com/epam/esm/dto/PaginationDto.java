package com.epam.esm.dto;

public class PaginationDto {

	private int pageNumber;
	private int pageSize;

	public PaginationDto(int pageNumber, int pageSize) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + pageNumber;
		result = prime * result + pageSize;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaginationDto other = (PaginationDto) obj;
		if (pageNumber != other.pageNumber)
			return false;
		if (pageSize != other.pageSize)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PaginationDto [pageNumber=" + pageNumber + ", pageSize=" + pageSize + "]";
	}

}
