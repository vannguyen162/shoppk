package com.developer.admin.customer;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.developer.admin.paging.PagingAndSortingHelper;
import com.developer.admin.security.DevelopUserDetails;
import com.developer.admin.setting.country.CountryRepository;
import com.developer.common.entity.Country;
import com.developer.common.entity.Customer;
import com.developer.common.entity.Product;
import com.developer.common.entity.User;
import com.developer.common.exception.CustomerNotFoundException;
import com.developer.common.model.customers.CustomerModel;

@Service
@Transactional
public class CustomerService {
	public static final int CUSTOMERS_PER_PAGE = 10;

	@Autowired private CustomerRepository customerRepo;
	@Autowired private CountryRepository countryRepo;
	@Autowired private PasswordEncoder passwordEncoder;
	
//	@Autowired private CustomerReplayRepository customerReplayRepo;
	
	
	public void listByPage(int pageNum, PagingAndSortingHelper helper){
		helper.listEntities(pageNum, CUSTOMERS_PER_PAGE, customerRepo);
		
	}
	
	public void updateCustomerEnabledStatus(Integer id, boolean enabled) {
		customerRepo.updateEnabledStatus(id, enabled);
	}
	
	public Customer get(Integer id) throws CustomerNotFoundException {
		try {
			return customerRepo.findById(id).get();
		}catch (NoSuchElementException ex) {
			throw new CustomerNotFoundException("Không thể tìm thấy người dùng với ID: " + id);
		}
	}
	
	public List<Country> listAllCountries(){
		return countryRepo.findAllByOrderByNameAsc();
	}
	
	public boolean isEmailUnique(Integer id, String email) {
		Customer exitCustomer = customerRepo.findByEmail(email);
		
		if(exitCustomer != null && exitCustomer.getId() != id) {
			return false;
		}
		return true;
	}
	
	public void save(Customer customerInForm) {
		Customer customerInDB = customerRepo.findById(customerInForm.getId()).get();
		
		if(!customerInForm.getPassword().isEmpty()) {
			String encodedPassword = passwordEncoder.encode(customerInForm.getPassword());
			customerInForm.setPassword(encodedPassword);
		}else {
			customerInForm.setPassword(customerInDB.getPassword());
		}
		
		customerInForm.setEnabled(customerInDB.isEnabled());
		customerInForm.setCreatedTime(customerInDB.getCreatedTime());
		customerInForm.setVerificationCode(customerInDB.getVerificationCode());
		customerInForm.setAuthenticationType(customerInDB.getAuthenticationType());
		customerInForm.setResetPasswordToken(customerInDB.getResetPasswordToken());
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DevelopUserDetails loggedUser = (DevelopUserDetails)auth.getPrincipal();
		customerInForm.setWorkUser(loggedUser.getFullname());
		
		customerInForm.setUpdatedTime(new Date());
		
		customerRepo.save(customerInForm);
	}
	
	public void delete(Integer id) throws CustomerNotFoundException{
		Long count = customerRepo.countById(id);
		if(count == null || count == 0) {
			throw new CustomerNotFoundException("Không thể tìm thấy người dùng với ID: " + id);
		}
		customerRepo.deleteById(id);
	}
	
//    public Page<Customer> findAll(Pageable pageable){
//        return customerRepo.findAll(pageable);
//    }
//    
//    public List<CustomerModel> listAllCusRepLay(){
//		return customerReplayRepo.getAllCustomer();
//    	
//    }
	
}

