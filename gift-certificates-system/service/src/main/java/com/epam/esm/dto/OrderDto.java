package com.epam.esm.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OrderDto extends RepresentationModel<OrderDto> {

	// TODO validation попробовать hibernate и написать deserializer если что и
	// сделать JsonIgnore на поля, которые нужны только для отбражения клиенту
	private Long id;
	private BigDecimal cost;
	private UserDto user;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime createDate;
	List<OrderedGiftCertificateDto> orderedGiftCertificates;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public List<OrderedGiftCertificateDto> getOrderedGiftCertificates() {
		return orderedGiftCertificates == null ? null : Collections.unmodifiableList(orderedGiftCertificates);
	}

	public void setOrderedGiftCertificates(List<OrderedGiftCertificateDto> orderedGiftCertificates) {
		this.orderedGiftCertificates = orderedGiftCertificates;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((orderedGiftCertificates == null) ? 0 : orderedGiftCertificates.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		OrderDto other = (OrderDto) obj;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (orderedGiftCertificates == null) {
			if (other.orderedGiftCertificates != null)
				return false;
		} else if (!orderedGiftCertificates.equals(other.orderedGiftCertificates))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderDto [id=" + id + ", cost=" + cost + ", user=" + user + ", createDate=" + createDate
				+ ", orderedGiftCertificates=" + orderedGiftCertificates + "]";
	}

}
