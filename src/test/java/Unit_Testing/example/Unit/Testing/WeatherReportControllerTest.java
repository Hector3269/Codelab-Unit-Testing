package Unit_Testing.example.Unit.Testing;



import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import Unit_Testing.example.Unit.Testing.controller.WeatherReportController;
import Unit_Testing.example.Unit.Testing.data.WeatherReport;
import Unit_Testing.example.Unit.Testing.service.WeatherReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(WeatherReportController.class)
public class WeatherReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherReportService weatherReportService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new WeatherReportController(weatherReportService)).build();
    }

    @Test
    void testGetWeatherReport() throws Exception {
        WeatherReport mockReport = new WeatherReport();
        mockReport.setTemperature(25.5);
        mockReport.setHumidity(60.0);

        when(weatherReportService.getWeatherReport(anyDouble(), anyDouble())).thenReturn(mockReport);

        mockMvc.perform(get("/v1/api/weather-report")
                        .param("latitude", "37.8267")
                        .param("longitude", "-122.4233"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temperature").value(25.5))
                .andExpect(jsonPath("$.humidity").value(60.0));
    }
}