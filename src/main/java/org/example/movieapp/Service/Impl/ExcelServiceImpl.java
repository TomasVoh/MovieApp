package org.example.movieapp.Service.Impl;

import jakarta.persistence.Column;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.movieapp.Service.ExcelService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ExcelServiceImpl implements ExcelService {
    protected XSSFWorkbook workbook;
    protected XSSFSheet sheet;

    public ExcelServiceImpl() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet();
    }

    @Override
    public void writeHeaders(String[] header) {
        Row row = sheet.createRow(0);
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        for (int i = 0; i < header.length; i++) {
            createCell(row, i, header[i], style);
        }
    }

    @Override
    public void createCell(Row row, int columnCount, Object value, XSSFCellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if(value instanceof String) {
            cell.setCellValue((String) value);
        } else if(value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if(value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if(value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        }
        cell.setCellStyle(style);
    }

    @Override
    public void initResponse(HttpServletResponse resp, String fileName) {
        resp.setContentType("application/octet-stream");
        String header = "Content-Disposition";
        String value = "attachment; filename=\"" + fileName + ".xlsx\"";
        resp.setHeader(header, value);
        try {
            ServletOutputStream outputStream = resp.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Workbook getWorkBook() {
        return workbook;
    }

    @Override
    public Sheet getSheet() {
        return sheet;
    }


}
