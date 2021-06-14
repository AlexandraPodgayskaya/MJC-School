package com.epam.esm.entity;

public class GiftCertificateSearchParameters {
	private String tagName;
	private String partNameOrDescription;
	private SortType sortType;
	private OrderType orderType;

	public GiftCertificateSearchParameters() {
	}

	public GiftCertificateSearchParameters(String tagName, String partNameOrDescription) {
		super();
		this.tagName = tagName;
		this.partNameOrDescription = partNameOrDescription;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getPartNameOrDescription() {
		return partNameOrDescription;
	}

	public void setPartNameOrDescription(String partNameOrDescription) {
		this.partNameOrDescription = partNameOrDescription;
	}

	public SortType getSortType() {
		return sortType;
	}

	public void setSortType(SortType sortType) {
		this.sortType = sortType;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderType == null) ? 0 : orderType.hashCode());
		result = prime * result + ((partNameOrDescription == null) ? 0 : partNameOrDescription.hashCode());
		result = prime * result + ((sortType == null) ? 0 : sortType.hashCode());
		result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
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
		GiftCertificateSearchParameters other = (GiftCertificateSearchParameters) obj;
		if (orderType != other.orderType)
			return false;
		if (partNameOrDescription == null) {
			if (other.partNameOrDescription != null)
				return false;
		} else if (!partNameOrDescription.equals(other.partNameOrDescription))
			return false;
		if (sortType != other.sortType)
			return false;
		if (tagName == null) {
			if (other.tagName != null)
				return false;
		} else if (!tagName.equals(other.tagName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GiftCertificateSearchParameters [tagName=" + tagName + ", partNameOrDescription="
				+ partNameOrDescription + ", sortType=" + sortType + ", orderType=" + orderType + "]";
	}

	public enum SortType {
		NAME, CREATE_DATE
	}

	public enum OrderType {
		ASC, DESC
	}
}
