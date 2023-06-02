package com.developer.promotional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.developer.common.entity.Board;

@Controller
public class PromotionalController {
	@Autowired private PromotionalService service;
	
	@GetMapping("/board")
	public String listFirstPage(Model model) {
		return listByPage(1, model);
	}
	
	@GetMapping("/board/page/{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model
			) {
		Page<Board> page = service.listBoard(pageNum);
		List<Board> listBoard = page.getContent();
		
		LocalDate now = LocalDate.now();
		System.out.println(now + "======================================");
		
		long startCount = (pageNum - 1) * PromotionalService.Board_PER_PAGE + 1;
		long endCount = startCount + PromotionalService.Board_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listBoard", listBoard);
		model.addAttribute("pageTitle", "Tin tức");

		return "promotional/board";
	}
	@GetMapping("/board/detail/{id}")
	public String viewBoardDetails(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes ra) {
		
		try {
			service.increaseNumViews(id);
			Board board = service.get(id);
			model.addAttribute("board", board);
			model.addAttribute("pageTitle", "Tin tức");
			return "promotional/board_detail";
		} catch (PromotionalNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
			return listByPage(1, model);
		}
	}

}
