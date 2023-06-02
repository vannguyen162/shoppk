package com.developer.order;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.developer.Utility;
import com.developer.common.entity.Customer;
import com.developer.common.entity.order.Order;
import com.developer.customer.CustomerService;
import com.developer.product.ProductService;

@Controller
public class OrderController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired private OrderService orderService;
	
	@GetMapping("/order/page/{pageNum}")
	public String viewDashboard(Model model, HttpServletRequest request, @PathVariable(name = "pageNum") int pageNum) {
		
		Customer customer = getAuthenticatedCustomer(request);
		Page<Order> page = orderService.listForCustomerByPage(customer, pageNum);
		List<Order> listOrders = page.getContent();
		
		long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
		long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("pageTitle", "Tất cả sản phẩm");
		
		
		model.addAttribute("listOrders", listOrders);
		
		model.addAttribute("pageTitle", "Đơn hàng của tôi");
		model.addAttribute("customer", customer);
		
		model.addAttribute("endCount", endCount);
		
		return "order/order";
	}
	
	private Customer getAuthenticatedCustomer(HttpServletRequest request) {
		String email = Utility.getEmailOfAuthenticatedCustomer(request);
		return customerService.getCustomerByEmail(email);
	}
	
	@GetMapping("/order/detail/{id}")
	public String viewOrderDetails(Model model,
			@PathVariable(name = "id") Integer id, HttpServletRequest request) {
		Customer customer = getAuthenticatedCustomer(request);
		Order order = orderService.getOrder(id, customer);
		
		model.addAttribute("order", order);
		
		return "order/order_details_modal";
	}	
}
