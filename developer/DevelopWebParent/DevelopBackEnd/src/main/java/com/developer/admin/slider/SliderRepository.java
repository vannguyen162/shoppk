package com.developer.admin.slider;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.developer.admin.paging.SearchRepository;
import com.developer.common.entity.Slider;

public interface SliderRepository extends SearchRepository<Slider, Integer> {

	@Query("SELECT s FROM Slider s WHERE s.startDate LIKE %?1%")
	public Page<Slider> findAll(String keyword, Pageable pageable);

	@Query("UPDATE Slider s SET s.enabled = ?2 WHERE s.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);

	public Long countById(Integer id);
}
