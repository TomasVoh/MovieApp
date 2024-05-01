package org.example.movieapp.Service.Impl;

import org.example.movieapp.Model.Country;
import org.example.movieapp.Repository.CountryRepository;
import org.example.movieapp.Service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CountryServiceImpl implements CountryService {
    private CountryRepository countryRepository;
    private Logger logger = LoggerFactory.getLogger(CountryServiceImpl.class);

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> findAll() {
        List<Country> countries = countryRepository.findAll();
        logger.trace("countries: {}", countries);
        return countries;
    }

    @Override
    public Country findById(long id) {
        Country country = countryRepository.findById(id).orElseThrow(NoSuchElementException::new);
        logger.trace("country: {}", country);
        return country;
    }
}
