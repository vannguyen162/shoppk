package com.developer.notice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.developer.common.entity.Notice;

@Service
public class NoticeService {
	@Autowired
	private NoticeRepository repo;

	public List<Notice> listNoticesPoppup() {
		return repo.listNoticesPoppup();
	}

	public List<String> getAllShortDescriptions() {
		List<Notice> notices = repo.listNotices();
		List<String> shortDescriptions = new ArrayList<>();
		for (Notice n : notices) {
			shortDescriptions.add(n.getShortDescription());
		}
		return shortDescriptions;
	}
}
