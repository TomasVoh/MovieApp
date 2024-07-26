package org.example.movieapp.Service.Impl;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.movieapp.Model.Country;
import org.example.movieapp.Model.Director;
import org.example.movieapp.Model.Movie;
import org.example.movieapp.Repository.CountryRepository;
import org.example.movieapp.Repository.DirectorRepository;
import org.example.movieapp.Repository.MovieRepository;
import org.example.movieapp.Service.DirectorImportExportService;
import org.example.movieapp.Service.ExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class DirectorImportExportServiceImpl implements DirectorImportExportService {
    private ExcelService excelService;
    private DirectorRepository directorRepository;
    private MovieRepository movieRepository;
    private Logger logger = LoggerFactory.getLogger(DirectorImportExportServiceImpl.class);
    private CountryRepository countryRepository;

    public DirectorImportExportServiceImpl(ExcelService excelService, DirectorRepository directorRepository, CountryRepository countryRepository, MovieRepository movieRepository) {
        this.excelService = excelService;
        this.directorRepository = directorRepository;
        this.countryRepository = countryRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public void exportToExcel(HttpServletResponse response) {
        List<Director> directors = directorRepository.findAll();
        Sheet sheet = excelService.createWorkbook("režiséři");
        excelService.writeHeaders(new String[]{"ID", "Jméno", "Příjmení", "Datum narození", "Obrázek", "Země", "Filmy"});
        int rowCount = 1;
        for(Director director : directors) {
            Row row = sheet.createRow(rowCount);
            int columnCount = 0;
            excelService.createCell(row, columnCount++, director.getId(), null);
            excelService.createCell(row, columnCount++, director.getName(), null);
            excelService.createCell(row, columnCount++, director.getSurname(), null);
            excelService.createCell(row, columnCount++, director.getDateOfBirth(), null);
            excelService.createCell(row, columnCount++, director.getImagePath(), null);
            excelService.createCell(row, columnCount++, director.getCountries().stream().map(Country::getId).collect(Collectors.toList()), null);
            excelService.createCell(row, columnCount++, director.getMovies().stream().map(Movie::getId).collect(Collectors.toList()), null);
            rowCount++;
        }
        excelService.initResponse(response, "directors");
    }

    @Override
    public void importFromExcel(MultipartFile file) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if(row == null) {
                    return;
                }
                Director director = new Director();
                director.setName(row.getCell(1).getStringCellValue());
                director.setSurname(row.getCell(2).getStringCellValue());
                director.setDateOfBirth(row.getCell(3).getLocalDateTimeCellValue().toLocalDate());
                director.setImagePath(row.getCell(4).getStringCellValue());
                director.setCountries(Arrays.stream(row.getCell(5).getStringCellValue().split(", ")).map(id -> countryRepository.findById(Long.parseLong(id)).orElseThrow(NoSuchElementException::new)).collect(Collectors.toList()));
                director.setMovies(Arrays.stream(row.getCell(6).getStringCellValue().split(", ")).map(id -> movieRepository.findById(Long.parseLong(id)).orElseThrow(NoSuchElementException::new)).collect(Collectors.toList()));
                if(row.getCell(0) != null) {
                    Director oldDirector = directorRepository.findById((long) row.getCell(0).getNumericCellValue()).orElseThrow(NoSuchElementException::new);
                    if(oldDirector.equals(director)) {
                        continue;
                    } else {
                        director.setId(oldDirector.getId());
                    }
                }
                logger.info("ahoj");
                directorRepository.save(director);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
