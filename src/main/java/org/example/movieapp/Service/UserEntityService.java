package org.example.movieapp.Service;

import org.example.movieapp.Dto.RegisterDto;
import org.example.movieapp.Model.Movie;
import org.example.movieapp.Model.UserEntity;

public interface UserEntityService extends PageReadAndWriteService<UserEntity>{
    void registerUser(RegisterDto registerDto);
    UserEntity findUserByEmail(String email);
    void addToFavouriteMovie(long movieId, String userEmail);
}
