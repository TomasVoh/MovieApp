package org.example.movieapp.Service.Impl;

import jakarta.persistence.EntityManager;
import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Dto.RevisionDto;
import org.example.movieapp.Mapper.RevisionMapper;
import org.example.movieapp.Model.Actor;
import org.example.movieapp.Model.Director;
import org.example.movieapp.Model.Movie;
import org.example.movieapp.Service.AuditService;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditServiceImpl implements AuditService {
    private EntityManager entityManager;

    public AuditServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public PageDto<RevisionDto<Movie>> findMoviesAudit(int pageNum, int pageSize) {
        List<Object[]> results = AuditReaderFactory.get(entityManager).createQuery().forRevisionsOfEntity( Movie.class, false, true).getResultList();
        return RevisionMapper.fromRevisionsToRevisionDtos(results, Movie.class, pageNum, pageSize);
    }

    @Override
    public PageDto<RevisionDto<Actor>> findActorsAudit(int pageNum, int pageSize) {
        List<Object[]> results = AuditReaderFactory.get(entityManager).createQuery().forRevisionsOfEntity( Actor.class, false, true).getResultList();
        return RevisionMapper.fromRevisionsToRevisionDtos(results, Actor.class, pageNum, pageSize);
    }

    @Override
    public PageDto<RevisionDto<Director>> findDirectorsAudit(int pageNum, int pageSize) {
        List<Object[]> results = AuditReaderFactory.get(entityManager).createQuery().forRevisionsOfEntity( Director.class, false, true).getResultList();
        return RevisionMapper.fromRevisionsToRevisionDtos(results, Director.class, pageNum, pageSize);
    }
}
