package com.example.weatherservice;

import com.example.weatherservice.model.WeatherHistory;
import com.example.weatherservice.repository.WeatherHistoryRepository;
import com.example.weatherservice.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @Mock
    private WeatherHistoryRepository weatherHistoryRepository;
    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        weatherService = new WeatherService(weatherHistoryRepository);
    }

    @Test
    void getByDate_Should_return_todays_weather_when_weather_already_present() {
        WeatherHistory w = new WeatherHistory(LocalDate.now(), "−2");

        given(weatherHistoryRepository.findById(any(LocalDate.class))).willReturn(Optional.of(w));

        WeatherHistory result = weatherService.getByDate();

        assertThat(result).isEqualTo(w);
    }

    @Test
    void getByDate_Should_create_new_weather_and_return_it_when_weather_not_found() {
        WeatherHistory w = new WeatherHistory(LocalDate.now(), "−2");

        given(weatherHistoryRepository.findById(any(LocalDate.class))).willReturn(Optional.empty());
        given(weatherHistoryRepository.save(any(WeatherHistory.class))).willReturn(w);

        WeatherHistory result = weatherService.getByDate();

        assertThat(result).isEqualTo(w);
    }
}