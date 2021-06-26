package com.epam.esm.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;

@Repository
public class OrderDaoImpl implements OrderDao {

	private static final String SELECT_ORDER_BY_ID_SQL = "FROM Order WHERE deleted = FALSE AND id = :id";// TODO
	private static final String SELECT_ORDERS_BY_USER_ID_SQL = "FROM Order WHERE deleted = false AND user.id = :userId";
	private static final String SELECT_TOTAL_NUMBER_USERS_ORDERS_SQL = "SELECT COUNT(*) FROM Order "
			+ "WHERE deleted = false AND user.id = :userId";
	private static final String ID_PARAMETER = "id"; // TODO util
	private static final String USER_ID_PARAMETER = "userId";

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Order create(Order order) {
		entityManager.persist(order);
		System.out.println("?????????????????????????" + order.toString());// TODO
		return order;
	}

	@Override
	public Optional<Order> findById(long id) {
		return entityManager.createQuery(SELECT_ORDER_BY_ID_SQL, Order.class).setParameter(ID_PARAMETER, id)
				.getResultStream().findFirst();
	}

	@Override
	public List<Order> findByUserId(long userId, Pagination pagination) {
		return entityManager.createQuery(SELECT_ORDERS_BY_USER_ID_SQL, Order.class)
				.setParameter(USER_ID_PARAMETER, userId).setFirstResult(pagination.getOffset())
				.setMaxResults(pagination.getLimit()).getResultList();
	}

	@Override
	public long getTotalNumberByUserId(long userId) {
		return (Long) entityManager.createQuery(SELECT_TOTAL_NUMBER_USERS_ORDERS_SQL)
				.setParameter(USER_ID_PARAMETER, userId).getSingleResult();
	}
}
