package com.example.weatherservice;

import com.example.weatherservice.model.WeatherHistory;
import com.example.weatherservice.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    WeatherService weatherService;

    @Test
    void getTemperature() throws Exception {
        WeatherHistory w = new WeatherHistory(LocalDate.now(), "−2");

        given(weatherService.getByDate()).willReturn(w);

        String contentAsString = mockMvc.perform(get("/weather"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(contentAsString).isEqualTo("−2");
    }
}