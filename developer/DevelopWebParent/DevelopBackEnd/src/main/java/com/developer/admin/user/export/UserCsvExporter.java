package com.developer.admin.user.export;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.developer.admin.AbstractExporter;
import com.developer.common.entity.User;

public class UserCsvExporter extends AbstractExporter{
	
	public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "text/csv; charset=UTF-8", ".csv", "taikhoan_");
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
				CsvPreference.STANDARD_PREFERENCE);
		
		String [] csvHeader = {"ID", "Họ và Tên" , "SĐT" , "Cmnd/Căn cước", "E-mail", "Địa chỉ", "Chức vụ", "Bằng Cấp", "Trạng thái"};
		String [] fieldMapping = {"id", "fullName" , "phone" , "identity_card", "email", "adress", "roles", "degrees", "enabled"};
		csvWriter.writeHeader(csvHeader);
		
		for(User user : listUsers) {
			csvWriter.write(user, fieldMapping);
		}
		
		csvWriter.close();
	}

}
