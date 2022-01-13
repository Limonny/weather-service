package com.example.weatherservice.service;

import com.example.weatherservice.exception.TemperatureRetrievalException;
import com.example.weatherservice.model.WeatherHistory;
import com.example.weatherservice.repository.WeatherHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;

@Service
@AllArgsConstructor
public class WeatherService {

    private final WeatherHistoryRepository weatherHistoryRepository;

    public WeatherHistory getByDate() {
        WeatherHistory weatherHistory;
        Optional<WeatherHistory> opt;

        synchronized (this) {
            opt = weatherHistoryRepository.findById(LocalDate.now());

            if (opt.isEmpty()) {
                weatherHistory = create();
                return weatherHistory;
            }
        }

        weatherHistory = opt.get();

        return weatherHistory;
    }

    private WeatherHistory create() {
        WeatherHistory weatherHistory = new WeatherHistory();

        String currentTemperature = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("https://yandex.ru/").openStream()))) {
            String str;
            while ((str = reader.readLine()) != null) {
                if (str.contains("weather__temp")) {
                    int index = str.indexOf("weather__temp");
                    currentTemperature = str
                            .substring(index + 15, index + 18)
                            .replaceAll("[^0-9+−]", "");
                    break;
                }
            }
        } catch (IOException e) {
            throw new TemperatureRetrievalException(BAD_GATEWAY, "Error occurred during temperature read. Try again later.");
        }

        if (currentTemperature == null || !currentTemperature.matches("^[+−]?[0-9]{1,2}$")) {
            throw new TemperatureRetrievalException(BAD_GATEWAY, "Temperature retrieval error. " + currentTemperature + " is not a valid temperature.");
        }

        weatherHistory.setDate(LocalDate.now());
        weatherHistory.setTemperature(currentTemperature);

        return weatherHistoryRepository.save(weatherHistory);
    }
}