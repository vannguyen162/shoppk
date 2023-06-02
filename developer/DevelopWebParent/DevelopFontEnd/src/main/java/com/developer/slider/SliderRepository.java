package com.developer.slider;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.developer.common.entity.Slider;

public interface SliderRepository extends JpaRepository<Slider, Integer>{
	
	@Query("SELECT s FROM Slider s WHERE s.enabled = true AND s.startDate <= CURRENT_DATE  AND s.endDate >= CURRENT_DATE")
	public List<Slider> listSliders();

}
