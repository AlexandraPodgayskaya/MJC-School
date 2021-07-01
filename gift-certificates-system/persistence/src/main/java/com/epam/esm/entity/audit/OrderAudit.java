package com.epam.esm.entity.audit;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;

import com.epam.esm.entity.Order;

public class OrderAudit {
	@PrePersist
	public void beforeCreateOrder(Order order) {
		order.setCreateDate(LocalDateTime.now());
	}

}
