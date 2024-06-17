package org.example.movieapp.Service.Impl;

import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Dto.RegisterDto;
import org.example.movieapp.Model.*;
import org.example.movieapp.Repository.*;
import org.example.movieapp.Service.UserEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserEntityServiceImpl implements UserEntityService {
    private UserEntityRepository userEntityRepository;
    private AuthorityRepository authorityRepository;
    private ActorRepository actorRepository;
    private MovieRepository movieRepository;
    private PasswordEncoder passwordEncoder;
    private DirectorRepository directorRepository;
    private final Logger logger = LoggerFactory.getLogger(UserEntityServiceImpl.class);

    public UserEntityServiceImpl(UserEntityRepository userEntityRepository, AuthorityRepository authorityRepository, ActorRepository actorRepository, MovieRepository movieRepository, PasswordEncoder passwordEncoder, DirectorRepository directorRepository) {
        this.userEntityRepository = userEntityRepository;
        this.authorityRepository = authorityRepository;
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
        this.passwordEncoder = passwordEncoder;
        this.directorRepository = directorRepository;
    }

    @Override
    public PageDto<UserEntity> findAllByPage(int page, int size) {
        return null;
    }

    @Override
    public UserEntity findById(long id) {
        UserEntity userEntity = userEntityRepository.findById(id).orElseThrow(NoSuchElementException::new);
        logger.trace("userEntity: {} with {}", userEntity, id);
        return userEntity;
    }

    @Override
    public UserEntity save(UserEntity entity) {
        UserEntity savedUser = userEntityRepository.save(entity);
        logger.trace("savedUser: {}", savedUser);
        return savedUser;
    }

    @Override
    public void registerUser(RegisterDto registerDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(registerDto.getName());
        userEntity.setSurname(registerDto.getSurname());
        userEntity.setEmail(registerDto.getEmail());
        userEntity.setNickname(registerDto.getNickname());
        userEntity.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userEntity.setAuthorities(Arrays.asList(authorityRepository.findByName("USER")));
        save(userEntity);
    }

    @Override
    public UserEntity findUserByEmail(String email) {
        UserEntity userEntity = userEntityRepository.findFirstByEmail(email);
        logger.trace("userEntity: {}", userEntity);
        return userEntity;
    }

    @Override
    public List<Map.Entry<Genre, Double>> findUsersGenreRatio(String username) {
        int genreCount = 0;
        UserEntity user = userEntityRepository.findFirstByEmail(username);
        HashMap<Genre, Double> genreRatio = new HashMap<>();
        for(Movie movie : user.getMovies()) {
            genreCount += movie.getGenres().size();
            for(Genre genre : movie.getGenres()) {
                if(genreRatio.containsKey(genre)) {
                    genreRatio.put(genre, genreRatio.get(genre) + 1);
                } else {
                    genreRatio.put(genre, (double) 1);
                }
            }
        }
        for(Map.Entry<Genre, Double> entry : genreRatio.entrySet()) {
            entry.setValue(entry.getValue() / genreCount * 100);
        }
        List<Map.Entry<Genre, Double>> priority = new ArrayList<>(genreRatio.entrySet());
        priority.sort((firstEntry, secondEntry) -> secondEntry.getValue().compareTo(firstEntry.getValue()));
        return priority;
    }

    @Override
    public void addToFavouriteMovie(long movieId, String userEmail) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(NoSuchElementException::new);
        UserEntity userEntity = findUserByEmail(userEmail);
        userEntity.getMovies().add(movie);
        save(userEntity);
    }

    @Override
    public void removeFromFavouriteMovie(long movieId, String userEmail) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(NoSuchElementException::new);
        UserEntity userEntity = findUserByEmail(userEmail);
        userEntity.getMovies().remove(movie);
        save(userEntity);
    }

    @Override
    public void addToFavouriteActor(long actorId, String userEmail) {
        Actor actor = actorRepository.findById(actorId).orElseThrow(NoSuchElementException::new);
        UserEntity userEntity = findUserByEmail(userEmail);
        userEntity.getActors().add(actor);
        save(userEntity);
    }

    @Override
    public void removeFromFavouriteActor(long actorId, String userEmail) {
        Actor actor = actorRepository.findById(actorId).orElseThrow(NoSuchElementException::new);
        UserEntity userEntity = findUserByEmail(userEmail);
        userEntity.getActors().remove(actor);
        save(userEntity);
    }

    @Override
    public void addToFavouriteDirector(long id, String userEmail) {
        Director director1 = directorRepository.findById(id).orElseThrow();
        UserEntity userEntity = findUserByEmail(userEmail);
        userEntity.getDirectors().add(director1);
        save(userEntity);
    }

    @Override
    public void removeFromFavouriteDirector(long id, String userEmail) {
        Director director1 = directorRepository.findById(id).orElseThrow();
        UserEntity userEntity = findUserByEmail(userEmail);
        userEntity.getDirectors().remove(director1);
        save(userEntity);
    }
}
