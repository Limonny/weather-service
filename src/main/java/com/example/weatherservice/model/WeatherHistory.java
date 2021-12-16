package com.example.weatherservice.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "weather_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WeatherHistory {

    @Id
    @Column(name = "weather_date")
    private LocalDate date;

    @Column(name = "weather_value", nullable = false)
    private String temperature;
}