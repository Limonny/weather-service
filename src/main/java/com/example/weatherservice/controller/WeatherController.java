package com.example.weatherservice.controller;

import com.example.weatherservice.model.WeatherHistory;
import com.example.weatherservice.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
@AllArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping
    public ResponseEntity<WeatherHistory> getTemperature() {
        WeatherHistory weatherHistory = weatherService.getByDate();

        return new ResponseEntity<>(weatherHistory, HttpStatus.OK);
    }
}