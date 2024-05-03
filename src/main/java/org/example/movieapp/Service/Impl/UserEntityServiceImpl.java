package org.example.movieapp.Service.Impl;

import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Dto.RegisterDto;
import org.example.movieapp.Model.UserEntity;
import org.example.movieapp.Repository.AuthorityRepository;
import org.example.movieapp.Repository.UserEntityRepository;
import org.example.movieapp.Service.UserEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserEntityServiceImpl implements UserEntityService {
    private UserEntityRepository userEntityRepository;
    private AuthorityRepository authorityRepository;
    private PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(UserEntityServiceImpl.class);

    public UserEntityServiceImpl(UserEntityRepository userEntityRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public PageDto<UserEntity> findAllByPage(int page, int size) {
        return null;
    }

    @Override
    public UserEntity findById(long id) {
        return null;
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
}
