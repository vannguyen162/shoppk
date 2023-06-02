package com.developer.admin.product.export;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.developer.admin.AbstractExporter;
import com.developer.common.entity.Product;

public class ProductExcelExporter extends AbstractExporter {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	public ProductExcelExporter() {
		workbook = new XSSFWorkbook();
	}

	private void writeHeaderLine() {
		sheet = workbook.createSheet("Products");
		XSSFRow row = sheet.createRow(0);

		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		cellStyle.setFont(font);
		
		createCell(row, 0, "Tên sản phẩm", cellStyle);
		createCell(row, 1, "Thương hiệu", cellStyle);
		createCell(row, 2, "Danh mục", cellStyle);
		createCell(row, 3, "Loại sản phẩm", cellStyle);
		createCell(row, 4, "Mã sản phẩm", cellStyle);
		createCell(row, 5, "Giá nhập", cellStyle);
		createCell(row, 6, "Giá bán", cellStyle);
		createCell(row, 7, "Số lượng", cellStyle);
		createCell(row, 8, "Số lượt xem", cellStyle);
		createCell(row, 9, "Thời gian khởi tạo", cellStyle);
		createCell(row, 10, "Cập nhật lần cuối", cellStyle);
		createCell(row, 11, "Người làm việc cuối cùng", cellStyle);
	}

	private void createCell(XSSFRow row, int columnIndex, Object value, CellStyle style) {
		XSSFCell cell = row.createCell(columnIndex);
		sheet.autoSizeColumn(columnIndex);

	    if (value instanceof Integer) {
	        cell.setCellValue((Integer) value);
	    } else if (value instanceof Boolean) {
	        cell.setCellValue((Boolean) value);
	    } else if (value instanceof BigDecimal) {
	        cell.setCellValue(((BigDecimal) value).toString());
	    } else {
	        cell.setCellValue((String) value);
	    }

		cell.setCellStyle(style);
	}

	public void export(List<Product> listProducts, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "application/octet-stream", ".xlsx", "sanpham_");
		
		writeHeaderLine();
		writeDataLines(listProducts);
		

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}

	private void writeDataLines(List<Product> listProducts) {
		int rowIndex = 1;
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		cellStyle.setFont(font);
		
		for(Product product : listProducts) {
			XSSFRow row = sheet.createRow(rowIndex++);
			int columnIndex = 0;
			
			createCell(row, columnIndex++, product.getName(), cellStyle);
			createCell(row, columnIndex++, product.getBrandName(), cellStyle);
			createCell(row, columnIndex++, product.getCategoryName(), cellStyle);
			createCell(row, columnIndex++, product.getKind(), cellStyle);
			createCell(row, columnIndex++, product.getPdtCode(), cellStyle);
			createCell(row, columnIndex++, product.getCostFormat(), cellStyle);
			createCell(row, columnIndex++, product.getPriceFormat(), cellStyle);
			createCell(row, columnIndex++, product.getQty(), cellStyle);
			createCell(row, columnIndex++, product.getNumViewValue(), cellStyle);
			createCell(row, columnIndex++, product.getCreateDateFormat(), cellStyle);
			createCell(row, columnIndex++, product.getUpDateFormat(), cellStyle);
			createCell(row, columnIndex++, product.getWorkUser(), cellStyle);
		}
	}

}
