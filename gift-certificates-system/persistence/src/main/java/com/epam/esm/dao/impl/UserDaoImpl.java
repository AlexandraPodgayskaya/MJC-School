package com.epam.esm.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	private static final String SELECT_ALL_USERS_SQL = "SELECT u FROM User u WHERE u.deleted = false";
	private static final String SELECT_USER_BY_ID_SQL = "SELECT u FROM User u WHERE u.deleted = false "
			+ "AND u.id = :id";
	private static final String SELECT_TOTAL_NUMBER_USERS_SQL = "SELECT COUNT(*) FROM User u WHERE u.deleted = false";
	private static final String ID_PARAMETER = "id";

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<User> findAll(Pagination pagination) {
		return entityManager.createQuery(SELECT_ALL_USERS_SQL, User.class)
				.setFirstResult((pagination.getPageNumber() - 1) * pagination.getPageSize())
				.setMaxResults(pagination.getPageSize()).getResultList();// TODO ������ �������� � pagination
	}

	@Override
	public Optional<User> findById(long id) {
		return entityManager.createQuery(SELECT_USER_BY_ID_SQL, User.class).setParameter(ID_PARAMETER, id)
				.getResultStream().findFirst();
	}

	@Override
	public long getTotalNumber() {
		return (Long) entityManager.createQuery(SELECT_TOTAL_NUMBER_USERS_SQL).getSingleResult();
	}

}
