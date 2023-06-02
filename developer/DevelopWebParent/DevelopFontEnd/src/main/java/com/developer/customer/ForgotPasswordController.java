package com.developer.customer;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.developer.Utility;
import com.developer.common.entity.Customer;
import com.developer.common.exception.CustomerNotFoundException;
import com.developer.setting.EmailSettingBag;
import com.developer.setting.SettingService;

@Controller
public class ForgotPasswordController {
	@Autowired private CustomerService customerService;
	@Autowired private SettingService settingService;
	
	@GetMapping("/forgot_password")
	public String showRequestForm() {
		return "customer/forgot_password_form";
	}
	
	@PostMapping("/forgot_password")
	public String processRequestForm(HttpServletRequest request, Model model) {
		String email = request.getParameter("email");
		try {
			String token = customerService.updateResetPasswordToken(email);
			System.out.println("Email : " + email);
			System.out.println("Token : " + token);
			String link = Utility.getSiteURL(request) + "/reset_password?token=" + token;
			System.out.println("Link : " + link);
			sendEmail(link, email);
			model.addAttribute("message", "Bạn vừa gửi yêu cầu thay mật khẩu"
					+ "\nVui lòng kiểm tra hộp thư của bạn!"
					+ "\nThư yêu cầu thay đổi có thể nằm trong thư mục 'Thư rác'");
		} catch (CustomerNotFoundException e) {
			model.addAttribute("error", e.getMessage());
		} catch (UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error", "Không thể gửi Email!");
			
		}
			
		
		return "customer/forgot_password_form";
	}
	
	private void sendEmail(String link, String email) throws UnsupportedEncodingException, MessagingException {
		EmailSettingBag emailSettings = settingService.getEmailSettings();
		JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettings);
		
		String toAddress = email;
		String subject = "Quên mật khẩu";
		
		String content = "<p>Xin chào!</p>"
				+ "<p>Bạn đã yêu cầu thay đổi mật khẩu</p>"
				+ "<p>Click vào link bên đưới để thay đổi mật khẩu</p>"
				+ "<p><a href=\"" + link + "\" >Thay đổi mật khẩu</a></p>";
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
		
		helper.setFrom(emailSettings.getFromAddress(), emailSettings.getSenderName());
		helper.setTo(toAddress);
		helper.setSubject(subject);
		
		helper.setText(content , true);
		mailSender.send(message);
		
	}
	
	@GetMapping("/reset_password")
	public String showResetForm(String token, Model model) {
		Customer customer = customerService.getByResetPasswordToken(token);
		if(customer != null) {
			model.addAttribute("token", token);
		}else {
			model.addAttribute("pageTitle", "Thay đổi mật khẩu");
			model.addAttribute("message", "Token không tồn tại");
			return "message";
		}
		return "customer/reset_password_form";
	}
	
	@PostMapping("/reset_password")
	public String processResetForm(HttpServletRequest request, Model model) {
		String token = request.getParameter("token");
		String password = request.getParameter("password");
		
		try {
			customerService.updatePassword(token, password);
			model.addAttribute("pageTitle", "Thay đổi mật khẩu");
			model.addAttribute("title", "Thay đổi mật khẩu");
			model.addAttribute("message", "Bạn đã thay đổi mật khẩu!");
			return "message";
		} catch (CustomerNotFoundException e) {
			model.addAttribute("pageTitle", "Reset PassWord");
			model.addAttribute("message", e.getMessage());
			return "message";
		}
	}

}
