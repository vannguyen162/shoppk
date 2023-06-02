package com.developer.admin.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.developer.admin.paging.SearchRepository;
import com.developer.common.entity.Notice;

public interface NoticeRepository extends SearchRepository<Notice, Integer> {
	
	@Query("SELECT n FROM Notice n WHERE n.shortDescription LIKE %?1%")
	public Page<Notice> findAll(String keyword, Pageable pageable);
	
	@Query("UPDATE Notice n SET n.enabled = ?2 WHERE n.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);
	
	public Long countById(Integer id);
}
