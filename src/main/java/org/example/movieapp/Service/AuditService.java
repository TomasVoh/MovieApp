package org.example.movieapp.Service;

import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Dto.RevisionDto;
import org.example.movieapp.Model.Actor;
import org.example.movieapp.Model.CustomRevisionEntity;
import org.example.movieapp.Model.Director;
import org.example.movieapp.Model.Movie;

import java.util.List;

public interface AuditService {
    PageDto<RevisionDto<Movie>> findMoviesAudit(int pageNum, int pageSize);
    PageDto<RevisionDto<Actor>> findActorsAudit(int pageNum, int pageSize);
    PageDto<RevisionDto<Director>> findDirectorsAudit(int pageNum, int pageSize);
}
