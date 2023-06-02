package com.developer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.developer.brand.BrandService;
import com.developer.category.CategoryService;
import com.developer.common.entity.Brand;
import com.developer.common.entity.Category;
import com.developer.common.entity.Notice;
import com.developer.common.entity.Product;
import com.developer.common.entity.Slider;
import com.developer.common.entity.customer.ClientInfo;
import com.developer.configure.BrowserInfo;
import com.developer.configure.InfoPcClientUtil;
import com.developer.customer.client.ClientInformationRepository;
import com.developer.notice.NoticeService;
import com.developer.product.ProductService;
import com.developer.slider.SliderService;

@Controller
public class MainController {

	@Autowired
	private NoticeService noticeService;
	@Autowired
	private ProductService productService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private SliderService sliderService;
	
	// 03/04/2023 set MAC address Consumer.
	@Autowired
	private ClientInformationRepository clientInforRepo;

	@GetMapping("")
	public String viewHomePage(Model model, HttpServletRequest request, HttpServletResponse response) {
		List<Notice> listPoppupNotices = noticeService.listNoticesPoppup();
		List<Notice> filteredNotices = new ArrayList<>();
		for (Notice notice : listPoppupNotices) {
			String cookieName = "poppupvalue_" + notice.getId();
			if (CookieUtils.getCookieValue(request, cookieName) == null) {
				filteredNotices.add(notice);
			}
		}
		Page<Product> listProductsByViews = productService.getMostViewsProduct(1);
		model.addAttribute("listProductsByViews", listProductsByViews);
		List<Product> listProductsRelateds = productService.getRelatedProduct();
		model.addAttribute("listProductsRelateds", listProductsRelateds);

		model.addAttribute("listPoppupNotices", filteredNotices);
		
		// 26/03/2023
		List<Brand> listBrands = brandService.listAllBrands();
		model.addAttribute("listBrands", listBrands);
		
		// 03/04/2023 set MAC address Consumer.
		setClientInfor(request);
		
		// 17/05/2023
		List<Slider> sliders = sliderService.listSlider();
		model.addAttribute("listSliders", sliders);
		
		return "index";
	}
	
	// 03/04/2023 set MAC address Consumer.
	private void setClientInfor(HttpServletRequest request) {
		// set device, IP address, and browser info using InfoPcClientUtil
//		device
		String userAgent = request.getHeader("User-Agent");
		String device = "Unknown";
		if (userAgent.contains("iPhone")) {
			device = "iPhone";
		} else if (userAgent.contains("Android")) {
			device = "Android";
		}
		InfoPcClientUtil.setDevice(device);
//		IP
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null || ipAddress.isEmpty()) {
			ipAddress = request.getRemoteAddr();
			InfoPcClientUtil.setIpAddress(ipAddress);
		}
//		Browser and version
		String[] browserInfo = BrowserInfo.getBrowserInfo(request);
		if (browserInfo.length > 1) {
			InfoPcClientUtil.setBrowserName(browserInfo[0]);
			InfoPcClientUtil.setBrowserVersion(browserInfo[1]);
		} else {
			InfoPcClientUtil.setBrowserName(browserInfo[0]);
			InfoPcClientUtil.setBrowserVersion(null);
		}
		// get MAC address using client IP address
		String macAddress = InfoPcClientUtil.getClientMACAddress(request.getRemoteAddr());
//	    Check MAC Address In DATABASE
		if (macAddress != null) {
			// Check MAC Address In DATABASE
			ClientInfo clientInfo = StreamSupport.stream(clientInforRepo.findAll().spliterator(), false)
					.filter(c -> macAddress.equals(c.getMacAddress())).findFirst().orElse(null);
			if (clientInfo != null) {
				clientInfo.setIpAddress(InfoPcClientUtil.getIpAddress());
				clientInfo.setBrowserName(InfoPcClientUtil.getBrowserName());
				clientInfo.setBrowserVersion(InfoPcClientUtil.getBrowserVersion());
				clientInfo.setDeviceInformation(InfoPcClientUtil.getDevice());
				clientInforRepo.save(clientInfo);
			} else {
				ClientInfo newClientInfo = new ClientInfo();
				newClientInfo.setMacAddress(macAddress);
				newClientInfo.setIpAddress(InfoPcClientUtil.getIpAddress());
				newClientInfo.setBrowserName(InfoPcClientUtil.getBrowserName());
				newClientInfo.setBrowserVersion(InfoPcClientUtil.getBrowserVersion());
				newClientInfo.setDeviceInformation(InfoPcClientUtil.getDevice());
				clientInforRepo.save(newClientInfo);
			}
		}
	}

	@GetMapping("/login")
	public String viewLoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}

		return "redirect:/";
	}
	
	// 26/03/2023
	@GetMapping("/brand/{brandId}/page/{pageNum}")
	public String viewBrandByPage(@PathVariable("brandId") Integer id, @PathVariable("pageNum") int pageNum,
			Model model) {
		Brand brand = brandService.get(id);

		Page<Product> pageProducts = productService.listByBrands(pageNum, brand.getId());
		List<Product> listProducts = pageProducts.getContent();

		long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
		long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
		if (endCount > pageProducts.getTotalElements()) {
			endCount = pageProducts.getTotalElements();
		}

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", pageProducts.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", pageProducts.getTotalElements());
		model.addAttribute("pageTitle", brand.getName());
		model.addAttribute("listProducts", listProducts);
		model.addAttribute("brand", brand);

		return "product/product_by_brand";
	}

}
