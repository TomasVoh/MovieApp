package org.example.movieapp.Service;

import java.util.List;

public interface ReadOnlyService<T> {
    List<T> findAll();
    T findById(long id);
}
