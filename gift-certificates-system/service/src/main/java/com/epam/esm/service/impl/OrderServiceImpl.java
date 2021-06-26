package com.epam.esm.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.OrderedGiftCertificateDao;
import com.epam.esm.dto.OrderedGiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderedGiftCertificate;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderDao orderDao;
	private final OrderedGiftCertificateDao orderGiftCertificateDao;
	private final ModelMapper modelMapper;
	private final UserService userService;
	private final GiftCertificateService giftCertificateService;

	@Autowired
	public OrderServiceImpl(OrderDao orderDao, OrderedGiftCertificateDao orderGiftCertificateDao, ModelMapper modelMapper,
			UserService userService, GiftCertificateService giftCertificateService) {
		this.orderDao = orderDao;
		this.orderGiftCertificateDao = orderGiftCertificateDao;
		this.modelMapper = modelMapper;
		this.userService = userService;
		this.giftCertificateService = giftCertificateService;
	}

	@Transactional
	@Override
	public OrderDto addOrder(OrderDto orderDto) throws IncorrectParameterValueException, ResourceNotFoundException {
		// TODO validate проверить чтобы не было повторений id orderDetails(вывести id в
		// Set и сравнить размер set с list orderDetails)
		System.out.println(orderDto.toString());//TODO
		userService.findUserById(orderDto.getUserId());
		orderDto.getOrderedGiftCertificates().forEach(giftCertificate -> giftCertificate.setGiftCertificate(
				giftCertificateService.findGiftCertificateById(giftCertificate.getGiftCertificate().getId())));
		List<OrderedGiftCertificate> orderedGiftCertificates = orderDto.getOrderedGiftCertificates().stream()
				.map(orderedGiftCertificateDto -> modelMapper.map(orderedGiftCertificateDto, OrderedGiftCertificate.class))
				.collect(Collectors.toList());
		Order order = modelMapper.map(orderDto, Order.class);
		BigDecimal orderCost = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
		for (OrderedGiftCertificate giftCertificate : orderedGiftCertificates) {
			giftCertificate.setName(giftCertificate.getGiftCertificate().getName());
			giftCertificate.setDescription(giftCertificate.getGiftCertificate().getDescription());
			giftCertificate.setPrice(giftCertificate.getGiftCertificate().getPrice());
			giftCertificate.setDuration(giftCertificate.getGiftCertificate().getDuration());
			giftCertificate.setCreateDate(giftCertificate.getGiftCertificate().getCreateDate());
			giftCertificate.setLastUpdateDate(giftCertificate.getGiftCertificate().getLastUpdateDate());
			BigDecimal giftCertificatesCost = giftCertificate.getGiftCertificate().getPrice()
					.multiply(BigDecimal.valueOf(giftCertificate.getNumber()));
			orderCost = orderCost.add(giftCertificatesCost);
		}
		order.setCost(orderCost);
		order.setCreateDate(LocalDateTime.now());
		Order addedOrder = orderDao.create(order);
		orderedGiftCertificates.forEach(giftCertificate -> giftCertificate.setOrder(addedOrder));
		orderGiftCertificateDao.create(orderedGiftCertificates);// TODO вернуть созданный

		List<OrderedGiftCertificateDto> orderDetailsDto = orderedGiftCertificates.stream()
				.map(giftCertificate -> modelMapper.map(giftCertificate, OrderedGiftCertificateDto.class)).collect(Collectors.toList());
		OrderDto createdOrderDto = modelMapper.map(addedOrder, OrderDto.class);
		createdOrderDto.setOrderedGiftCertificates(orderDetailsDto);
		return createdOrderDto;
	}

	@Override
	public OrderDto findOrderById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageDto<OrderDto> findOrdersByUserId(long userId, PaginationDto pagination) {
		// TODO Auto-generated method stub
		return null;
	}

}
