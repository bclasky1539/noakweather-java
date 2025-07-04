/*
 * noakweather(TM) is a Java library for parsing weather data
 * Copyright (C) 2022 quark95cos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package noakweather.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.stream.Stream;
import noakweather.utils.Configs;
import noakweather.utils.UtilsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class representing the fetching of the METAR or TAF data from NOAA
 *
 * Author: quark95cos Since: Copyright(c) 2022
 */
public class WeatherCondHttpClient {

    private static String station = "";

    private static final Logger LOGGER
            = LogManager.getLogger(WeatherCondHttpClient.class.getName());

    /**
     * !!!!!!!!!!!!!!!!!!!!! NOT IN USE - NEED TO FINALIZE
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *
     * @param station
     * @return empty string
     */
    public static String refreshWeather(String station) {
        WeatherCondHttpClient.station = station;
        System.out.println(Configs.getInstance().getString("MISC_STATION")
                + " #" + WeatherCondHttpClient.station + "#");

        // Create an URL object
        // URL url;
        return "";
    }

    /**
     *
     * @param station
     * @param dataType
     * @return metar or taf weather information
     * @throws noakweather.utils.UtilsException
     */
    public static Optional<String> fetchMetarOrTaf(String station, String dataType) throws UtilsException {
        StringBuilder weatherData = new StringBuilder();
        try {
            // Create HttpClient
            HttpClient httpClient = HttpClient.newHttpClient();

            // Determine the URL based on data type
            URI uri = null;
            if (dataType.equals(Configs.getInstance().getString("MISC_METAR_M"))) {
                uri = new NOAAUrl().generateMetarDataUri(station);
            } else if (dataType.equals(Configs.getInstance().getString("MISC_TAF_T"))) {
                uri = new NOAAUrl().generateTafDataUri(station);
            }

            if (uri != null) {
                // Build and send GET request
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(uri) // Set the URI
                        .GET()  // Set the HTTP method to GET
                        .timeout(Duration.ofSeconds(30)) // Set timeout
                        .build(); // Build the request

                // Send request and get response as a stream of lines
                HttpResponse<Stream<String>> response = httpClient.send(request,
                     HttpResponse.BodyHandlers.ofLines());

                // Check the status code before processing
                if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                    String errMsg = Configs.getInstance().getString("EXCEP_FETCH_WEATHER_DATA")
                            + " " + response.statusCode();
                    LOGGER.error(errMsg);
                    LOGGER.error(Configs.getInstance().getString("EXCEP_FAILED_FETCH_STATION")
                            + " " + station);
                    throw new UtilsException("fetchMetarOrTaf: " + errMsg);
                }

                // Process response body line by line using try-with-resources
                // to ensure the stream is closed
                try (Stream<String> lines = response.body()) {
                    lines.forEach(line -> weatherData.append(line).append(" "));
                }
            }
        } catch (IOException e) {
            LOGGER.error(Configs.getInstance().getString("EXCEP_FAILED_DOWNLOAD_FILE")
                    + " " + e);
        } catch (InterruptedException e) {
            LOGGER.error(Configs.getInstance().getString("EXCEP_HTTP_REQUEST_INTER")
                    + " " + e);
            Thread.currentThread().interrupt(); // Reset interrupted status
        } catch (NullPointerException e) {
            LOGGER.error(Configs.getInstance().getString("EXCEP_NULL_POINTER_EXCEPTION")
                    + " " + e);
        }

        LOGGER.debug(Configs.getInstance().getString("MISC_WEATHER_DATA")
                + " " + weatherData.toString());

        return Optional.of(weatherData.toString());
    }

    private WeatherCondHttpClient() {
    }
}
