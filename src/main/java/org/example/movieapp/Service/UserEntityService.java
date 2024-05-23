package org.example.movieapp.Service;

import org.example.movieapp.Dto.RegisterDto;
import org.example.movieapp.Model.Movie;
import org.example.movieapp.Model.UserEntity;

public interface UserEntityService extends PageReadAndWriteService<UserEntity>{
    void registerUser(RegisterDto registerDto);
    UserEntity findUserByEmail(String email);
    void addToFavouriteMovie(long movieId, String userEmail);
    void removeFromFavouriteMovie(long movieId, String userEmail);
    void addToFavouriteActor(long actorId, String userEmail);
    void removeFromFavouriteActor(long actorId, String userEmail);
}
