package com.developer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.developer.category.CategoryService;
import com.developer.common.entity.Category;
import com.developer.notice.NoticeService;

public class NoticeInterceptor extends HandlerInterceptorAdapter{
	
    private final NoticeService noticeService;

    public NoticeInterceptor(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        List<String> shortDescriptions = noticeService.getAllShortDescriptions();
        request.setAttribute("shortDescriptions", shortDescriptions);
        return true;
    }
}