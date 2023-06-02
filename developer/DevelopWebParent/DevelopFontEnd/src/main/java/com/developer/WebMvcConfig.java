package com.developer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.developer.category.CategoryService;
import com.developer.customer.CustomerService;
import com.developer.notice.NoticeService;
import com.developer.shoppingcart.ShoppingCartService;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final CategoryService categoryService;
    private final ShoppingCartService cartService;
    private final ApplicationContext applicationContext;
    private final NoticeService noticeService;

	public WebMvcConfig(CategoryService categoryService, ShoppingCartService cartService,
			ApplicationContext applicationContext, NoticeService noticeService) {
		this.categoryService = categoryService;
		this.cartService = cartService;
		this.applicationContext = applicationContext;
		this.noticeService = noticeService;
	}

	@Bean
    public CategoryInterceptor categoryInterceptor() {
        return new CategoryInterceptor(categoryService);
    }
    
    @Bean
    public CartInterceptor cartInterceptor() {
    	return new CartInterceptor(cartService, applicationContext);
    }
    
	@Bean
    public NoticeInterceptor noticeInterceptor() {
        return new NoticeInterceptor(noticeService);
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(categoryInterceptor());
        registry.addInterceptor(cartInterceptor());
        registry.addInterceptor(noticeInterceptor());
    }
}
