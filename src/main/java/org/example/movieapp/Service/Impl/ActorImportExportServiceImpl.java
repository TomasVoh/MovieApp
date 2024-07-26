package org.example.movieapp.Service.Impl;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.movieapp.Model.Actor;
import org.example.movieapp.Model.Country;
import org.example.movieapp.Model.Movie;
import org.example.movieapp.Repository.ActorRepository;
import org.example.movieapp.Repository.CountryRepository;
import org.example.movieapp.Repository.GenreRepository;
import org.example.movieapp.Repository.MovieRepository;
import org.example.movieapp.Service.ActorImportExportService;
import org.example.movieapp.Service.ExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ActorImportExportServiceImpl implements ActorImportExportService {
    private final CountryRepository countryRepository;
    private ActorRepository actorRepository;
    private MovieRepository movieRepository;
    private ExcelService excelService;
    private Logger logger = LoggerFactory.getLogger(ActorImportExportService.class);

    public ActorImportExportServiceImpl(ActorRepository actorRepository, MovieRepository movieRepository, ExcelService excelService, CountryRepository countryRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
        this.excelService = excelService;
        this.countryRepository = countryRepository;
    }

    @Override
    public void exportToExcel(HttpServletResponse response) {
        List<Actor> actorList = actorRepository.findAll();
        Sheet sheet = excelService.createWorkbook("herci");
        excelService.writeHeaders(new String[]{"ID", "Jméno", "Přijmení", "Datum narození","Obrázek", "Země", "Filmy"});
        int rowCount = 1;
        for (Actor actor : actorList) {
            int columnCount = 0;
            Row row = sheet.createRow(rowCount);
            excelService.createCell(row, columnCount++, actor.getId(), null);
            excelService.createCell(row, columnCount++, actor.getName(), null);
            excelService.createCell(row, columnCount++, actor.getSurname(), null);
            excelService.createCell(row, columnCount++, actor.getBirthday(), null);
            excelService.createCell(row, columnCount++, actor.getImagePath(), null);
            excelService.createCell(row, columnCount++, actor.getCountries().stream().map((country -> country.getId())).collect(Collectors.toList()), null);
            excelService.createCell(row, columnCount++, actor.getMovies().stream().map(movie -> movie.getId()).collect(Collectors.toList()), null);
            rowCount++;
        }
        excelService.initResponse(response, "actors");
    }

    @Override
    public void importFromExcel(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    return;
                }
                Actor actor = new Actor();
                actor.setName(row.getCell(1).getStringCellValue());
                actor.setSurname(row.getCell(2).getStringCellValue());
                actor.setBirthday(row.getCell(3).getLocalDateTimeCellValue().toLocalDate());
                actor.setImagePath(row.getCell(4).getStringCellValue());
                actor.setCountries(Arrays.stream(row.getCell(5).getStringCellValue().split(", ")).map(id -> {
                    Country country = countryRepository.findById(Long.parseLong(id)).orElseThrow(NoSuchElementException::new);
                    return country;
                }).collect(Collectors.toList()));
                actor.setMovies(Arrays.stream(row.getCell(6).getStringCellValue().split(", ")).map(id -> {
                    Movie movie = movieRepository.findById(Long.parseLong(id)).orElseThrow(NoSuchElementException::new);
                    return movie;
                }).collect(Collectors.toList()));
                if(row.getCell(0) != null) {
                    Actor oldActor = actorRepository.findById((long) row.getCell(0).getNumericCellValue()).orElseThrow(NoSuchElementException::new);
                    if(oldActor.equals(actor)) {
                        continue;
                    } else {
                        actor.setId(oldActor.getId());
                    }
                }
                actorRepository.save(actor);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
