package org.example.movieapp.Service;

import org.example.movieapp.Dto.PageDto;

import java.util.List;

public interface PageReadAndWriteService<T> {
    PageDto<T> findAllByPage(int page, int size);
    T findById(long id);
    T save(T entity);
}
