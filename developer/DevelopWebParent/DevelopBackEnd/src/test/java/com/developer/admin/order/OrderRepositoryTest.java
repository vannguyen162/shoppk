package com.developer.admin.order;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.developer.common.entity.Customer;
import com.developer.common.entity.Product;
import com.developer.common.entity.order.Order;
import com.developer.common.entity.order.OrderDetail;
import com.developer.common.entity.order.OrderStatus;
import com.developer.common.entity.order.OrderTrack;
import com.developer.common.entity.order.PaymentMethod;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class OrderRepositoryTest {

	@Autowired private OrderRepository repo;
	@Autowired private TestEntityManager entityManager;
	
	@Test
	public void testAddNewOrder() {
		Customer customer = entityManager.find(Customer.class, 8);
		Product product = entityManager.find(Product.class, 52);
		
		Order order = new Order();
		order.setOrderTime(new Date());
		order.setCustomer(customer);
		order.setFirstName(customer.getFirstName());
		order.setLastName(customer.getLastName());
		order.setPhoneNumber(customer.getPhoneNumber());
		order.setAddressLine1(customer.getAddressLine1());
		order.setAddressLine2(customer.getAddressLine2());
		order.setCity(customer.getCity());
		order.setCountry(customer.getCountry().getName());
		order.setPostalCode(customer.getPostalCode());
		order.setState(customer.getState());
		
		order.setShippingCost(10000);
		order.setProductCost(product.getDiscountPriceSale());
		order.setTax(0);
		order.setSubtotal(product.getDiscountPriceSale());
		order.setTotal(product.getPrice() + 10000);
		
		order.setPaymentMethod(PaymentMethod.CREDIT_CARD);
		order.setStatus(OrderStatus.NEW);
		order.setDeliverDate(new Date());
		order.setDeliverDays(1);
		
		OrderDetail detail = new OrderDetail();
		detail.setProduct(product);
		detail.setOrder(order);
		detail.setProductCost(product.getCost());
		detail.setShippingCost(10);
		detail.setQuantity(3);
		detail.setSubtotal(product.getDiscountPriceSale());
		detail.setUnitPrice(product.getDiscountPriceSale());
		
		order.getOrderDetails().add(detail);
		Order saveOrder = repo.save(order);
		assertThat(saveOrder.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testUpdateOrderTracks() {
		Integer orderId = 10;
		Order order = repo.findById(orderId).get();
		
		OrderTrack newTrack = new OrderTrack();
		newTrack.setOrder(order);
		newTrack.setUpdatedTime(new Date());
		newTrack.setStatus(OrderStatus.PROCESSING);
		newTrack.setNotes(OrderStatus.PROCESSING.defaultDescription());
		
		OrderTrack newTrack1 = new OrderTrack();
		newTrack1.setOrder(order);
		newTrack1.setUpdatedTime(new Date());
		newTrack1.setStatus(OrderStatus.DELIVERD);
		newTrack1.setNotes(OrderStatus.DELIVERD.defaultDescription());
		
		List<OrderTrack> orderTracks = order.getOrderTracks();
		orderTracks.add(newTrack);
		orderTracks.add(newTrack1);
		
		Order updateOrder = repo.save(order);
		
		assertThat(updateOrder.getOrderTracks()).hasSizeGreaterThan(1);
	}

	
}
