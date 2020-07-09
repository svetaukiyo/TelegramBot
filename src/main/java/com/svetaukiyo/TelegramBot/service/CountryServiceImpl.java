package com.svetaukiyo.TelegramBot.service;

import com.svetaukiyo.TelegramBot.entity.Country;
import com.svetaukiyo.TelegramBot.exception.DataNotFoundException;
import com.svetaukiyo.TelegramBot.exception.DeleteException;
import com.svetaukiyo.TelegramBot.exception.SaveException;
import com.svetaukiyo.TelegramBot.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {


    private CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Optional<Country> findById(Long id) {
        Optional<Country> country = countryRepository.findById(id);
        if (country.isEmpty()) {
            throw new DataNotFoundException("City with ID " + id + " is doesn't exists");
        }
        return country;
    }

    @Override
    public String findByName(String name) {
        return countryRepository.findByName(name).get().getResponse();
    }

    @Override
    public Country update(Country country) {
        if (countryRepository.exists(Example.of(country))) {
            throw new SaveException("City " + country.getName() + "not found");
        }
        return countryRepository.save(country);
    }

    @Override
    public Country create(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public List<Country> getAll() {
        return countryRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new DeleteException("City with ID " + id + " not found");
        }
        countryRepository.deleteById(id);
    }
}
