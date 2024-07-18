package org.example.movieapp.Service.Impl;

import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Filters.DirectorSpecificationFilter;
import org.example.movieapp.Mapper.PageMapper;
import org.example.movieapp.Model.Country;
import org.example.movieapp.Model.Director;
import org.example.movieapp.Model.Movie;
import org.example.movieapp.Repository.DirectorRepository;
import org.example.movieapp.Service.DirectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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

    @Override
    public List<Director> findAll() {
        return directorRepository.findAll();
    }

    @Override
    public PageDto<Director> findDirectorsWithFilter(String name, String surname, LocalDate startDate, LocalDate endDate, List<Movie> movies, List<Country> countries, int pageNum, int pageSize) {
        Specification<Director> directorSpecification = Specification.where(name != null ? DirectorSpecificationFilter.nameLike(name) : null)
                .and(surname != null ? DirectorSpecificationFilter.surnameLike(surname) : null)
                .and(startDate != null && endDate != null ? DirectorSpecificationFilter.birthdayInterval(startDate, endDate) : null)
                .and(movies != null ? DirectorSpecificationFilter.movieEquals(movies) : null)
                .and(countries != null ? DirectorSpecificationFilter.countryEquals(countries) : null);
        Page<Director> directorPage = directorRepository.findAll(directorSpecification, PageRequest.of(pageNum, pageSize));
        PageDto<Director> pageDirectorDto = PageMapper.pageMapper(directorPage);
        logger.trace("pageFilteredDto: {}", pageDirectorDto);
        return pageDirectorDto;
    }
}
