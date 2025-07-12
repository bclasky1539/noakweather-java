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

    private static final Logger LOGGER
            = LogManager.getLogger(WeatherCondHttpClient.class.getName());

    // Static instance for URL generation - initialized lazily for better error handling
    private static volatile NOAAUrl urlGenerator;
    private static volatile String metarDataType;
    private static volatile String tafDataType;

    /**
     * Private constructor to prevent instantiation of utility class
     */
    private WeatherCondHttpClient() {
        // Utility class - not meant to be instantiated
    }

    /**
     * Gets the NOAAUrl instance, initializing it if necessary.
     * Uses double-checked locking for thread safety.
     * 
     * @return NOAAUrl instance
     * @throws UtilsException if initialization fails
     */
    private static NOAAUrl getUrlGenerator() throws UtilsException {
        if (urlGenerator == null) {
            synchronized (WeatherCondHttpClient.class) {
                if (urlGenerator == null) {
                    try {
                        urlGenerator = new NOAAUrl();
                        // Cache the data type strings as well
                        metarDataType = Configs.getInstance().getString("MISC_METAR_M");
                        tafDataType = Configs.getInstance().getString("MISC_TAF_T");
                    } catch (Exception e) {
                        throw new UtilsException("Failed to initialize NOAAUrl: " + e.getMessage(), e);
                    }
                }
            }
        }
        return urlGenerator;
    }

    /**
     * Generates the appropriate URI based on data type and station.
     * 
     * @param station the weather station identifier
     * @param dataType the type of data to fetch (METAR or TAF)
     * @return URI for the weather data endpoint
     * @throws UtilsException if URI generation fails or data type is invalid
     */
    private static URI generateDataUri(String station, String dataType) throws UtilsException {
        NOAAUrl generator = getUrlGenerator();
        
        if (dataType.equals(metarDataType)) {
            URI uri = generator.generateMetarDataUri(station);
            if (uri == null) {
                throw new UtilsException("Failed to generate METAR URI for station: " + station);
            }
            return uri;
        } else if (dataType.equals(tafDataType)) {
            URI uri = generator.generateTafDataUri(station);
            if (uri == null) {
                throw new UtilsException("Failed to generate TAF URI for station: " + station);
            }
            return uri;
        } else {
            throw new UtilsException("Unknown data type: " + dataType + 
                ". Expected: " + metarDataType + " or " + tafDataType);
        }
    }

    /**
     *
     * @param station the weather station identifier
     * @param dataType the type of data to fetch (METAR or TAF)
     * @return METAR or TAF weather information
     * @throws noakweather.utils.UtilsException if fetching fails
     */
    public static Optional<String> fetchMetarOrTaf(String station, String dataType) throws UtilsException {
        if (station == null || station.trim().isEmpty()) {
            throw new UtilsException("Station identifier cannot be null or empty");
        }
        if (dataType == null || dataType.trim().isEmpty()) {
            throw new UtilsException("Data type cannot be null or empty");
        }

        StringBuilder weatherData = new StringBuilder();
        try {
            // Create HttpClient
            HttpClient httpClient = HttpClient.newHttpClient();

            // Determine the URL based on data type
            //URI uri = null;
            //if (dataType.equals(Configs.getInstance().getString("MISC_METAR_M"))) {
            //    uri = new NOAAUrl().generateMetarDataUri(station);
            //} else if (dataType.equals(Configs.getInstance().getString("MISC_TAF_T"))) {
            //    uri = new NOAAUrl().generateTafDataUri(station);
            //}

            URI uri = generateDataUri(station, dataType);
            
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Fetching {} data for station {} from URI: {}", 
                    dataType, station, uri);
            }
            
            //if (uri != null) {
            // Build and send GET request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri) // Set the URI
                    .GET()  // Set the HTTP method to GET
                    .timeout(Duration.ofSeconds(30)) // Set timeout
                    .build(); // Build the request

            HttpResponse<Stream<String>> response = httpClient.send(request,
                 HttpResponse.BodyHandlers.ofLines());

            // Check the status code before processing
            if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                LOGGER.error("{} {}", 
                    Configs.getInstance().getString("EXCEP_FETCH_WEATHER_DATA"),
                    response.statusCode());
                LOGGER.error("{} {}", 
                    Configs.getInstance().getString("EXCEP_FAILED_FETCH_STATION"),
                    station);
                String errMsg = String.format("%s %d", 
                    Configs.getInstance().getString("EXCEP_FETCH_WEATHER_DATA"),
                    response.statusCode());
                throw new UtilsException("fetchMetarOrTaf: " + errMsg);
            }

            // Process response body line by line using try-with-resources
            // to ensure the stream is closed
            try (Stream<String> lines = response.body()) {
                lines.forEach(line -> weatherData.append(line).append(" "));
            }
            //}
        } catch (IOException e) {
            LOGGER.error("{} {}", 
                Configs.getInstance().getString("EXCEP_FAILED_DOWNLOAD_FILE"), 
                e);
        } catch (InterruptedException e) {
            LOGGER.error("{} {}", 
                Configs.getInstance().getString("EXCEP_HTTP_REQUEST_INTER"), 
                e);
            Thread.currentThread().interrupt(); // Reset interrupted status
        } catch (NullPointerException e) {
            LOGGER.error("{} {}", 
                Configs.getInstance().getString("EXCEP_NULL_POINTER_EXCEPTION"), 
                e);
        }

        
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("{} {}", 
                Configs.getInstance().getString("MISC_WEATHER_DATA"), 
                weatherData.toString());
        }

        return Optional.of(weatherData.toString());
    }

    /**
     * Convenience method to fetch METAR data.
     * 
     * @param station the weather station identifier
     * @return METAR weather information wrapped in Optional
     * @throws UtilsException if fetching fails
     */
    public static Optional<String> fetchMetar(String station) throws UtilsException {
        return fetchMetarOrTaf(station, getMetarDataType());
    }
    
    /**
     * Convenience method to fetch TAF data.
     * 
     * @param station the weather station identifier
     * @return TAF weather information wrapped in Optional
     * @throws UtilsException if fetching fails
     */
    public static Optional<String> fetchTaf(String station) throws UtilsException {
        return fetchMetarOrTaf(station, getTafDataType());
    }
    
    /**
     * Gets the configured METAR data type identifier.
     * 
     * @return METAR data type string
     * @throws UtilsException if configuration is not available
     */
    private static String getMetarDataType() throws UtilsException {
        getUrlGenerator(); // Ensure initialization
        return metarDataType;
    }
    
    /**
     * Gets the configured TAF data type identifier.
     * 
     * @return TAF data type string
     * @throws UtilsException if configuration is not available
     */
    private static String getTafDataType() throws UtilsException {
        getUrlGenerator(); // Ensure initialization
        return tafDataType;
    }
}
