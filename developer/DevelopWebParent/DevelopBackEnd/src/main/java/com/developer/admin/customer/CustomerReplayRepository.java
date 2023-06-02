package com.developer.admin.customer;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.developer.common.entity.Customer;
import com.developer.common.model.customers.CustomerModel;
public interface CustomerReplayRepository extends PagingAndSortingRepository<Customer, String>{
	
	@Query(value=
			 "  SELECT id as id                 "
			+"       , first_name as firtName   "
			+"       , last_name as lastName    "
			+"       , email as email           "
			+"       , phone_number as phone    "
			+"    FROM customers           "
			, nativeQuery = true)       
	public List<CustomerModel> getAllCustomer();
}
