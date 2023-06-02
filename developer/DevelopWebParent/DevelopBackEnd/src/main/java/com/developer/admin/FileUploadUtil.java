package com.developer.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadUtil.class);

	public static void saveFile(String uploadDir, String fileName, 
			MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get(uploadDir);
		
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ex) {
			throw new IOException("Không thể lưu file: " + fileName, ex);
		}
	}

	public static void cleanDir(String dir) {
		Path dirPath = Paths.get(dir);

		try {
			Files.list(dirPath).forEach(file -> {
				if(!Files.isDirectory(file)) {
					try {
						Files.delete(file);
					} catch (IOException ex) {
						LOGGER.error("Không thể xóa file:" + file);
//						System.out.println("Không thể xóa file:" + file);					
					}
				}
			});
		} catch (IOException ex) {
			LOGGER.error("Danh sách rỗng:" + dirPath);
//			System.out.println("Danh sách rỗng: " + dirPath);
		}
	}

	public static void removeDir(String dir) {
		cleanDir(dir);
		
		try {
			Files.delete(Paths.get(dir));
		} catch (IOException e) {
			LOGGER.error("Không thể xóa thư mục: " + dir);
		}
		
	}

}
