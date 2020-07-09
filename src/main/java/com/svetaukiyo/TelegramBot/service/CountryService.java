package com.svetaukiyo.TelegramBot.service;

import com.svetaukiyo.TelegramBot.entity.Country;

import java.util.List;
import java.util.Optional;

public interface CountryService {

    Optional<Country> findById(Long id);

    String findByName(String name);

    Country update(Country country);

    Country create(Country country);

    List<Country> getAll();

    void delete(Long id);
}
