package org.example;/*
 * Author Name: Rohit
 * Date: 03-08-2023
 * Created With: IntelliJ IDEA Community Edition
 */
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class WeatherAPIProgram {

    private static final String API_BASE_URL = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";

    public static void main(String[] args) {
        WeatherAPIProgram program = new WeatherAPIProgram();
        program.start();
    }

    private void start() {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            printMenu();
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    getWeatherDetails();
                    break;
                case 2:
                    getWindSpeedDetails();
                    break;
                case 3:
                    getPressureDetails();
                    break;
                case 0:
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }

        } while (option != 0);

        scanner.close();
    }

    private void printMenu() {
        System.out.println("1. Get weather");
        System.out.println("2. Get Wind Speed");
        System.out.println("3. Get Pressure");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private void getWeatherDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the date (YYYY-MM-DD HH:mm:ss): ");
        String date = scanner.nextLine();
        String url = API_BASE_URL;
        String response = callAPI(url);
        printWeatherData(date, response);
    }

    private void getWindSpeedDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the date (YYYY-MM-DD HH:mm:ss): ");
        String date = scanner.nextLine();
        String url = API_BASE_URL;
        String response = callAPI(url);
        printWindSpeedData(date, response);
    }

    private void getPressureDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the date (YYYY-MM-DD HH:mm:ss): ");
        String date = scanner.nextLine();
        String url = API_BASE_URL;
        String response = callAPI(url);
        printPressureData(date, response);
    }

    private String callAPI(String url) {
        StringBuilder response = new StringBuilder();
        try {
            URL apiURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            } else {
                response.append("Error: ").append(responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            response.append("Error: ").append(e.getMessage());
        }
        return response.toString();
    }

    private void printWeatherData(String date, String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray forecastData = jsonResponse.getJSONArray("list");
            SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat userDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date targetDate = userDateFormat.parse(date);
            for (int i = 0; i < forecastData.length(); i++) {
                JSONObject forecast = forecastData.getJSONObject(i);
                Date forecastDate = apiDateFormat.parse(forecast.getString("dt_txt"));

                if (targetDate.compareTo(forecastDate) == 0) {
                    JSONObject mainData = forecast.getJSONObject("main");
                    double temperature = mainData.getDouble("temp");
                    System.out.println("Temperature on " + date + ": " + temperature + " degrees Celsius");
                    return;
                }
            }

            System.out.println("Data not available for the specified date.");
        } catch (JSONException | java.text.ParseException e) {
            System.out.println("Error occurred while parsing the JSON response.");
        }
    }

    private void printWindSpeedData(String date, String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray forecastData = jsonResponse.getJSONArray("list");
            SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat userDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date targetDate = userDateFormat.parse(date);
            for (int i = 0; i < forecastData.length(); i++) {
                JSONObject forecast = forecastData.getJSONObject(i);
                Date forecastDate = apiDateFormat.parse(forecast.getString("dt_txt"));

                if (targetDate.compareTo(forecastDate) == 0) {
                    JSONObject windData = forecast.getJSONObject("wind");
                    double windSpeed = windData.getDouble("speed");
                    System.out.println("Wind Speed on " + date + ": " + windSpeed + " m/s");
                    return;
                }
            }

            System.out.println("Data not available for the specified date.");
        } catch (JSONException | java.text.ParseException e) {
            System.out.println("Error occurred while parsing the JSON response.");
        }
    }

    private void printPressureData(String date, String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray forecastData = jsonResponse.getJSONArray("list");
            SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat userDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date targetDate = userDateFormat.parse(date);
            for (int i = 0; i < forecastData.length(); i++) {
                JSONObject forecast = forecastData.getJSONObject(i);
                Date forecastDate = apiDateFormat.parse(forecast.getString("dt_txt"));

                if (targetDate.compareTo(forecastDate) == 0) {
                    JSONObject mainData = forecast.getJSONObject("main");
                    double pressure = mainData.getDouble("pressure");
                    System.out.println("Pressure on " + date + ": " + pressure + " hPa");
                    return;
                }
            }

            System.out.println("Data not available for the specified date.");
        } catch (JSONException | java.text.ParseException e) {
            System.out.println("Error occurred while parsing the JSON response.");
        }
    }
}

