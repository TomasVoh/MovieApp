package org.example.movieapp.Service.Impl;

import org.example.movieapp.Model.*;
import org.example.movieapp.Repository.MovieRepository;
import org.example.movieapp.Repository.UserEntityRepository;
import org.example.movieapp.Service.RecommendService;
import org.example.movieapp.Service.UserEntityService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecommendServiceImpl implements RecommendService {
    private UserEntityRepository userEntityRepository;
    private MovieRepository movieRepository;
    private UserEntityService userEntityService;

    public RecommendServiceImpl(UserEntityRepository userEntityRepository, MovieRepository movieRepository, UserEntityService userEntityService) {
        this.userEntityRepository = userEntityRepository;
        this.movieRepository = movieRepository;
        this.userEntityService = userEntityService;
    }

    @Override
    public List<Movie> findRecommendedMovies(String username) {
        UserEntity userEntity = userEntityRepository.findFirstByEmail(username);
        List<Movie> movies = movieRepository.findAll();
        HashMap<Movie, Double> moviePriority = new HashMap<>();
        for(Movie movie : movies) {
            double score = findActorPriority(movie, userEntity.getActors());
            score += findDirectorPriority(movie, userEntity.getDirectors());
            for(Genre genre : movie.getGenres()) {
                score += findGenrePriority(genre, username);
            }
            moviePriority.put(movie, score);
        }
        return filterMovies(moviePriority).stream().map(Map.Entry::getKey).toList();
    }

    @Override
    public Double findActorPriority(Movie movie, List<Actor> actors) {
        double score = 0;
        for (Actor actor : actors) {
            if (movie.getActors().contains(actor)) {
                score = score + 0.5;
            }
        }
        return score;
    }

    @Override
    public Double findDirectorPriority(Movie movie, List<Director> directors) {
        double score = 0;
        for (Director director : directors) {
            if (movie.getDirectors().contains(director)) {
                score = score + 1;
            }
        }
        return score;
    }

    @Override
    public Double findGenrePriority(Genre genre, String username) {
        double score = 0;
        Map<Genre, Double> genreDoubleHashMap = userEntityService.findUsersGenreRatio(username).entrySet().stream().filter(entry -> entry.getValue() > 10).collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
        if(genreDoubleHashMap.containsKey(genre)) {
            score = score + 0.2;
        }
        return score;
    }

    @Override
    public List<Map.Entry<Movie, Double>> filterMovies(HashMap<Movie, Double> moviePriorities) {
        List<Map.Entry<Movie, Double>> movies = new ArrayList<>(moviePriorities.entrySet());
        movies.sort((firstEntry, secondEntry) -> secondEntry.getValue().compareTo(firstEntry.getValue()));
        System.out.println(movies.stream().map((movie) -> movie.getKey().getName() + " " + movie.getValue()).toList());
        return movies.subList(0, 5);
    }


}
