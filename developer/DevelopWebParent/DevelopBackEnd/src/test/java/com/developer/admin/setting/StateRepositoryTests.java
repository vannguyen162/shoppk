package com.developer.admin.setting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.developer.admin.setting.state.StateRepository;
import com.developer.common.entity.Country;
import com.developer.common.entity.State;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class StateRepositoryTests {
	
	@Autowired private StateRepository repo;
	@Autowired private TestEntityManager entityManager;
	
	@Test
	public void testCreateStateInIndian() {
		Integer countryId = 1;
		Country country = entityManager.find(Country.class, countryId);
		
//		State state = repo.save(new State("Karnataka", country));
//		State state = repo.save(new State("Punjab", country));
//		State state = repo.save(new State("Ult", country));
		State state = repo.save(new State("Hà Nội", country));
		
		assertThat(state).isNotNull();
		assertThat(state.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateStatesInUs() {
		Integer countryId = 2;
		Country country = entityManager.find(Country.class, countryId);
		
//		State state = repo.save(new State("California", country));
//		State state = repo.save(new State("Texas", country));
//		State state = repo.save(new State("New York", country));
		State state = repo.save(new State("Washington", country));
		
		assertThat(state).isNotNull();
		assertThat(state.getId()).isGreaterThan(0);
	}
	@Test
	public void testListStateByCount() {
		Integer countryId = 2;
		Country country = entityManager.find(Country.class, countryId);
		List<State> listStates = repo.findByCountryOrderByNameAsc(country);
		
		listStates.forEach(System.out::println);
		
		assertThat(listStates.size()).isGreaterThan(0);
	}
	
	@Test
	public void testUpdateState() {
		Integer countryId = 3;
		String stateName = "Tamil Nadu";
		State state = repo.findById(countryId).get();
		
		state.setName(stateName);
		State updateState = repo.save(state);
		
		assertThat(updateState.getName()).isEqualTo(stateName);
		
	}
	@Test
	public void testGetState() {
		Integer stateId = 8;
		Optional<State> findById = repo.findById(stateId);
		assertThat(findById.isPresent());
		
	}
	
	@Test
	public void testDeleteState() {
		Integer stateId = 8;
		repo.deleteById(stateId);
		
		Optional<State> findById = repo.findById(stateId);
		assertThat(findById.isEmpty());
	}
	
	
}
