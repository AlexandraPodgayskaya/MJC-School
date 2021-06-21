package com.epam.esm.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class consists query and parameters to insert into it
 *
 * @author Aleksandra Podgayskaya
 * @version 1.0
 */
public class GiftCertificateSearchQuery {
	String query;
	List<String> parameters;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<String> getParameters() {
		return parameters == null ? Collections.emptyList() : Collections.unmodifiableList(parameters);
	}

	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

	public void addParameter(String parameter) {
		if (parameters == null) {
			parameters = new ArrayList<>();
		}
		parameters.add(parameter);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
		result = prime * result + ((query == null) ? 0 : query.hashCode());
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
		GiftCertificateSearchQuery other = (GiftCertificateSearchQuery) obj;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		if (query == null) {
			if (other.query != null)
				return false;
		} else if (!query.equals(other.query))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GiftCertificateSearchQuery [query=" + query + ", parameters=" + parameters + "]";
	}
}
