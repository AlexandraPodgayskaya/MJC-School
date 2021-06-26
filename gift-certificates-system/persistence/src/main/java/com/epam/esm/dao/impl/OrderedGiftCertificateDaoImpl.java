package com.epam.esm.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.epam.esm.dao.OrderedGiftCertificateDao;
import com.epam.esm.entity.OrderedGiftCertificate;

@Repository
public class OrderedGiftCertificateDaoImpl implements OrderedGiftCertificateDao {

	private static final String INSERT_CONNECTION_SQL = "INSERT INTO ordered_gift_certificate "
			+ "(order_id, gift_certificate_id, name, description, price, duration, create_date, "
			+ "last_update_date, number) VALUES (:orderId, :giftCertificateId, :name, :description, :price, "
			+ ":duration, :createDate, :lastUpdateDate, :number)";
	private static final String ID = "orderId";
	private static final String GIFT_CERTIFICATE_ID = "giftCertificateId";
	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";
	private static final String PRICE = "price";
	private static final String DURATION = "duration";
	private static final String CREATE_DATE = "createDate";
	private static final String LAST_UPDATE_DATE = "lastUpdateDate";
	private static final String NUMBER = "number";

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void create(List<OrderedGiftCertificate> orderedGiftCertificates) {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + orderedGiftCertificates.toString());// TODO
		orderedGiftCertificates.forEach(order -> entityManager.createNativeQuery(INSERT_CONNECTION_SQL)
				.setParameter(ID, order.getOrder().getId())
				.setParameter(GIFT_CERTIFICATE_ID, order.getGiftCertificate().getId())
				.setParameter(NAME, order.getName())
				.setParameter(DESCRIPTION, order.getDescription())
				.setParameter(PRICE, order.getPrice())
				.setParameter(DURATION, order.getDuration())
				.setParameter(CREATE_DATE, order.getCreateDate())
				.setParameter(LAST_UPDATE_DATE, order.getLastUpdateDate())
				.setParameter(NUMBER, order.getNumber()).executeUpdate());
	}
}
