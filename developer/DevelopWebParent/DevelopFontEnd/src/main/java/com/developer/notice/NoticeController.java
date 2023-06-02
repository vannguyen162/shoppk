package com.developer.notice;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.developer.common.entity.Notice;

@Controller
public class NoticeController {

	@Autowired private NoticeService noticeService;
	
	@GetMapping("/poppup_notice")
	public String showPoppup(Model model, HttpServletRequest request) {
		List<Notice> listNotices = noticeService.listNoticesPoppup();
		model.addAttribute("listNotices", listNotices);
		return "";
	}
}
