package com.developer.customer.activity;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.developer.common.entity.Customer;
import com.developer.common.entity.customer.CustomerActivity;

public interface CustomerActivityRepository extends CrudRepository<CustomerActivity, Integer> {

	@Query("SELECT ca FROM CustomerActivity ca WHERE ca.customer = :customer ORDER BY ca.timeAtivity DESC")
	List<CustomerActivity> findActivityByCustomerOrderByTimeDesc(@Param("customer") Customer customer);

	@Query("SELECT ca FROM CustomerActivity ca WHERE ca.timeAtivity < :cutoffDate")
	List<CustomerActivity> findOlderThan(@Param("cutoffDate") Date cutoffDate);
}
