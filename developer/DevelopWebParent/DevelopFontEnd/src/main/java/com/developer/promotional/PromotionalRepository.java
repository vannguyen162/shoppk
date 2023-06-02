package com.developer.promotional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.developer.common.entity.Board;

public interface PromotionalRepository extends PagingAndSortingRepository<Board, Integer>{
	
//	@Query("SELECT b FROM Board b WHERE b.enabled = true")
	@Query("SELECT b FROM Board b WHERE b.enabled = true AND b.startDate <= CURRENT_DATE  AND b.endDate >= CURRENT_DATE")
	public Page<Board> listBoard(Pageable pageable);
	
}
