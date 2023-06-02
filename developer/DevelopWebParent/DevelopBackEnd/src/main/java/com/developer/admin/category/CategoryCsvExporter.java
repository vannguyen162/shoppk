package com.developer.admin.category;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.developer.admin.AbstractExporter;
import com.developer.common.entity.Category;
import com.developer.common.entity.User;

public class CategoryCsvExporter extends AbstractExporter{
	public void export(List<Category> listCategories, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "text/csv; charset=UTF-8", ".csv", "danhmuc_");
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
				CsvPreference.STANDARD_PREFERENCE);
		
		String [] csvHeader = {"ID danh mục", "Tên danh mục"};
		String [] fieldMapping = {"id", "name" };
		csvWriter.writeHeader(csvHeader);
		
		for(Category category : listCategories) {
			category.setName(category.getName().replace("--", " "));
			csvWriter.write(category, fieldMapping);
		}
		
		csvWriter.close();
	}


}
