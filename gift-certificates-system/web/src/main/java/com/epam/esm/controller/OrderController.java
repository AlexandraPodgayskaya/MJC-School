package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.converter.ParametersToDtoConverter;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private static final String USER = "user";
	private static final String GIFT_CERTIFICATE = "gift-certificate";
	private final OrderService orderService;
	private final ParametersToDtoConverter parametersToDtoConverter;

	@Autowired
	public OrderController(OrderService orderService, ParametersToDtoConverter parametersToDtoConverter) {
		this.orderService = orderService;
		this.parametersToDtoConverter = parametersToDtoConverter;
	}

	@GetMapping("/{id}")
	public OrderDto getOrderById(@PathVariable long id) {
		OrderDto foundOrderDto = orderService.findOrderById(id);
		addLinks(foundOrderDto);
		return foundOrderDto;
	}

	@GetMapping("/users/{userId}")
	public PageDto<OrderDto> getOrdersByUserId(@PathVariable long userId,
			@RequestParam Map<String, String> parameters) {
		PaginationDto pagination = parametersToDtoConverter.getPaginationDto(parameters);
		PageDto<OrderDto> page = orderService.findOrdersByUserId(userId, pagination);
		page.getPagePositions().forEach(this::addLinks);
		return page;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrderDto addOrder(@RequestBody OrderDto orderDto) {
		OrderDto addedOrderDto = orderService.addOrder(orderDto);
		addLinks(addedOrderDto);
		return addedOrderDto;
	}

	private void addLinks(OrderDto orderDto) {
		// TODO выделить в отдельный класс? Static?
		orderDto.add(linkTo(methodOn(OrderController.class).getOrderById(orderDto.getId())).withSelfRel());
		orderDto.getUser()
				.add(linkTo(methodOn(UserController.class).getUserById(orderDto.getUser().getId())).withRel(USER));
		orderDto.getOrderedGiftCertificates().forEach(
				orderedGiftCertificate -> orderedGiftCertificate.add(linkTo(methodOn(GiftCertificateController.class)
						.getGiftCertificateById(orderedGiftCertificate.getGiftCertificate().getId()))
								.withRel(GIFT_CERTIFICATE)));

	}

}
