package com.developer.admin.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.developer.admin.paging.PagingAndSortingHelper;
import com.developer.admin.paging.PagingAndSortingParam;
import com.developer.common.entity.Notice;

@Controller
public class NoticeController {

	private String defaultRedirectURL = "redirect:/notice/page/1?sortField=id&sortDir=asc";
	@Autowired private NoticeService service;
	
	@GetMapping("/notice")
	public String listFirstPage() {
		return defaultRedirectURL;
	}
	
	@GetMapping("/notice/page/{pageNum}")
	public String listByPage(@PagingAndSortingParam(listName = "listNotices", 
			moduleURL = "/notice") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum, Model model) {
		service.listByPage(pageNum, helper);
		
		model.addAttribute("pageTitle", "Quản lý thông báo");
		
		return "notice/notice";
	}
	
	@GetMapping("/notice/{id}/enabled/{status}")
	public String updateNoticeEnabledStatus(@PathVariable("id") Integer id,
			@PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
		service.updateNoticeEnabledStatus(id, enabled);
		String message = "Thông báo đã được cập nhật trạng thái";
		redirectAttributes.addFlashAttribute("message", message);
		
		return defaultRedirectURL;
	}
	
	@GetMapping("/notice/new")
	public String newNotice(Model model) {
		
		model.addAttribute("notice", new Notice());
		model.addAttribute("pageTitle", "Tạo mới thông báo");
		
		return "notice/notice_form";
	}
	
	@PostMapping("/notice/save")
	public String saveNotice(Notice notice, RedirectAttributes ra){
		service.save(notice);
		ra.addFlashAttribute("message", "Thêm thông báo thành công");
		return defaultRedirectURL;
	}
	
	@GetMapping("/notice/edit/{id}")
	public String editNotice(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes ra) throws NoticeNotFoundException {
		try {
			Notice notice = service.get(id);
			
			model.addAttribute("notice", notice);
			model.addAttribute("pageTitle", "Chỉnh sửa thông báo");
			
			return "notice/notice_form";
			
		} catch (NoticeNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());

			return defaultRedirectURL;
		}
	}
	
	@GetMapping("/notice/delete/{id}")
	public String deleteNotice(@PathVariable Integer id, RedirectAttributes ra) {
		try {
			Notice notice = service.get(id);
			service.delete(id);
			ra.addFlashAttribute("message", "Đã xóa thông báo: " + notice.getShortDescription());
		} catch (NoticeNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			
		}
		return defaultRedirectURL;
	}
}
