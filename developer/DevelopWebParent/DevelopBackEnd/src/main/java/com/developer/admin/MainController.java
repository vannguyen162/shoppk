package com.developer.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.developer.admin.backup.BackupManager;
import com.developer.admin.backup.BackupRepository;
import com.developer.common.entity.Backup;
import com.developer.common.entity.setting.SettingBag;

@Controller
public class MainController {

	@Autowired
    private DataSource dataSource;
	
	@Autowired
	private BackupRepository backupRepo;
	
    @Autowired
    private BackupManager backupManager;
	
	@GetMapping("")
	public String viewHomePage(Model model) {

		model.addAttribute("pageTitle", "Trang chủ");

		return "index";
	}

	@GetMapping("/login")
	public String viewLoginPage(Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}

		model.addAttribute("pageTitle", "Đăng nhập");

		return "redirect:/";
	}
	
	@GetMapping("/backup")
	public String backupDatabase(RedirectAttributes ra) throws IOException, SQLException {
		
	    String dbName = dataSource.getConnection().getCatalog();
	    String backupDir = "backup_data/";
	    String backupFilename = "backup_" + new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date()) + ".sql";
	    String backupFilePath = backupDir + backupFilename;
	    ProcessBuilder processBuilder = new ProcessBuilder(
	            "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe",
	            "--add-drop-database",
	            "-B",
	            dbName,
	            "-u",
	            "root",
	            "-p" + "root",
	            "-r",
	            backupFilePath
	    );
	    Process process = processBuilder.start();
	    try {
	        int exitValue = process.waitFor();
	        if (exitValue != 0) {
	            // print out the error message
	            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
	            String line;
	            StringBuilder errorMessage = new StringBuilder();
	            while ((line = reader.readLine()) != null) {
	                errorMessage.append(line).append("\n");
	            }
	            throw new RuntimeException("Sao lưu không thành công " + exitValue + "\nError message: " + errorMessage.toString());
	        }
	        else {
	            // Backup was successful, save path to database
	            Backup backup = new Backup();
	            backup.setPathFile(backupFilePath);
	            backup.setCountDownload(0);
	            backup.setTimeCreate(new Date());
	            backupRepo.save(backup);
	        }
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	        throw new RuntimeException("Sao lưu bị gián đoạn", e);
	    }
	    ra.addFlashAttribute("message", "Đã sao lưu dữ liệu: " + backupFilename);
	    return "redirect:/settings";
	}


}
