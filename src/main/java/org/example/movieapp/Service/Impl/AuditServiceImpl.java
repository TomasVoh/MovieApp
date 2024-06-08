package org.example.movieapp.Service.Impl;

import jakarta.persistence.EntityManager;
import org.example.movieapp.Dto.RevisionDto;
import org.example.movieapp.Mapper.RevisionMapper;
import org.example.movieapp.Model.Actor;
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
    public List<RevisionDto<Movie>> findMoviesAudit() {
        List<Object[]> results = AuditReaderFactory.get(entityManager).createQuery().forRevisionsOfEntity( Movie.class, false, true).getResultList();
        return RevisionMapper.fromRevisionsToRevisionDtos(results, Movie.class);
    }

    @Override
    public List<RevisionDto<Actor>> findActorsAudit() {
        List<Object[]> results = AuditReaderFactory.get(entityManager).createQuery().forRevisionsOfEntity( Actor.class, false, true).getResultList();
        return RevisionMapper.fromRevisionsToRevisionDtos(results, Actor.class);
    }
}
