package com.developer.customer.activity;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.developer.common.entity.Address;
import com.developer.common.entity.Customer;
import com.developer.common.entity.customer.CustomerActivity;
import com.developer.customer.CustomerRepository;

@Service
@Transactional
public class CustomerActivityService {

	@Autowired private CustomerActivityRepository repo;
	@Autowired private CustomerRepository customerRepo;
	
	public void save(String activity, Customer customer, String url) {
		CustomerActivity customerActivity = new CustomerActivity();
		customerActivity.setAtivity(activity);
		customerActivity.setTimeAtivity(new Date());
		customerActivity.setCustomer(customer);
		customerActivity.setUrlLog(url);
		repo.save(customerActivity);
	}
	
	public void saveById(String activity, Integer customerId , String url) {
		Customer customer = customerRepo.findById(customerId).get();
		CustomerActivity customerActivity = new CustomerActivity();
		customerActivity.setAtivity(activity);
		customerActivity.setTimeAtivity(new Date());
		customerActivity.setCustomer(customer);
		customerActivity.setUrlLog(url);
		repo.save(customerActivity);
	}
	
	public List<CustomerActivity> listActivityByCustomer(Customer customer){
		return repo.findActivityByCustomerOrderByTimeDesc(customer);
	}
	
}
