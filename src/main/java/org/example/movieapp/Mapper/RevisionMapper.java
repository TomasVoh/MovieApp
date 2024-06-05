package org.example.movieapp.Mapper;

import org.example.movieapp.Dto.RevisionDto;
import org.example.movieapp.Model.CustomRevisionEntity;
import org.example.movieapp.Model.Movie;
import org.hibernate.envers.RevisionType;
import org.springframework.data.history.Revision;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class RevisionMapper {
    public static List<RevisionDto> fromRevisionsToRevisionDtos(List<CustomRevisionEntity> revisions, List<Object[]> moviesAudit) {
        List<RevisionDto> revisionDtos = new ArrayList<>();
        for (int i = 0; i < revisions.size(); i++) {
            CustomRevisionEntity revision = revisions.get(i);
            RevisionDto revisionDto = new RevisionDto();
            Object[] revisionAud = moviesAudit.get(i);
            Movie movie = (Movie) revisionAud[0];
            revisionDto.setUsername(revision.getUsername());
            revisionDto.setTimestamp(LocalDateTime.ofInstant(Instant.ofEpochMilli(revision.getTimestamp()), ZoneId.of("CET")));
            revisionDto.setRevType((RevisionType) revisionAud[2]);
            revisionDto.setEntityId(movie.getId());
            revisionDtos.add(revisionDto);
        }
        return revisionDtos;
    }
}
