package org.example.movieapp.Repository;

import org.example.movieapp.Model.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director, Long> {
}
