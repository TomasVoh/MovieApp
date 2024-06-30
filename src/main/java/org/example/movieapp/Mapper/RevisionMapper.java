package org.example.movieapp.Mapper;

import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Dto.RevisionDto;
import org.example.movieapp.Model.Actor;
import org.example.movieapp.Model.CustomRevisionEntity;
import org.example.movieapp.Model.Movie;
import org.hibernate.envers.RevisionType;
import org.springframework.data.domain.Page;
import org.springframework.data.history.Revision;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class RevisionMapper {
    public static <T> PageDto<RevisionDto<T>> fromRevisionsToRevisionDtos(List<Object[]> moviesAudit, Class<T> clazz, int page, int size) {
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
        List<RevisionDto<T>> data = revisionDtos.reversed().subList(page * size, (page * size + size) < revisionDtos.size() ? (page * size + size) : revisionDtos.size());
        int totalPages = revisionDtos.size() / size;
        if(revisionDtos.size() % size != 0) {
            totalPages++;
        }
        PageDto<RevisionDto<T>> pageDto = new PageDto<>();
        pageDto.setPageSize(size);
        pageDto.setPageNum(page);
        pageDto.setContent(data);
        pageDto.setTotalPages(totalPages);
        return pageDto;
    }
}
