package org.example.movieapp.Service;

import org.example.movieapp.Model.Actor;
import org.example.movieapp.Model.Director;
import org.example.movieapp.Model.Genre;
import org.example.movieapp.Model.Movie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RecommendService {
    List<Movie> findRecommendedMovies(String username);
    Double findActorPriority(Movie movie, List<Actor> actors);
    Double findDirectorPriority(Movie movie, List<Director> directors);
    Double findGenrePriority(Genre genre, String username);
    List<Map.Entry<Movie, Double>> filterMovies(HashMap<Movie, Double> moviePriorities);
}
