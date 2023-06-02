package com.developer.admin.user.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.developer.admin.FileUploadUtil;
import com.developer.admin.security.DevelopUserDetails;
import com.developer.admin.user.UserService;
import com.developer.common.entity.User;

@Controller
public class AccountController {
	
	@Autowired
	private UserService service;
	
	@RequestMapping("/account")
	public String viewDetails(@AuthenticationPrincipal DevelopUserDetails loggedUser,
			Model model) {
		String email = loggedUser.getUsername();
		User user = service.getByEmail(email);
		
		model.addAttribute("user", user);
		model.addAttribute("pageTitle", "Tài khoản của tôi");
		
		return "users/account_form";
	}
	
	@PostMapping("/account/update")
	public String saveDetails(User user, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal DevelopUserDetails loggedUser,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {
		
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);
			User saveUser = service.updateAccount(user);
			
			String uploadDir = "user-photos/" + saveUser.getId();
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			
		}else {
			if(user.getPhotos().isEmpty()) user.setPhotos(null);
			service.updateAccount(user);
			
		}
		loggedUser.setFullName(user.getFullName());
		
		redirectAttributes.addFlashAttribute("message", "Cập nhật tài khoản thành công!");
		
		return "redirect:/account";
	}

}
