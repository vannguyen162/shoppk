package com.developer.admin.backup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.developer.admin.setting.SettingRepository;
import com.developer.common.entity.Backup;
import com.developer.common.entity.setting.Setting;

@Component
public class BackupManager {
     private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
     @Autowired
     private BackupRepository backupRepo;
     
     @Autowired
     private DataSource dataSource;
     
     @Autowired
     private SettingRepository settingRepository;

     @PostConstruct
     public void init() {
    	 Setting backupSetting = settingRepository.findByKey("BACKUP_YN");
    	 if (backupSetting != null && "Y".equals(backupSetting.getValue())) {
             scheduleDailyBackup();
         }else {
        	 cancelDailyBackup();
		}
     }

     private void scheduleDailyBackup() {
         // Lên lịch cho thời gian biểu
         LocalTime backupTime = LocalTime.of(23, 19); // ví dụ 23h37 phút
         ZonedDateTime now = ZonedDateTime.now();
         ZonedDateTime nextRun = now.with(backupTime);
         if (now.compareTo(nextRun) > 0)
             nextRun = nextRun.plusDays(1);

         scheduler.scheduleAtFixedRate(new Runnable() {
             public void run() {
                 try {
                     backupDatabase();
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
         }, Duration.between(now, nextRun).toMillis(), Duration.ofDays(1).toMillis(), TimeUnit.MILLISECONDS);
     }

     private void cancelDailyBackup() {
         scheduler.shutdown();
     }
    
     private void backupDatabase() throws SQLException, IOException {
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
                 String lines;
                 StringBuilder errorMessage = new StringBuilder();
                 while ((lines = reader.readLine()) != null) {
                     errorMessage.append(lines).append("\n");
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
     }
}
