package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;

public interface OrderService {

	OrderDto addOrder(OrderDto orderDto);

	OrderDto findOrderById(long id);

	PageDto<OrderDto> findOrdersByUserId(long userId, PaginationDto pagination);

}
