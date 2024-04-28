package org.example.movieapp.Service.Impl;

import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Mapper.PageMapper;
import org.example.movieapp.Model.Actor;
import org.example.movieapp.Repository.ActorRepository;
import org.example.movieapp.Service.ActorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.NoSuchElementException;

public class ActorServiceImpl implements ActorService{
    private ActorRepository actorRepository;
    private Logger logger = LoggerFactory.getLogger(ActorServiceImpl.class);

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
        return null;
    }
}
