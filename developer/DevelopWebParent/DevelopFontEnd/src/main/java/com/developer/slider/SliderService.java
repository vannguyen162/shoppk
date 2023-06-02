package com.developer.slider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.developer.common.entity.Slider;

@Service
public class SliderService {
	@Autowired
	private SliderRepository repo;
	
	public List<Slider> listSlider() {
		return repo.listSliders();
	}
}
