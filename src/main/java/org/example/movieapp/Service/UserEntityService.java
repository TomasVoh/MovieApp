package org.example.movieapp.Service;

import org.example.movieapp.Dto.RegisterDto;
import org.example.movieapp.Model.Director;
import org.example.movieapp.Model.Genre;
import org.example.movieapp.Model.Movie;
import org.example.movieapp.Model.UserEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserEntityService extends PageReadAndWriteService<UserEntity>{
    void registerUser(RegisterDto registerDto);
    UserEntity findUserByEmail(String email);
    List<Map.Entry<Genre, Double>> findUsersGenreRatio(String username);
    void addToFavouriteMovie(long movieId, String userEmail);
    void removeFromFavouriteMovie(long movieId, String userEmail);
    void addToFavouriteActor(long actorId, String userEmail);
    void removeFromFavouriteActor(long actorId, String userEmail);
    void addToFavouriteDirector(long id, String userEmail);
    void removeFromFavouriteDirector(long id, String userEmail);

}
