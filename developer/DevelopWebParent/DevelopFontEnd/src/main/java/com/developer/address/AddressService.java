package com.developer.address;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.developer.common.entity.Address;
import com.developer.common.entity.Customer;
import com.developer.common.entity.Product;
import com.developer.configure.BaseURL;
import com.developer.customer.activity.CustomerActivityService;

@Service
@Transactional
public class AddressService {

	@Autowired private AddressRepository repo;
	
	@Autowired private CustomerActivityService customerActivityService;
	
	public List<Address> listAddressBook(Customer customer){
		return repo.findByCustomer(customer);
	}
	
	public void save(Address address) {
		repo.save(address);
	}
	
	public Address get(Integer addressId, Integer customerId) {
		return repo.findByIdAndCustomer(addressId, customerId);
	}
	
	public void delete(Integer addressId, Integer customerId) {
		repo.deleteByIdAndCustomer(addressId, customerId);
		
		// 06/04/2023 action by customer.
		BaseURL.setUrl("address_book");
		String url = BaseURL.getUrl();
		String activity = "Đã xóa địa chỉ nhận hàng";
		customerActivityService.saveById(activity, customerId, url);
	}
	
	public void setDefaultAddress(Integer defaultAddressId, Integer customerId) {
		if(defaultAddressId > 0) {
			repo.setDefaultAddress(defaultAddressId);
		}
		repo.setNonDefaultForOthers(defaultAddressId, customerId);
		
		// 06/04/2023 action by customer.
		BaseURL.setUrl("address_book/edit/" + defaultAddressId);
		String url = BaseURL.getUrl();
		String activity = "Thay đổi địa chỉ nhận hàng";
		customerActivityService.saveById(activity, customerId, url);
	}
	
	public Address getDefaultAddress(Customer customer) {
		return repo.findDefaultByCustomer(customer.getId());
	}
 
}
