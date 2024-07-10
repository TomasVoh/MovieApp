package org.example.movieapp.Service.Impl;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.movieapp.Model.Actor;
import org.example.movieapp.Model.Country;
import org.example.movieapp.Model.Movie;
import org.example.movieapp.Repository.*;
import org.example.movieapp.Service.ExcelService;
import org.example.movieapp.Service.MovieImportExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class MovieImportExportServiceImpl implements MovieImportExportService {
    private ExcelService excelService;
    private MovieRepository movieRepository;
    private GenreRepository genreRepository;
    private ActorRepository actorRepository;
    private CountryRepository countryRepository;
    private DirectorRepository directorRepository;
    private Logger logger = LoggerFactory.getLogger(MovieImportExportServiceImpl.class);

    public MovieImportExportServiceImpl(ExcelService excelService, MovieRepository movieRepository, GenreRepository genreRepository, ActorRepository actorRepository, CountryRepository countryRepository, DirectorRepository directorRepository) {
        this.excelService = excelService;
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.actorRepository = actorRepository;
        this.countryRepository = countryRepository;
        this.directorRepository = directorRepository;
    }

    @Override
    public void exportToExcel(HttpServletResponse response) {
        List<Movie> movieList = movieRepository.findAll();
        Sheet sheet = excelService.createWorkbook("filmy");
        excelService.writeHeaders(new String[]{"Id", "Název", "Popis", "Délka", "Datum vydání", "Cesta k obrázku", "Žánry", "Herci", "Země", "Režisér/i"});
        int startRow = 1;
        for (Movie movie : movieList) {
            Row row = sheet.createRow(startRow);
            int columnCount = 0;
            excelService.createCell(row, columnCount++, movie.getId(), null);
            excelService.createCell(row, columnCount++, movie.getName(), null);
            excelService.createCell(row, columnCount++, movie.getDescription(), null);
            excelService.createCell(row, columnCount++, movie.getLength(), null);
            excelService.createCell(row, columnCount++, movie.getReleaseDate(), null);
            excelService.createCell(row, columnCount++, movie.getImagePath(), null);
            excelService.createCell(row, columnCount++, movie.getGenres().stream().map(genre -> genre.getId().toString()).toList(), null);
            excelService.createCell(row, columnCount++, movie.getActors().stream().map((actor) -> actor.getId().toString()).toList(), null);
            excelService.createCell(row, columnCount++, movie.getCountries().stream().map((country) -> country.getId().toString()).toList(), null);
            excelService.createCell(row, columnCount, movie.getDirectors().stream().map((director) -> director.getId().toString()).toList(), null);
            startRow++;
        }
        excelService.initResponse(response, "movie");
    }

    @Override
    public void importFromExcel(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            DataFormatter dataFormatter = new DataFormatter();
            XSSFSheet sheet = workbook.getSheetAt(0);
            System.out.println(sheet.getLastRowNum());
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if(row == null) {
                    return;
                }
                logger.info(String.valueOf(i));
                Movie movie = new Movie();
                movie.setName(row.getCell(1).getStringCellValue());
                movie.setDescription(row.getCell(2).getStringCellValue());
                movie.setLength((int) row.getCell(3).getNumericCellValue());
                movie.setReleaseDate(row.getCell(4).getLocalDateTimeCellValue().toLocalDate());
                movie.setImagePath(row.getCell(5).getStringCellValue());
                movie.setGenres(Arrays.stream(row.getCell(6).getStringCellValue().split(", ")).map((id) -> genreRepository.findById(Long.parseLong(id)).orElseThrow(NoSuchElementException::new)).collect(Collectors.toList()));
                movie.setActors(Arrays.stream(row.getCell(7).getStringCellValue().split(", ")).map((id) -> {
                    Actor actor = actorRepository.findById(Long.parseLong(id)).orElseThrow(NoSuchElementException::new);
                    return actor;
                }).collect(Collectors.toList()));
                movie.setCountries(Arrays.stream(dataFormatter.formatCellValue(row.getCell(8)).split(", ")).map((id) -> {
                    Country country = countryRepository.findById(Long.parseLong(id)).orElseThrow(NoSuchElementException::new);
                    return country;
                }).collect(Collectors.toList()));
                movie.setDirectors(Arrays.stream(dataFormatter.formatCellValue(row.getCell(9)).split(", ")).map((id) -> directorRepository.findById(Long.parseLong(id)).orElseThrow(NoSuchElementException::new)).collect(Collectors.toList()));
                if(row.getCell(0) != null) {
                    Movie oldMovie = movieRepository.findById((long)row.getCell(0).getNumericCellValue()).orElse(null);
                    if(oldMovie != null) {
                        if(oldMovie.equals(movie)) {
                            logger.info("žádná editace");
                            return;
                        } else {
                            logger.info("editace");
                            movie.setId(oldMovie.getId());
                        }
                    }
                }
                movieRepository.save(movie);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
