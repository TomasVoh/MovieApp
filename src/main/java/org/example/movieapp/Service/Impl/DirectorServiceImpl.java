package org.example.movieapp.Service.Impl;

import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Mapper.PageMapper;
import org.example.movieapp.Model.Director;
import org.example.movieapp.Repository.DirectorRepository;
import org.example.movieapp.Service.DirectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DirectorServiceImpl implements DirectorService {
    private DirectorRepository directorRepository;
    private Logger logger = LoggerFactory.getLogger(DirectorServiceImpl.class);

    public DirectorServiceImpl(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @Override
    public PageDto<Director> findAllByPage(int page, int size) {
        Page<Director> directorPage = directorRepository.findAll(PageRequest.of(page, size));
        PageDto<Director> pageDirectorDto = PageMapper.pageMapper(directorPage);
        logger.trace("pageDirectorDto: {}", pageDirectorDto);
        return pageDirectorDto;
    }

    @Override
    public Director findById(long id) {
        Director director = directorRepository.findById(id).orElseThrow(NoSuchElementException::new);
        logger.trace("director: {}", director);
        return director;
    }

    @Override
    public Director save(Director entity) {
        Director savedDirector = directorRepository.save(entity);
        logger.trace("savedDirector: {}", savedDirector);
        return savedDirector;
    }
}
