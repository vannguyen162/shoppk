package com.developer.admin.slider;

import java.util.Date;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.developer.admin.paging.PagingAndSortingHelper;
import com.developer.admin.security.DevelopUserDetails;
import com.developer.common.entity.Slider;

@Service
@Transactional
public class SliderService {

	@Autowired
	private SliderRepository repo;
	public static final int SLIDER_PER_PAGE = 10;
	
	public void listByPage(int pageNum, PagingAndSortingHelper helper) {
		helper.listEntities(pageNum, SLIDER_PER_PAGE, repo);
	}

	public void updateSlideEnabledStatus(Integer id, boolean enabled) {
		repo.updateEnabledStatus(id, enabled);
	}

	public Slider get(Integer id) throws SliderNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new SliderNotFoundException("Không thể tìm thấy slide");
		}
	}
	

	public Slider save(Slider slideInForm) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DevelopUserDetails loggedUser = (DevelopUserDetails) auth.getPrincipal();
		if (slideInForm.getId() == null) {
			slideInForm.setCreatedTime(new Date());
			slideInForm.setUpdatedTime(new Date());
			slideInForm.setWorkUser("Tạo bởi " + loggedUser.getFullname());
		} else {
			Slider slideInDB = repo.findById(slideInForm.getId()).get();
			slideInForm.setCreatedTime(slideInDB.getCreatedTime());
			slideInForm.setUpdatedTime(new Date());
			slideInForm.setWorkUser("Sửa bởi " + loggedUser.getFullname());
		}

		return repo.save(slideInForm);
	}

	public void delete(Integer id) throws SliderNotFoundException {
		Long count = repo.countById(id);
		if (count == null || count == 0) {
			throw new SliderNotFoundException("Không thể tìm thấy slide với id:  " + id);
		}
		repo.deleteById(id);
	}
}
