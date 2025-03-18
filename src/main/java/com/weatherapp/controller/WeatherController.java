package com.weatherapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.weatherapp.api.Geocoding_API;
import com.weatherapp.api.Weather_API;

@RestController
public class WeatherController {

    /**
     * Exposes a REST endpoint to get weather data for a given city.
     * Currently, this example simply calls the API classes that print results to the console.
     * In a full implementation, you would refactor these API classes to return data and then build
     * a JSON response.
     *
     * @param city the city name (defaults to "Miami" if not provided)
     * @return a message indicating that the request was received
     */
    @GetMapping("/weather")
    public String getWeather(@RequestParam(value = "city", defaultValue = "Miami") String city) {
        // Invoke the geocoding API for location information
        new Geocoding_API(city);

        // Invoke the weather API for weather information
        new Weather_API(city);

        // For demonstration purposes, we return a simple message.
        // In a full application, you'd build and return a structured JSON response.
        return "Weather information for " + city + " Manar is amazing.";
    }
}
