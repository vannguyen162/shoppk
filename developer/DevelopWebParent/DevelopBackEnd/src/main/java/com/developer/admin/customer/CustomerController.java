package com.developer.admin.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.developer.admin.paging.PagingAndSortingHelper;
import com.developer.admin.paging.PagingAndSortingParam;
import com.developer.common.entity.Country;
import com.developer.common.entity.Customer;
import com.developer.common.exception.CustomerNotFoundException;
import com.developer.common.model.customers.CustomerModel;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService service;
	
	@GetMapping("/customers")
	public String listFirstPage(Model model) {
		return "redirect:/customers/page/1?sortField=firstName&sortDir=asc";
	}
	
	@GetMapping("/customers/page/{pageNum}")
	public String listByPage(
			@PagingAndSortingParam(listName = "listCustomers", moduleURL = "/customers") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum,Model model
			) {
		service.listByPage(pageNum, helper);
		
		return "customers/customers";
	}
	
	@GetMapping("/customers/{id}/enabled/{status}")
	public String updateCustomerEnabledStatus(@PathVariable("id") Integer id,
			@PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
		service.updateCustomerEnabledStatus(id, enabled);
		String status = enabled ? ": hiện" : ": ẩn";
		String message = "Người dùng (ID: "+ id +") đã được cập nhật trạng thái " + status;
		redirectAttributes.addFlashAttribute("message", message);
		
		return "redirect:/customers";
	}
	
	@GetMapping("/customers/detail/{id}")
	public String viewCustomer(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes ra) {
		try {
			Customer customer = service.get(id);
			model.addAttribute("customer", customer);
			
			return "customers/customer_detail_modal";
			
		} catch (CustomerNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			
			return "redirect:/customers";
		}
	}
	
	@GetMapping("/customers/edit/{id}")
	public String editCustomer(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes ra) {
		try {
			Customer customer = service.get(id);
			List<Country> countries = service.listAllCountries();
			
			model.addAttribute("listCountries", countries);
			model.addAttribute("customer", customer);
			model.addAttribute("pageTitle", "Chỉnh sửa thông tin người dùng (ID: " + id + ")");
			
			return "customers/customer_form";
			
		} catch (CustomerNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());

			return "redirect:/customers";
		}
	}
	
	@PostMapping("/customers/save")
	public String saveCustomer(Customer customer, Model model, RedirectAttributes ra) {
		service.save(customer);
		ra.addFlashAttribute("message", "Người dùng (ID: " + customer.getId() + ") vừa được cập nhật");
		return "redirect:/customers";
	}
	
	@GetMapping("/customers/delete/{id}")
	public String deleteCustomer(@PathVariable Integer id, RedirectAttributes ra) {
		try {
			service.delete(id);
			ra.addFlashAttribute("message", "Đã xóa người dùng (ID: " + id + ")");
		} catch (CustomerNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			
		}
		return "redirect:/customers";
	}
}
