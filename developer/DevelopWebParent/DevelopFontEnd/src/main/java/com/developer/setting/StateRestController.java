package com.developer.setting;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.developer.common.entity.Country;
import com.developer.common.entity.State;
import com.developer.common.entity.StateDTO;

@RestController
public class StateRestController {
	
	@Autowired private StateRepository repo;
	
	@GetMapping("/settings/list_states_by-country/{id}")
	public List<StateDTO> listByCountry(@PathVariable("id") Integer countryId){
		List<State> listStates = repo.findByCountryOrderByNameAsc(new Country(countryId));
		List<StateDTO> result = new ArrayList<>();
		
		for(State state : listStates) {
			result.add(new StateDTO(state.getId(), state.getName()));
		}
		
		return result;
	}
}
