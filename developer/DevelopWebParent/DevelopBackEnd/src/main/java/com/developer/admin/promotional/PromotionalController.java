package com.developer.admin.promotional;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.developer.admin.FileUploadUtil;
import com.developer.admin.paging.PagingAndSortingHelper;
import com.developer.admin.paging.PagingAndSortingParam;
import com.developer.common.entity.Board;
import com.developer.common.exception.ProductNotFoundException;

@Controller
public class PromotionalController {

	@Autowired
	private PromotionalService service;
	
	@GetMapping("/board")
	public String listFirstPage() {
		return "redirect:/board/page/1?sortField=id&sortDir=asc";
	}
	
	@GetMapping("/board/page/{pageNum}")
	public String listByPage(
			@PagingAndSortingParam(listName = "listBoard", moduleURL = "/board") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum, Model model
			) {
		service.listByPage(pageNum, helper);
		
		model.addAttribute("pageTitle", "Quản lý tin tức");

		
		return "board/board";
	}
	@GetMapping("/board/{id}/enabled/{status}")
	public String updateBoardEnabledStatus(@PathVariable("id") Integer id,
			@PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
		service.updateEnabledStatus(id, enabled);
		String status = enabled ? ": hiện" : ": ẩn";
		String message = "Tin đăng (ID: "+ id +") đã được cập nhật trạng thái " + status;
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/board";
	}
	
	@GetMapping("/board/newboard")
	public String newboard(Model model) {

		Board board = new Board();
		board.setEnabled(true);

		model.addAttribute("board", board);
		model.addAttribute("pageTitle", "Thêm tin tức");
		return "board/board_form";
	}
	
	@PostMapping("/board/save")
	public String saveboard(Board board, RedirectAttributes redirectAttributes,
			@RequestParam(value = "fileImage", required = false) MultipartFile multipartFile) throws IOException {
		
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			board.setMainImage(fileName);
			Board saveBoard = service.save(board);
			
			String uploadDir = "../board-images/" + saveBoard.getId();
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			
		}else {
			
			service.save(board);
		}
		
		redirectAttributes.addFlashAttribute("message", "Thêm tin tức thành công");
		
		return "redirect:/board";
	}
	@GetMapping("/board/detail/{id}")
	public String viewBoardDetails(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes ra) {
		try {
			Board board = service.get(id);
			model.addAttribute("board", board);
			
			return "board/board_detail_modal";
			
		} catch (PromotionalNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
			
			return "redirect:/board";
		}
	}
	@GetMapping("/board/edit/{id}")
	public String editBoard(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes ra) {
		try {
			Board board = service.get(id);
			
			model.addAttribute("board", board);
			model.addAttribute("pageTitle", "Chỉnh sửa Tin tức (ID: " + id + ")");
			
			return "board/board_form";
			
		} catch (PromotionalNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());

			return "redirect:/board";
		}
	}
	@GetMapping("/board/delete/{id}")
	public String deleteBoard(@PathVariable(name = "id") Integer id,
			Model model, 
			RedirectAttributes redirectAttributes) {
		
		try {
			service.delete(id);
			String productImagesDir = "../board-images/" + id;
			
			FileUploadUtil.removeDir(productImagesDir);

			
			redirectAttributes.addFlashAttribute("message", "Đã xóa tin tức (ID: " + id + ")");
		} catch (PromotionalNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			
		}
		return "redirect:/board";
	}
}
