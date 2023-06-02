package com.developer.admin.slider;

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
import com.developer.admin.promotional.PromotionalNotFoundException;
import com.developer.common.entity.Board;
import com.developer.common.entity.Slider;

@Controller
public class SliderController {

	private String defaultRedirectURL = "redirect:/slider/page/1?sortField=id&sortDir=asc";
	@Autowired
	private SliderService service;

	@GetMapping("/slider")
	public String listFirstPage() {
		return defaultRedirectURL;
	}

	@GetMapping("/slider/page/{pageNum}")
	public String listByPage(
			@PagingAndSortingParam(listName = "listSliders", moduleURL = "/slider") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum, Model model) {
		service.listByPage(pageNum, helper);

		model.addAttribute("pageTitle", "Quản lý quảng cáo");

		return "slider/slider";
	}

	@GetMapping("/slider/{id}/enabled/{status}")
	public String updateSlideEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled,
			RedirectAttributes redirectAttributes) {
		service.updateSlideEnabledStatus(id, enabled);
		String message = "Quảng cáo đã được cập nhật trạng thái";
		redirectAttributes.addFlashAttribute("message", message);

		return defaultRedirectURL;
	}

	@GetMapping("/slider/new")
	public String newSlider(Model model) {

		model.addAttribute("slider", new Slider());
		model.addAttribute("pageTitle", "Thêm quảng cáo");
		return "slider/slider_form";
	}

	@PostMapping("/slider/save")
	public String saveSlider(Slider slider, RedirectAttributes redirectAttributes,
			@RequestParam(value = "fileImage", required = false) MultipartFile multipartFile) throws IOException {

		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			slider.setMainImage(fileName);
			Slider saveSlide = service.save(slider);

			String uploadDir = "../slider-images/" + saveSlide.getId();
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

		} else {

			service.save(slider);
		}

		redirectAttributes.addFlashAttribute("message", "Thêm quảng cáo thành công");

		return defaultRedirectURL;
	}
	
	@GetMapping("/slider/delete/{id}")
	public String deleteSlider(@PathVariable(name = "id") Integer id,
			Model model, 
			RedirectAttributes redirectAttributes) {
		
		try {
			service.delete(id);
			String productImagesDir = "../slider-images/" + id;
			
			FileUploadUtil.removeDir(productImagesDir);

			
			redirectAttributes.addFlashAttribute("message", "Đã xóa quảng cáo (ID: " + id + ")");
		} catch (SliderNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			
		}
		return defaultRedirectURL;
	}
	
	@GetMapping("/slider/edit/{id}")
	public String editSlider(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes ra) {
		try {
			Slider slider = service.get(id);
			
			model.addAttribute("slider", slider);
			model.addAttribute("pageTitle", "Chỉnh sửa Tin tức (ID: " + id + ")");
			
			return "slider/slider_form";
			
		} catch (SliderNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());

			return defaultRedirectURL;
		}
	}
}
