package com.developer.customer.activity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.developer.Utility;
import com.developer.common.entity.Customer;
import com.developer.common.entity.customer.CustomerActivity;
import com.developer.customer.CustomerService;

@Controller
public class CustomerActivityController {
	
	@Autowired private CustomerActivityService service;
	@Autowired private CustomerService customerService;
	
	@GetMapping("/customeractivity")
	public String showRegisterForm(Model model, HttpServletRequest request) {
		Customer customer = getAuthenticatedCustomer(request);
		
	    List<CustomerActivity> listCustomerActivitys = service.listActivityByCustomer(customer);

	    List<CustomerActivity> todayActivities = new ArrayList<>();
	    List<CustomerActivity> yesterdayActivities = new ArrayList<>();
	    List<CustomerActivity> weekActivities = new ArrayList<>();

	    for (CustomerActivity activity : listCustomerActivitys) {
	    	LocalDate localDate = activity.getTimeAtivity().toInstant()
	    			.atZone(ZoneId.systemDefault())
	                .toLocalDate();
	        long diffInDays = ChronoUnit.DAYS.between(localDate, LocalDate.now());

	        if (diffInDays == 0) {
	            todayActivities.add(activity);
	        } else if (diffInDays == 1) {
	            yesterdayActivities.add(activity);
	        } else if (diffInDays > 1 && diffInDays <= 7) {
	            weekActivities.add(activity);
	        }
	    }
	    
	    model.addAttribute("todayActivities", todayActivities);
	    model.addAttribute("yesterdayActivities", yesterdayActivities);
	    model.addAttribute("weekActivities", weekActivities);
		model.addAttribute("pageTitle", "Lịch sử hoạt động");
		
		return "customer/customer_activity";
	}
	
	private Customer getAuthenticatedCustomer(HttpServletRequest request) {
 		String email = Utility.getEmailOfAuthenticatedCustomer(request);
 		return customerService.getCustomerByEmail(email);
 	}
}
