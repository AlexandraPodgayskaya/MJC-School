package com.epam.esm.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;

@Repository
public class OrderDaoImpl implements OrderDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Order create(Order order) {
		entityManager.persist(order);
		System.out.println("?????????????????????????" + order.toString());//TODO
		return order;
	}

}
