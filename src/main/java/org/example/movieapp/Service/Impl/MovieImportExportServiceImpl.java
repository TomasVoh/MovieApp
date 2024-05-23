package org.example.movieapp.Service.Impl;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.example.movieapp.Model.Movie;
import org.example.movieapp.Repository.MovieRepository;
import org.example.movieapp.Service.ExcelService;
import org.example.movieapp.Service.MovieImportExportService;
import org.springframework.stereotype.Service;

import java.util.List;

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
        excelService.writeHeaders(new String[]{"Id", "Název", "Popis", "Délka", "Datum vydání"});
        int startRow = 1;
        for (Movie movie : movieList) {
            Row row = excelService.getWorkBook().getSheetAt(0).createRow(startRow);
            int columnCount = 0;
            excelService.createCell(row, columnCount++, movie.getId(), null);
            excelService.createCell(row, columnCount++, movie.getName(), null);
            excelService.createCell(row, columnCount++, movie.getDescription(), null);
            excelService.createCell(row, columnCount++, movie.getLength(), null);
            excelService.createCell(row, columnCount, movie.getReleaseDate(), null);
            startRow++;
        }
        excelService.initResponse(response, "movie");
    }
}
