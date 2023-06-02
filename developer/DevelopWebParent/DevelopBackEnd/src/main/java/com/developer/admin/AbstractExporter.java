package com.developer.admin;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.developer.common.entity.User;

public class AbstractExporter {

	public void setResponseHeader(HttpServletResponse response, String contentType,
			String extension, String prefix) throws IOException {
		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyy_HH-mm-ss");
		String timestamp = dateFormatter.format(new Date());
		String fileName = prefix + timestamp + extension;
		String chartFile = URLEncoder.encode(fileName, "UTF-8");

		response.setContentType(contentType);

		String headerKey = "Content-Disposition";
		String headerVale = "attachment; filename=" + chartFile;
		response.setHeader(headerKey, headerVale);
	}

}
