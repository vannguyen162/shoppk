package com.developer.notice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.developer.common.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Integer>{
	
	@Query("SELECT n FROM Notice n WHERE n.enabled = true AND n.poppup = false AND n.startDate <= CURRENT_DATE  AND n.endDate >= CURRENT_DATE")
	public List<Notice> listNotices();
	
	@Query("SELECT n FROM Notice n WHERE n.enabled = true AND n.poppup = true AND n.startDate <= CURRENT_DATE  AND n.endDate >= CURRENT_DATE")
	public List<Notice> listNoticesPoppup();
}
