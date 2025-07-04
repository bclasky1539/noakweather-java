/*
 * noakweather(TM) is a Java library for parsing weather data
 * Copyright (C) 2025 quark95cos
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
package noakweather.noaa_api.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import noakweather.utils.UtilsException;

/**
 * Class representing the weather section the retrieves either the METAR or TAF
 * report from a file
 *
 * Author: quark95cos Since: Copyright(c) 2025
 */
public class WeatherDataFile {
    private final List<WeatherRecord> records;
    private final String primaryStationCode;
    
    /**
     * Static factory method to create WeatherDataFile from file path
     *
     * @param filePath
     * @return
     * @throws noakweather.utils.UtilsException
     * @throws java.io.IOException
     */
    public static WeatherDataFile fromPath(Path filePath) throws UtilsException, IOException {
        List<String> lines = Files.readAllLines(filePath);
        return new WeatherDataFile(lines);
    }
    
    /**
     * Private constructor that takes the list of lines
     *
     * @param lines
     * @return
     */
    private WeatherDataFile(List<String> lines) {
        this.records = lines.stream()
            .filter(line -> !line.trim().isEmpty())
            .map(WeatherRecord::fromLine)
            .collect(Collectors.toList());
        
        // Get station code from first record
        this.primaryStationCode = records.isEmpty() ? "UNKN" : records.get(0).getStationCode();
    }

    /**
     * Find METAR data
     *
     * @param stationCode
     * @return
     */
    public Optional<String> findMetarData(String stationCode) {
        return records.stream()
            .filter(record -> record.getType() == WeatherType.METAR)
            .filter(record -> record.getStationCode().equals(stationCode))
            .map(WeatherRecord::getFormattedForParser)
            .findFirst();
    }
    
    /**
     * Find TAF data
     *
     * @param stationCode
     * @return
     */
    public Optional<String> findTafData(String stationCode) {
        return records.stream()
            .filter(record -> record.getType() == WeatherType.TAF)
            .filter(record -> record.getStationCode().equals(stationCode))
            .map(WeatherRecord::getFormattedForParser)
            .findFirst();
    }
    
    /**
     * Get primary station code
     *
     * @return
     */
    public String getPrimaryStationCode() {
        return primaryStationCode;
    }
    
    /**
     * Static nested class for individual weather records
     *
     * Author: quark95cos Since: Copyright(c) 2025
     */
    private static class WeatherRecord {
        private final WeatherType type;
        private final String stationCode;
        private final String originalLine; // Store the complete original line
        
        /**
         * Private constructor
         *
         * @return
         */
        private WeatherRecord(WeatherType type, String stationCode, String originalLine) {
            this.type = type;
            this.stationCode = stationCode;
            this.originalLine = originalLine;
        }
        
        /**
         * Static factory method to create WeatherRecord from a line
         *
         * @return
         */
        static WeatherRecord fromLine(String line) {
            String[] parts = line.split("\\s+", 4);
            if (parts.length < 4) {
                throw new IllegalArgumentException("Invalid weather data line: " + line);
            }
            
            boolean isTaf = "TAF".equals(parts[2]);
            WeatherType type = isTaf ? WeatherType.TAF : WeatherType.METAR;
            
            String stationCode;
            
            if (isTaf) {
                // TAF format: date time TAF [AMD] STATION_CODE weather_data...
                String[] tafParts = parts[3].split("\\s+");
                if (tafParts.length >= 2 && "AMD".equals(tafParts[0])) {
                    stationCode = tafParts[1];
                } else if (tafParts.length >= 1) {
                    stationCode = tafParts[0];
                } else {
                    throw new IllegalArgumentException("Invalid TAF format: " + line);
                }
            } else {
                // METAR format: date time STATION_CODE weather_data...
                stationCode = parts[2];
            }
            
            // Store the complete original line for the parser
            return new WeatherRecord(type, stationCode, line);
        }
        
        /**
         * Formatter for parser which returns the complete original
         * line for your existing parser
         * 
         * @return
         */
        String getFormattedForParser() {
            // 
            return originalLine;
        }
        
        /**
         * Weather type
         * 
         * @return
         */
        WeatherType getType() { 
            return type; 
        }
        
        /**
         * Station Code
         * 
         * @return
         */
        String getStationCode() { 
            return stationCode; 
        }
    }
    
    /**
     * Enum for weather types
     *
     */
    enum WeatherType {
        METAR, TAF
    }
}
