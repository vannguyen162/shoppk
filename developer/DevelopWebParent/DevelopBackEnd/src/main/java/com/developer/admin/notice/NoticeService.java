package com.developer.admin.notice;

import java.util.Date;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.developer.admin.paging.PagingAndSortingHelper;
import com.developer.admin.security.DevelopUserDetails;
import com.developer.common.entity.Notice;

@Service
@Transactional
public class NoticeService {

	@Autowired
	private NoticeRepository repo;
	public static final int NOTICE_PER_PAGE = 10;

	public void listByPage(int pageNum, PagingAndSortingHelper helper) {
		helper.listEntities(pageNum, NOTICE_PER_PAGE, repo);
	}

	public void updateNoticeEnabledStatus(Integer id, boolean enabled) {
		repo.updateEnabledStatus(id, enabled);
	}

	public Notice get(Integer id) throws NoticeNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new NoticeNotFoundException("Không thể tìm thấy thông báo");
		}
	}

	public Notice save(Notice noticeInForm) {
		if (noticeInForm.getId() == null) {
			noticeInForm.setCreatedTime(new Date());
			noticeInForm.setUpdatedTime(new Date());
		} else {
			Notice noticeInDB = repo.findById(noticeInForm.getId()).get();
			noticeInForm.setCreatedTime(noticeInDB.getCreatedTime());
			noticeInForm.setUpdatedTime(new Date());
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DevelopUserDetails loggedUser = (DevelopUserDetails) auth.getPrincipal();
		noticeInForm.setWorkUser(loggedUser.getFullname());

		return repo.save(noticeInForm);
	}

	public void delete(Integer id) throws NoticeNotFoundException {
		Long count = repo.countById(id);
		if (count == null || count == 0) {
			throw new NoticeNotFoundException("Không thể tìm thông báo với: " + id);
		}
		repo.deleteById(id);
	}
}
