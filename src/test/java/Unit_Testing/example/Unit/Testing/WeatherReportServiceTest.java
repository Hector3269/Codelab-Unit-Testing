package Unit_Testing.example.Unit.Testing;

import Unit_Testing.example.Unit.Testing.data.WeatherApiResponse;
import Unit_Testing.example.Unit.Testing.data.WeatherReport;
import Unit_Testing.example.Unit.Testing.service.WeatherReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WeatherReportServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherReportService weatherReportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWeatherReport() {
        // Datos de prueba
        double latitude = 10.0;
        double longitude = 20.0;

        // Crear una respuesta simulada de la API
        WeatherApiResponse response = new WeatherApiResponse();
        WeatherApiResponse.Main main = new WeatherApiResponse.Main();
        main.setTemperature(25.0);
        main.setHumidity(60.0);
        response.setMain(main);

        // Configurar el mock para devolver la respuesta simulada
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=aeb07a35fc7d2fc788ab33e2211664a9";
        when(restTemplate.getForObject(url, WeatherApiResponse.class)).thenReturn(response);

        // Llamar al método que se está probando
        WeatherReport report = weatherReportService.getWeatherReport(latitude, longitude);

        assertEquals(25.0, report.getTemperature());
        assertEquals(60.0, report.getHumidity());

        verify(restTemplate, times(1)).getForObject(url, WeatherApiResponse.class);
    }
}