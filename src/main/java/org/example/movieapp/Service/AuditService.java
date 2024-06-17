package org.example.movieapp.Service;

import org.example.movieapp.Dto.RevisionDto;
import org.example.movieapp.Model.Actor;
import org.example.movieapp.Model.CustomRevisionEntity;
import org.example.movieapp.Model.Director;
import org.example.movieapp.Model.Movie;

import java.util.List;

public interface AuditService {
    List<RevisionDto<Movie>> findMoviesAudit();
    List<RevisionDto<Actor>> findActorsAudit();
    List<RevisionDto<Director>> findDirectorsAudit();
}
