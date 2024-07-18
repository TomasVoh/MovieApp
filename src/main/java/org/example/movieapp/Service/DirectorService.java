package org.example.movieapp.Service;

import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Model.Country;
import org.example.movieapp.Model.Director;
import org.example.movieapp.Model.Movie;

import java.time.LocalDate;
import java.util.List;

public interface DirectorService extends PageReadAndWriteService<Director>{
    List<Director> findAll();
    PageDto<Director> findDirectorsWithFilter(String name, String surname, LocalDate startDate, LocalDate endDate, List<Movie> movies, List<Country> countries, int pageNum, int pageSize);
}
