package org.example.movieapp.Service.Impl;

import jakarta.persistence.EntityManager;
import org.example.movieapp.Dto.RevisionDto;
import org.example.movieapp.Mapper.RevisionMapper;
import org.example.movieapp.Model.CustomRevisionEntity;
import org.example.movieapp.Model.Movie;
import org.example.movieapp.Repository.AuditRepository;
import org.example.movieapp.Service.AuditService;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditServiceImpl implements AuditService {
    private AuditRepository auditRepository;
    private EntityManager entityManager;

    public AuditServiceImpl(AuditRepository auditRepository, EntityManager entityManager) {
        this.auditRepository = auditRepository;
        this.entityManager = entityManager;
    }
    @Override
    public List<RevisionDto> findAll() {
        List<CustomRevisionEntity> revisionEntities = auditRepository.findAll();
        List<Object[]> results = AuditReaderFactory.get(entityManager).createQuery().forRevisionsOfEntity( Movie.class, false, true).getResultList();
        return RevisionMapper.fromRevisionsToRevisionDtos(revisionEntities, results);
    }
}
