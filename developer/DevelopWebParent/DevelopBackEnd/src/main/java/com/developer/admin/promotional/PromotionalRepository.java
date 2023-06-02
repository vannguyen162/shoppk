package com.developer.admin.promotional;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.developer.admin.paging.SearchRepository;
import com.developer.common.entity.Board;
import com.developer.common.entity.Product;

public interface PromotionalRepository extends SearchRepository<Board, Integer>{
	
	@Query("SELECT b FROM Board b WHERE CONCAT(b.title, ' ', b.boardCate, ' ') LIKE %?1%")
	public Page<Board> findAll(String keyword, Pageable pageable);
	
	public Long countById(Integer id);
	
	@Query("UPDATE Board b SET b.enabled = ?2 WHERE b.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);
	
	@Query("SELECT b FROM Board b WHERE b.kindContent = ?1 ORDER BY b.boardNo DESC")
	List<Board> findByKindOrderByKindContentDesc(String kind);
}
	