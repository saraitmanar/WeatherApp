package com.weatherapp.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Weather_API {
    private static final String API_KEY = System.getenv("23dcdbb187fa219b1ae591bbf5b1379d"); // Fetching API key from environment variables
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    public Weather_API(String cityName) {
        try {
            // Create the complete URL with query parameters
            String urlString = createRequestUrl(cityName);

            // Send the HTTP request and get the response
            String response = sendRequest(urlString);

            // Parse the response and extract the weather data
            if (response != null) {
                parseAndPrintWeatherData(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String createRequestUrl(String cityName) {
        return BASE_URL + "?q=" + cityName + "&appid=" + API_KEY + "&units=metric"; // units=metric for Celsius
    }

    private String sendRequest(String urlString) throws Exception {
        // Send HTTP GET request
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
            return response.toString();
        } else {
            System.out.println("GET request failed. Response Code: " + responseCode);
            return null;
        }
    }

    private void parseAndPrintWeatherData(String response) throws Exception {
        // Parse the JSON response to extract weather data
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);

        // Extracting data
        String cityName = rootNode.get("name").asText();
        String weatherCondition = rootNode.get("weather").get(0).get("description").asText();
        double temperature = rootNode.get("main").get("temp").asDouble();
        double humidity = rootNode.get("main").get("humidity").asDouble();

        // Print the extracted weather information
        System.out.println("Weather in " + cityName + ": " + weatherCondition);
        System.out.println("Temperature: " + temperature + "°C");
        System.out.println("Humidity: " + humidity + "%");
    }

    public static void main(String[] args) {
        new Weather_API("Miami"); // Call the Weather_API for the city of your choice
    }
}
