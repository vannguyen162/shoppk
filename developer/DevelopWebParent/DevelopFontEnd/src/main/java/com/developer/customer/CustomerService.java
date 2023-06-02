package com.developer.customer;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.developer.common.entity.AuthenticationType;
import com.developer.common.entity.Country;
import com.developer.common.entity.Customer;
import com.developer.common.exception.CustomerNotFoundException;
import com.developer.configure.BaseURL;
import com.developer.customer.activity.CustomerActivityService;
import com.developer.setting.CountryRepository;

import net.bytebuddy.utility.RandomString;

@Service
@Transactional
public class CustomerService {

	@Autowired private CountryRepository countryRepo;
	@Autowired private CustomerRepository customerRepo;
	@Autowired PasswordEncoder passwordEncoder;
	
	// 03/04/2023 action by customer.
	@Autowired private CustomerActivityService customerActivityService;
	
	public List<Country> listAllCountries(){
		return countryRepo.findAllByOrderByNameAsc();
	}
	
	public boolean isEmailUnique(String email) {
		Customer customer = customerRepo.findByEmail(email);
		return customer == null;
	}
	
	public void registerCustomer(Customer customer) {
		encodePassword(customer);
		customer.setEnabled(false);
		customer.setCreatedTime(new Date());
		customer.setAuthenticationType(AuthenticationType.DATABASE);
		
		String randomCode = RandomString.make(64);
		customer.setVerificationCode(randomCode);
		
		customerRepo.save(customer);
		
	}
	
	public Customer getCustomerByEmail(String email) {
		return customerRepo.findByEmail(email);
	}

	private void encodePassword(Customer customer) {
		String encodePassword = passwordEncoder.encode(customer.getPassword());
		customer.setPassword(encodePassword);
	}
	
	public boolean verify(String verificationCode) {
		Customer customer = customerRepo.findByVerificationCode(verificationCode);
		
		if(customer == null || customer.isEnabled()) {
			return false;
		}else {
			customerRepo.enabled(customer.getId());
			return true;
		}
	}
	
	public void updateAuthenticationType(Customer customer, AuthenticationType type) {
		if(!customer.getAuthenticationType().equals(type)) {
			customerRepo.updateAuthenticationType(customer.getId(), type);
		}
	}
	
	public void addNewCustomerUponOAuthLogin(String name, String email, String countryCode,
			AuthenticationType authenticationType) {
		Customer customer = new Customer();
		customer.setEmail(email);
		setName(name, customer);
		
		customer.setEnabled(true);
		customer.setCreatedTime(new Date());
		customer.setAuthenticationType(authenticationType);
		customer.setPassword("");
		customer.setAddressLine1("");
		customer.setCity("");
		customer.setState("");
		customer.setPhoneNumber("");
		customer.setPostalCode("");
		customer.setCountry(countryRepo.findByCode(countryCode));
		
		customerRepo.save(customer);
	}
	
	private void setName(String name, Customer customer) {
		String [] nameArray = name.split(" ");
		if(nameArray.length < 2) {
			customer.setFirstName(name);
			customer.setLastName("");
			
		}else {
			String fisrtName = nameArray[0];
			customer.setFirstName(fisrtName);
			
			String lastName = name.replaceFirst(fisrtName + " ", "");
			customer.setLastName(lastName);
		}
	}
	
	public void update(Customer customerInForm) {
		Customer customerInDB = customerRepo.findById(customerInForm.getId()).get();
		
		if(customerInDB.getAuthenticationType().equals(AuthenticationType.DATABASE)) {
			if(!customerInForm.getPassword().isEmpty()) {
				String encodedPassword = passwordEncoder.encode(customerInForm.getPassword());
				customerInForm.setPassword(encodedPassword);
			}else {
				customerInForm.setPassword(customerInDB.getPassword());
			}
		}else {
			customerInForm.setPassword(customerInDB.getPassword());
		}
		
		customerInForm.setEnabled(customerInDB.isEnabled());
		customerInForm.setCreatedTime(customerInDB.getCreatedTime());
		customerInForm.setVerificationCode(customerInDB.getVerificationCode());
		customerInForm.setAuthenticationType(customerInDB.getAuthenticationType());
		customerInForm.setResetPasswordToken(customerInDB.getResetPasswordToken());
		
		// 06/04/2023 action by customer.
		BaseURL.setUrl("account_details");
		String url = BaseURL.getUrl();
		String activity = "Đã cập nhật thông tin tài khoản";
		customerActivityService.save(activity, customerInDB, url);
		
		customerRepo.save(customerInForm);
	}

	public String updateResetPasswordToken(String email) throws CustomerNotFoundException {
		Customer customer = customerRepo.findByEmail(email);
		if(customer !=  null) {
			String token = RandomString.make(30);
			customer.setResetPasswordToken(token);
			customerRepo.save(customer);
			return token;
		}else {
			throw new CustomerNotFoundException("Email này chưa được chưa đăng kí!" + email);
		}
		
	}
	
	public Customer getByResetPasswordToken(String token) {
		return customerRepo.findByResetPasswordToken(token);
	}
	
	public void updatePassword(String token, String newPassword) throws CustomerNotFoundException {
		Customer customer = customerRepo.findByResetPasswordToken(token);
		if(customer == null) {
			throw new CustomerNotFoundException("Không tìm thấy người dùng: token không hợp lệ");
		}
		customer.setPassword(newPassword);
		customer.setResetPasswordToken(null);
		encodePassword(customer);
		customerRepo.save(customer);
		
		// 06/04/2023 action by customer.
		BaseURL.setUrl("account_details");
		String url = BaseURL.getUrl();
		String activity = "Đã cập thay đổi mật khẩu";
		customerActivityService.save(activity, customer, url);
	}
}
