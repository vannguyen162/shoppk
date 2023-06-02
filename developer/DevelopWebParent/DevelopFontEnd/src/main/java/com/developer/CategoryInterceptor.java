package com.developer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.developer.category.CategoryService;
import com.developer.common.entity.Category;

public class CategoryInterceptor extends HandlerInterceptorAdapter{
	
    private final CategoryService categoryService;

    public CategoryInterceptor(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        List<Category> rootCategories = categoryService.rootCategories();
        request.setAttribute("rootCategories", rootCategories);
        return true;
    }
}