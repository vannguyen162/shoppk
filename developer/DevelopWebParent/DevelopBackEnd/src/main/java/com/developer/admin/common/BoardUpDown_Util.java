package com.developer.admin.common;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractView;

public class BoardUpDown_Util extends AbstractView {
	private static final int BUFF_SIZE = 2048;

	private Logger logger = LoggerFactory.getLogger(BoardEditor_Util.class);

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String downFileName = (String) model.get("downFileName");
		String orgFileName = (String) model.get("orgFileName");
		
		logger.debug("download:" + downFileName + " >>> " + orgFileName);
		
		response.setContentType("application/x-msdownload");
		String fileName = URLEncoder.encode(orgFileName, "UTF-8");		

		response.setHeader("Content-disposition", "attachment; filename="+ fileName);	
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		
		File file = new File(downFileName);		
		byte[] b = new byte[BUFF_SIZE]; // buffer size 2K.
		BufferedInputStream fin = null;
		BufferedOutputStream outs = null;

		try {
			if (!file.exists()) {
				throw new FileNotFoundException(downFileName);
			}

			if (!file.isFile()) {
				throw new FileNotFoundException(downFileName);
			}
			
			fin = new BufferedInputStream(new FileInputStream(file));
			outs = new BufferedOutputStream(response.getOutputStream());
			int read = 0;

			while ((read = fin.read(b)) != -1) {
				outs.write(b, 0, read);
			}
		} catch (Exception e){
			logger.error("FIle Down IGNORED: " + e.getMessage());
		} finally {
			if (outs != null) {
				try {
					outs.close();
				} catch (Exception ignore) {
					logger.error("IGNORED: " + ignore.getMessage());
				}
			}
			if (fin != null) {
				try {
					fin.close();
				} catch (Exception ignore) {
					logger.error("IGNORED: " + ignore.getMessage());
				}
			}
		}
	}

}
