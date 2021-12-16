package com.example.weatherservice.repository;

import com.example.weatherservice.model.WeatherHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface WeatherHistoryRepository extends JpaRepository<WeatherHistory, LocalDate> {
}