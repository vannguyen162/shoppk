package com.developer;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		/*
//		 * registry.addResourceHandler("/resources/bootstrap/**") .addResourceLocations(
//		 * "classpath:/META-INF/resources/webjars/bootstrap/4.3.1/");
//		 * registry.addResourceHandler("/resources/jquery/**")
//		 * .addResourceLocations("classpath:/META-INF/resources/webjars/jquery/3.4.1/");
//		 * registry.addResourceHandler("/resources/css/**").addResourceLocations(
//		 * "classpath:/static/css/");
//		 */
////		===============================================================================
//		// regist folder Category
//		String categoryImagesDirName = "../category-images";
//		Path categoryImagesDir = Paths.get(categoryImagesDirName);
//		
//		String categoryImagesPath = categoryImagesDir.toFile().getAbsolutePath();
//		
//		registry.addResourceHandler("/category-images/**")
//				.addResourceLocations("file:/" + categoryImagesPath + "/");
////		===============================================================================		
//		// regist folder Category		
//		String brandLogosDirName = "../brand-logos";
//		Path brandLogosDir = Paths.get(brandLogosDirName);
//		
//		String brandLogosPath = brandLogosDir.toFile().getAbsolutePath();
//		
//		registry.addResourceHandler("/brand-logos/**")
//		.addResourceLocations("file:/" + brandLogosPath + "/");
////		===============================================================================		
//		// regist folder Product		
//		String productLogosDirName = "../product-images";
//		Path productLogosDir = Paths.get(productLogosDirName);
//		
//		String productLogosPath = productLogosDir.toFile().getAbsolutePath();
//		
//		registry.addResourceHandler("/product-images/**")
//		.addResourceLocations("file:/" + productLogosPath + "/");
//
//	}
//}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		exposeDirectory("../category-images", registry);
		exposeDirectory("../brand-logos", registry);
		exposeDirectory("../product-images", registry);
		exposeDirectory("../site-logo", registry);
		exposeDirectory("../board-images", registry);
		exposeDirectory("../slider-images", registry);
	}

	private void exposeDirectory(String pathPattern, ResourceHandlerRegistry registry) {
		Path path = Paths.get(pathPattern);
		String absolutePath = path.toFile().getAbsolutePath();

		String logicalPath = pathPattern.replace("../", "") + "/**";

		registry.addResourceHandler(logicalPath).addResourceLocations("file:/" + absolutePath + "/");
	}
}