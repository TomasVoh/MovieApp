package org.example.movieapp.Service;

import org.example.movieapp.Model.CustomRevisionEntity;

import java.util.List;

public interface AuditService {
    List<CustomRevisionEntity> findAll();
}
