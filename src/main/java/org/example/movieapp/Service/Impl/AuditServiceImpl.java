package org.example.movieapp.Service.Impl;

import org.example.movieapp.Model.CustomRevisionEntity;
import org.example.movieapp.Repository.AuditRepository;
import org.example.movieapp.Service.AuditService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditServiceImpl implements AuditService {
    private AuditRepository auditRepository;

    public AuditServiceImpl(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }
    @Override
    public List<CustomRevisionEntity> findAll() {
        return auditRepository.findAll();
    }
}
