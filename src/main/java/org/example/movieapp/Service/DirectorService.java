package org.example.movieapp.Service;

import org.example.movieapp.Model.Director;

import java.util.List;

public interface DirectorService extends PageReadAndWriteService<Director>{
    List<Director> findAll();
}
