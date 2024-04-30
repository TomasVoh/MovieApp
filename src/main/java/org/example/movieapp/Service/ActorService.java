package org.example.movieapp.Service;

import org.example.movieapp.Model.Actor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ActorService extends PageReadAndWriteService<Actor> {
    List<Actor> findAll();
}
