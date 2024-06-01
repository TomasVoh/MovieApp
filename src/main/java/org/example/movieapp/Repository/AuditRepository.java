package org.example.movieapp.Repository;

import org.example.movieapp.Model.CustomRevisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<CustomRevisionEntity, Long> {
}
