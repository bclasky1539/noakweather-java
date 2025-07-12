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

import java.net.URI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import noakweather.utils.Configs;

/**
 * Class representing the NOAA URL to fetch the METAR or TAF data from NOAA
 *
 * Author: quark95cos Since: Copyright(c) 2022
 */
public class NOAAUrl {
    private static final Logger LOGGER
            = LogManager.getLogger(NOAAUrl.class.getName());
    
    private final String metarUrl;
    private final String tafUrl;
    private final String metarExt;
    private final String tafExt;
    private final String metarLogMessage;
    private final String tafLogMessage;
    private final String errorLogMessage;
    
    /**
     * Default constructor using system configuration.
     * This is the primary constructor for production use.
     */
    public NOAAUrl() {
        this(Configs.getInstance());
    }
    
    /**
     * Constructor with explicit configuration dependency.
     * Package-private for testing purposes.
     * 
     * @param configs Configuration provider
     * @throws IllegalArgumentException if configs is null or missing required keys
     */
    NOAAUrl(Configs configs) {
        if (configs == null) {
            throw new IllegalArgumentException("Configs cannot be null");
        }
        
        try {
            this.metarUrl = configs.getString("MISC_METAR_URL");
            this.tafUrl = configs.getString("MISC_TAF_URL");
            this.metarExt = configs.getString("MISC_METAR_EXT");
            this.tafExt = configs.getString("MISC_TAF_EXT");
            this.metarLogMessage = configs.getString("NOAA_URL_DECODED_GEN_METAR_URL");
            this.tafLogMessage = configs.getString("NOAA_URL_DECODED_GEN_TAF_URL");
            this.errorLogMessage = configs.getString("NOAA_URL_DECODED_GEN_MT_ERR_URL");
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to load required configuration", e);
        }
        
        // Validate required configuration values
        validateConfig();
    }
    
    /**
     * Validates that all required configuration values are present and non-empty.
     * 
     * @throws IllegalArgumentException if any required config is missing or empty
     */
    private void validateConfig() {
        if (isEmpty(metarUrl)) {
            throw new IllegalArgumentException("MISC_METAR_URL configuration is required");
        }
        if (isEmpty(tafUrl)) {
            throw new IllegalArgumentException("MISC_TAF_URL configuration is required");
        }
        if (isEmpty(metarExt)) {
            throw new IllegalArgumentException("MISC_METAR_EXT configuration is required");
        }
        if (isEmpty(tafExt)) {
            throw new IllegalArgumentException("MISC_TAF_EXT configuration is required");
        }
    }
    
    /**
     * Helper method to check if a string is null or empty.
     */
    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
    
    /**
     * Generate a URI for METAR weather data for the specified station.
     *
     * @param station Weather station identifier (e.g., "KJFK")
     * @return URI for METAR data, or null if generation fails
     * @throws IllegalArgumentException if station is null or empty
     */
    public URI generateMetarDataUri(String station) {
        if (isEmpty(station)) {
            throw new IllegalArgumentException("Station identifier cannot be null or empty");
        }
        
        URI uri = null;
        try {
            uri = URI.create(metarUrl + station + metarExt);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("{} {}", metarLogMessage, uri);
            }
        } catch (IllegalArgumentException err) {
            LOGGER.error("{} {}", errorLogMessage, err);
        }
        return uri;
    }
    
    /**
     * Generate a URI for TAF weather data for the specified station.
     *
     * @param station Weather station identifier (e.g., "KJFK")
     * @return URI for TAF data, or null if generation fails
     * @throws IllegalArgumentException if station is null or empty
     */
    public URI generateTafDataUri(String station) {
        if (isEmpty(station)) {
            throw new IllegalArgumentException("Station identifier cannot be null or empty");
        }
        
        URI uri = null;
        try {
            uri = URI.create(tafUrl + station + tafExt);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("{} {}", tafLogMessage, uri);
            }
        } catch (IllegalArgumentException err) {
            LOGGER.error("{} {}", errorLogMessage, err);
        }
        return uri;
    }
}
