package com.developer.admin.backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.developer.admin.paging.PagingAndSortingHelper;
import com.developer.admin.paging.PagingAndSortingParam;
import com.developer.common.entity.Backup;

@Controller
public class BackupController {
	
	@Autowired
	private BackupService service;
	@Autowired
	private BackupRepository backupRepository;

	@GetMapping("/backuplist")
	public String listFirstPage() {
		return "redirect:/backuplist/page/1?sortField=id&sortDir=asc";
	}

	@GetMapping("/backuplist/page/{pageNum}")
	public String listByPage(
			@PagingAndSortingParam(listName = "listBackup", moduleURL = "/backuplist") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum, Model model) {
		service.listByPage(pageNum, helper);

		model.addAttribute("pageTitle", "Danh sách sao lưu");

		return "settings/backup";
	}
	
	@GetMapping("/download")
	public void downloadFile(@RequestParam("path") String path, HttpServletResponse response) throws IOException {
	    File file = new File(path);
	    response.setContentType("application/octet-stream");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
	    try (InputStream inputStream = new FileInputStream(file); OutputStream outputStream = response.getOutputStream()) {
	        byte[] buffer = new byte[4096];
	        int length;
	        while ((length = inputStream.read(buffer)) > 0) {
	            outputStream.write(buffer, 0, length);
	        }
	    }
	    incrementDownloadCount(path);
	}
	
	public void incrementDownloadCount(String path) {
	  Backup backupFile = backupRepository.findByPathFile(path);
	  
	  backupFile.setCountDownload(backupFile.getCountDownload() + 1);
	  backupRepository.save(backupFile);
	}
}
