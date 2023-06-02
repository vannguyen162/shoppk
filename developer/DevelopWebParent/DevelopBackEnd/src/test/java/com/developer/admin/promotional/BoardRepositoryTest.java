package com.developer.admin.promotional;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.developer.common.entity.Board;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BoardRepositoryTest {
	
	@Autowired
	private PromotionalRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewBoard() {
		Board board = new Board("00000", "NEW", "Hi 23", "this contents");
		Board save = repo.save(board);
		assertThat(save.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testEnabledBoard() {
		Integer id = 5;
		repo.updateEnabledStatus(id, true);
	}
}
