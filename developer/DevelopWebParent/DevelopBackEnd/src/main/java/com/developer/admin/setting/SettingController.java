package com.developer.admin.setting;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.developer.admin.FileUploadUtil;
import com.developer.common.entity.Currency;
import com.developer.common.entity.setting.Setting;



@Controller
public class SettingController {

	@Autowired private SettingService service;
	
	@Autowired private CurrencyRepository currencyRepo;
	
	
	@GetMapping("/settings")
	public String listAll(Model model) {
		List<Setting> listSettings = service.listAllSettings();
		List<Currency> listCurrencies = currencyRepo.findAllByOrderByNameAsc();
		
		model.addAttribute("listCurrencies", listCurrencies);
		
		for(Setting setting : listSettings) {
			model.addAttribute(setting.getKey(), setting.getValue());
		}
		
		return "settings/settings";
	}
	
	@PostMapping("/settings/save_general")
	public String saveGeneralSettings(@RequestParam("fileImage") MultipartFile multipartFile,
			HttpServletRequest request, RedirectAttributes ra
			) throws IOException {
		GeneralSettingbag settingBag = service.getGeneralSettings(); 
		
		saveSiteLogo(multipartFile, settingBag);
		saveCurrencySymbol(request, settingBag);
		
		updateSettingValuesFromForm(request, settingBag.list());
		
		ra.addFlashAttribute("message", "Đã lưu cài đặt");
		
		return "redirect:/settings";
	}
	
	private void saveSiteLogo(MultipartFile multipartFile, GeneralSettingbag settingBag) throws IOException {
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			String value = "/site-logo/" + fileName;
			settingBag.updateSiteLogo(value);
			
			String uploadDir = "../site-logo/";
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		}
	}
	
	private void saveCurrencySymbol(HttpServletRequest request, GeneralSettingbag settingBag) {
		Integer currencyId = Integer.parseInt(request.getParameter("CURRENCY_ID"));
		Optional<Currency> findByIdResult = currencyRepo.findById(currencyId);
		
		if(findByIdResult.isPresent()) {
			Currency currency = findByIdResult.get();
			settingBag.updateCurrencySymbol(currency.getSymbol());
		}
	}
	
	private void updateSettingValuesFromForm(HttpServletRequest request, List<Setting> listSettings) {
		for(Setting setting : listSettings) {
			String value = request.getParameter(setting.getKey());
			if(value != null) {
				setting.setValue(value);
			}
		}
		service.saveAll(listSettings);
	}
	
	@PostMapping("/settings/save_mail_server")
	public String saveMailServerSettings(HttpServletRequest request, RedirectAttributes ra) {
		List<Setting> mailServerSettings = service.getMailServerSettings();
		updateSettingValuesFromForm(request, mailServerSettings);
		
		ra.addFlashAttribute("message", "Đã lưu cài đặt E-Mail");
		
		return "redirect:/settings#mailServer";
	}
	
	@PostMapping("/settings/save_mail_templates")
	public String saveMailTemplateSettings(HttpServletRequest request, RedirectAttributes ra) {
		List<Setting> mailTempalateSettings = service.getMailTemplateSettings();
		updateSettingValuesFromForm(request, mailTempalateSettings);
		
		ra.addFlashAttribute("message", "Đã lưu cài đặt nội dung gửi E-Mail");
		
		return "redirect:/settings#mailTemplates";
	}
	
	@PostMapping("/settings/save_payment")
	public String savePaymentSettings(HttpServletRequest request, RedirectAttributes ra) {
		List<Setting> paymentSettings = service.getPaymentSettings();
		updateSettingValuesFromForm(request, paymentSettings);
		
		ra.addFlashAttribute("message", "Đã lưu cài đặt thanh toán");
		
		return "redirect:/settings#payment";
	}
}
