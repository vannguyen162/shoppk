package com.developer.address;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.developer.Utility;
import com.developer.common.entity.Address;
import com.developer.common.entity.Country;
import com.developer.common.entity.Customer;
import com.developer.configure.BaseURL;
import com.developer.customer.CustomerService;
import com.developer.customer.activity.CustomerActivityService;

@Controller
public class AddressController {

	@Autowired private AddressService addressService;
	@Autowired private CustomerService customerService;
	
	@Autowired private CustomerActivityService customerActivityService;
	
	@GetMapping("/address_book")
	public String showAddressBook(Model model, HttpServletRequest request) {
		Customer customer = getAuthenticatedCustomer(request);
		List<Address> listAddresses = addressService.listAddressBook(customer);
		
		boolean usePrimaryAddressAsDefault = true;
		for(Address address : listAddresses) {
			if(address.isDefaultForShipping()) {
				usePrimaryAddressAsDefault = false;
				break;
			}
		}
		
		model.addAttribute("listAddresses", listAddresses);
		model.addAttribute("customer", customer);
		model.addAttribute("usePrimaryAddressAsDefault", usePrimaryAddressAsDefault);
		
		model.addAttribute("pageTitle", "Địa chỉ");
		return "address_book/addresses";
	}
	
	private Customer getAuthenticatedCustomer(HttpServletRequest request) {
 		String email = Utility.getEmailOfAuthenticatedCustomer(request);
 		return customerService.getCustomerByEmail(email);
 	}
	
	@GetMapping("/address_book/new")
	public String newAddress(Model model) {
		List<Country> listCountries = customerService.listAllCountries();
		
		model.addAttribute("listCountries", listCountries);
		model.addAttribute("address", new Address());
		
		model.addAttribute("pageTitle", "Thêm địa chỉ");
		
		return "address_book/address_form";
	}
	
	@PostMapping("/address_book/save")
	public String saveAddress(Address address, HttpServletRequest request, RedirectAttributes ra) {
		Customer customer = getAuthenticatedCustomer(request);
		
		address.setCustomer(customer);
		addressService.save(address);
		
		ra.addFlashAttribute("message", "Đã lưu địa chỉ!");
		
		// 06/04/2023 action by customer.
		BaseURL.setUrl("address_book/edit/" + address.getId());
		String url = BaseURL.getUrl();
		String activity = "Đã thêm địa chỉ nhận hàng";
		customerActivityService.save(activity, customer, url);
		
		return "redirect:/address_book";
	}
	
	@GetMapping("/address_book/edit/{id}")
	public String editAddress(@PathVariable("id") Integer addressId, Model model,
			HttpServletRequest request) {
		Customer customer = getAuthenticatedCustomer(request);
		List<Country> listCountries = customerService.listAllCountries();
		
		Address address = addressService.get(addressId, customer.getId());
		
		model.addAttribute("address", address);
		model.addAttribute("listCountries", listCountries);
		model.addAttribute("pageTitle", "Chỉnh sửa địa chỉ");
		
		return "address_book/address_form";
	}
	
	@GetMapping("/address_book/delete/{id}")
	public String deleteAddress(@PathVariable("id") Integer addressId, RedirectAttributes ra,
			HttpServletRequest request) {
		Customer customer = getAuthenticatedCustomer(request);
		addressService.delete(addressId, customer.getId());
		
		ra.addFlashAttribute("message", "Đã xóa địa chỉ!");
		return "redirect:/address_book";
	}
	
	@GetMapping("/address_book/default/{id}")
	public String setDefaultAddress(@PathVariable("id") Integer addressId, 
			HttpServletRequest request) {
		Customer customer = getAuthenticatedCustomer(request);
		addressService.setDefaultAddress(addressId, customer.getId());
		
		String redirectOption = request.getParameter("redirect");
		String redirectURL = "redirect:/address_book";
		
		if ("cart".equals(redirectOption)) {
			redirectURL = "redirect:/cart";
		}
		
		return redirectURL;
	}
}
