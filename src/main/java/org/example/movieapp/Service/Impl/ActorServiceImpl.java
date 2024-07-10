package org.example.movieapp.Service.Impl;

import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Filters.ActorSpecificationFilter;
import org.example.movieapp.Mapper.PageMapper;
import org.example.movieapp.Model.Actor;
import org.example.movieapp.Model.Country;
import org.example.movieapp.Model.Movie;
import org.example.movieapp.Repository.ActorRepository;
import org.example.movieapp.Service.ActorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ActorServiceImpl implements ActorService{
    private ActorRepository actorRepository;
    private Logger logger = LoggerFactory.getLogger(ActorServiceImpl.class);

    public ActorServiceImpl(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public List<Actor> findAll() {
        List<Actor> actors = actorRepository.findAll();
        logger.trace("actors: {}", actors);
        return actors;
    }

    @Override
    public PageDto<Actor> findActorsWithFilter(String name, String surname, LocalDate startDate, LocalDate endDate, List<Country> countries, List<Movie> movies, int pageNum, int pageSize) {
        Specification<Actor> actorSpecification = Specification.where(name != null ? ActorSpecificationFilter.nameLike(name) : null)
                .and(surname != null ? ActorSpecificationFilter.surnameLike(surname) : null)
                .and(startDate != null && endDate != null ? ActorSpecificationFilter.birthdayInterval(startDate, endDate) : null)
                .and(countries != null ? ActorSpecificationFilter.countryEquals(countries) : null)
                .and(movies != null ? ActorSpecificationFilter.moviesEquals(movies) : null);
        Page<Actor> actorPage = actorRepository.findAll(actorSpecification, PageRequest.of(pageNum, pageSize));
        PageDto<Actor> actorPageDto = PageMapper.pageMapper(actorPage);
        logger.trace("actorFilteredPagedDto: {}", actorPageDto);
        return actorPageDto;
    }

    @Override
    public PageDto<Actor> findAllByPage(int page, int size) {
        Page<Actor> actorPage = actorRepository.findAll(PageRequest.of(page, size));
        PageDto<Actor> actorPageDto = PageMapper.pageMapper(actorPage);
        logger.trace("actorPageDto: {}", actorPageDto);
        return actorPageDto;
    }

    @Override
    public Actor findById(long id) {
        Actor actor = actorRepository.findById(id).orElseThrow(NoSuchElementException::new);
        logger.trace("actor: {}", actor);
        return actor;
    }

    @Override
    public Actor save(Actor entity) {
        Actor actor = actorRepository.save(entity);
        logger.trace("savedActor: {}", actor);
        return actor;
    }
}
