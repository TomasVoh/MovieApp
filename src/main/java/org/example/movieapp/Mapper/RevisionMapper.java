package org.example.movieapp.Mapper;

import org.example.movieapp.Dto.RevisionDto;
import org.example.movieapp.Model.Actor;
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
    public static <T> List<RevisionDto<T>> fromRevisionsToRevisionDtos(List<Object[]> moviesAudit, Class<T> clazz) {
        List<RevisionDto<T>> revisionDtos = new ArrayList<>();
        for (int i = 0; i < moviesAudit.size(); i++) {
            Object[] revisionAud = moviesAudit.get(i);
            RevisionDto<T> revisionDto = new RevisionDto<T>();
            revisionDto.setEntity((T) revisionAud[0]);
            System.out.println(revisionAud[1].toString());
            CustomRevisionEntity revision = (CustomRevisionEntity) revisionAud[1];
            revisionDto.setUsername(revision.getUsername());
            revisionDto.setTimestamp(LocalDateTime.ofInstant(Instant.ofEpochMilli(revision.getTimestamp()), ZoneId.of("CET")));
            revisionDto.setRevType((RevisionType) revisionAud[2]);
            revisionDtos.add(revisionDto);
        }
        return revisionDtos.reversed();
    }
}
