package org.example.movieapp.Service.Impl;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.movieapp.Model.Movie;
import org.example.movieapp.Repository.MovieRepository;
import org.example.movieapp.Service.ExcelService;
import org.example.movieapp.Service.MovieImportExportService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MovieImportExportServiceImpl implements MovieImportExportService {
    private ExcelService excelService;
    private MovieRepository movieRepository;

    public MovieImportExportServiceImpl(ExcelService excelService, MovieRepository movieRepository) {
        this.excelService = excelService;
        this.movieRepository = movieRepository;
    }

    @Override
    public void exportToExcel(HttpServletResponse response) {
        List<Movie> movieList = movieRepository.findAll();
        excelService.writeHeaders(new String[]{"Id", "Název", "Popis", "Délka", "Datum vydání", "Cesta k obrázku"});
        int startRow = 1;
        for (Movie movie : movieList) {
            Row row = excelService.getWorkBook().getSheetAt(0).createRow(startRow);
            int columnCount = 0;
            excelService.createCell(row, columnCount++, movie.getId(), null);
            excelService.createCell(row, columnCount++, movie.getName(), null);
            excelService.createCell(row, columnCount++, movie.getDescription(), null);
            excelService.createCell(row, columnCount++, movie.getLength(), null);
            excelService.createCell(row, columnCount++, movie.getReleaseDate(), null);
            excelService.createCell(row, columnCount, movie.getImagePath(), null);
            startRow++;
        }
        excelService.initResponse(response, "movie");
    }

    @Override
    public void importFromExcel(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Movie movie = movieRepository.findById((long) row.getCell(0).getNumericCellValue()).orElseThrow(NoSuchElementException::new);
                movie.setName(row.getCell(1).getStringCellValue());
                movie.setDescription(row.getCell(2).getStringCellValue());
                movie.setLength((int) row.getCell(3).getNumericCellValue());
                movie.setReleaseDate(row.getCell(4).getLocalDateTimeCellValue().toLocalDate());
                movie.setImagePath(row.getCell(5).getStringCellValue());
                movieRepository.save(movie);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
