package com.svetaukiyo.TelegramBot.controller;

import com.svetaukiyo.TelegramBot.TelegramBot;
import com.svetaukiyo.TelegramBot.dto.CountryDto;
import com.svetaukiyo.TelegramBot.entity.Country;
import com.svetaukiyo.TelegramBot.service.CountryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CountryController {

    private ModelMapper modelMapper;
    private CountryService countryService;
    private final TelegramBot telegramBot;

    @Autowired
    public CountryController(ModelMapper modelMapper, CountryService countryService, TelegramBot telegramBot) {
        this.countryService = countryService;
        this.modelMapper = modelMapper;
        this.telegramBot = telegramBot;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return telegramBot.onWebhookUpdateReceived(update);
    }

    @GetMapping("/all")
    public List<CountryDto> getAll() {
        return countryService.getAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/name/{name}")
    public String getByName(@PathVariable(value = "name") String name) {
        countryService.findByName(name);
        return countryService.findByName(name);
    }

        @GetMapping("/id/{id}")
    public CountryDto getById(@PathVariable(value = "id") Long id) {
        return convertToDto(countryService.findById(id).get());
    }

    @PostMapping("/update")
    public CountryDto update(@Validated @RequestBody Country country) {
        return convertToDto(countryService.update(country));
    }

    @PostMapping("/create")
    public CountryDto create(@Validated @RequestBody Country country) {
        return convertToDto(countryService.create(country));
    }

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        countryService.delete(id);
    }

    private CountryDto convertToDto(Country country) {
        return modelMapper.map(country, CountryDto.class);
    }
}
