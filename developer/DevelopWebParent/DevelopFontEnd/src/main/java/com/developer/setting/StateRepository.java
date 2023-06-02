package com.developer.setting;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.developer.common.entity.Country;
import com.developer.common.entity.State;

public interface StateRepository extends CrudRepository<State, Integer> {

	public List<State> findByCountryOrderByNameAsc(Country country);
}
