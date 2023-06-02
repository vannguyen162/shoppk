package com.developer.shipping;

import org.springframework.data.repository.CrudRepository;

import com.developer.common.entity.Country;
import com.developer.common.entity.ShippingRate;

public interface ShippingRateRepository extends CrudRepository<ShippingRate, Integer>{

	public ShippingRate findByCountryAndState(Country country, String state);
}
