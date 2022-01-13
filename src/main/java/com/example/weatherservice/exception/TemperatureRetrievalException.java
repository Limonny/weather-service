package com.example.weatherservice.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

public class TemperatureRetrievalException extends ResponseStatusException {

    public TemperatureRetrievalException(HttpStatus status, String message) {
        super(status, message);
        ErrorAttributeOptions.of(MESSAGE);
    }
}