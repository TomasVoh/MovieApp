package org.example.movieapp.Repository;

import org.example.movieapp.Model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
