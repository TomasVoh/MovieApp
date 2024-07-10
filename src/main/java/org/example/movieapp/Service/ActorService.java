package org.example.movieapp.Service;

import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Model.Actor;
import org.example.movieapp.Model.Country;
import org.example.movieapp.Model.Movie;

import java.time.LocalDate;
import java.util.List;

public interface ActorService extends PageReadAndWriteService<Actor> {
    List<Actor> findAll();
    PageDto<Actor> findActorsWithFilter(String name, String surname, LocalDate startDate, LocalDate endDate, List<Country> countries, List<Movie> movies, int pageNum, int pageSize);

}
