package com.weatherapp.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Geocoding_API {
    private static final String API_KEY = "23dcdbb187fa219b1ae591bbf5b1379d"; // Replace with your actual API key
    private static final String API_URL = "http://api.openweathermap.org/geo/1.0/direct?q=";

    public Geocoding_API(String cityName) {
        try {
            URL url = new URL(API_URL + cityName + "&appid=" + API_KEY);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                System.out.println("Response: " + response.toString());
            } else {
                System.out.println("GET request failed. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        new Geocoding_API("Miami");
    }
}
