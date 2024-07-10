package org.example.movieapp.Service;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

public interface ExcelService {
    void writeHeaders(String[] header);
    void createCell(Row row, int columnCount, Object value, XSSFCellStyle cellStyle);
    void initResponse(HttpServletResponse resp, String fileName);
    Sheet createWorkbook(String sheetName);
}
