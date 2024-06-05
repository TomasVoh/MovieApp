package org.example.movieapp.Repository;

import org.example.movieapp.Dto.RevisionDto;
import org.example.movieapp.Model.CustomRevisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuditRepository extends JpaRepository<CustomRevisionEntity, Long> {

}
