package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;

public interface OrderDao {

	Order create(Order order);
	
	Optional <Order> findById(long id);
	
	List <Order> findByUserId(long userId, Pagination pagination);
	
	long getTotalNumberByUserId(long userId);
}
