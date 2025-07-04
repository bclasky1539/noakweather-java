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
package noakweather.noaa_api.common;

//import noakweather.noakutils.UtilsException;
//import noakweather.noakutils.UtilsMisc;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import noakweather.noaa_api.wthtype.Metar;
import noakweather.noaa_api.wthtype.Taf;
import noakweather.service.WeatherCondHttpClient;
import noakweather.utils.Configs;
import noakweather.utils.UtilsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class representing the weather section the retrieves either the METAR or TAF
 * report
 *
 * Author: quark95cos Since: Copyright(c) 2022
 */
public class Weather {

    private static final Logger LOGGER
            = LogManager.getLogger(Weather.class.getName());

    /**
     * Get the TAF information based on if it is from a file or live
     *
     * @param station
     * @param parsePrint
     * @param dataType
     * @return
     * @throws noakweather.utils.UtilsException
     * @throws java.io.IOException
     */
    public static Taf getTaf(String station, String parsePrint, String dataType) throws UtilsException, IOException {
        if (isFilePath(station)) {
            return getTafFromFile(station, parsePrint);
        } else {
            return getTafFromLiveSource(station, parsePrint, dataType);
        }
    }

    /**
     * Get the TAF information
     *
     * @param station
     * @param parsePrint
     * @param dataType
     * @return
     * @throws noakweather.utils.UtilsException
     */
    private static Taf getTafFromLiveSource(String station, String parsePrint, String dataType) throws UtilsException {
        Optional<String> tafData = WeatherCondHttpClient.fetchMetarOrTaf(station, dataType);
        return processTafData(station, parsePrint, tafData);
    }

    /**
     * Get the TAF information
     *
     * @param station
     * @param parsePrint
     * @return
     * @throws noakweather.utils.UtilsException
     */
    private static Taf getTafFromFile(String filePath, String parsePrint) throws UtilsException, IOException {
        String cleanPath = filePath.startsWith("FILE:") ? filePath.substring(5) : filePath;
        LOGGER.info(Configs.getInstance().getString("FILENAME_DECODED_VALUE") + " " + cleanPath);
    
        WeatherDataFile weatherFile = WeatherDataFile.fromPath(Paths.get(cleanPath));
        String station = weatherFile.getPrimaryStationCode();
        Optional<String> tafData = weatherFile.findTafData(station);
    
        return processTafData(station, parsePrint, tafData);
    }

    /**
     * Get the TAF information
     *
     * @param station
     * @param parsePrint
     * @param tafData
     * @return
     * @throws noakweather.utils.UtilsException
     */
    private static Taf processTafData(String station, String parsePrint, Optional<String> tafData) throws UtilsException {
        Taf taf = new Taf();
    
        LOGGER.info(Configs.getInstance().getString("MISC_STATION") + " " + station);
    
        if (tafData.isPresent()) {
            String rawData = tafData.get();
            String rawTafMessage = Configs.getInstance().getString("MISC_RAW_TAFDATA") + " #" + rawData + "#";
        
            System.out.println(rawTafMessage);
            LOGGER.info(rawTafMessage);
        
            taf.parse(rawData);
        
            if ("Y".equals(parsePrint)) {
                System.out.println("\n\n\n" + rawTafMessage);
                taf.print();
            }
        } else {
            System.out.println(Configs.getInstance().getString("MISC_TAF_NONE") + " " + station);
        }
    
        return taf;
    }

    /**
     * Get the METAR information based on if it is from a file or live
     *
     * @param station
     * @param parsePrint
     * @param dataType
     * @return
     * @throws noakweather.utils.UtilsException
     * @throws java.io.IOException
     */
    public static Metar getMetar(String station, String parsePrint, String dataType) throws UtilsException, IOException {
        if (isFilePath(station)) {
            return getMetarFromFile(station, parsePrint);
        } else {
            return getMetarFromLiveSource(station, parsePrint, dataType);
        }
    }

    /**
     * Prepare to get the METAR information from live source
     *
     * @param station
     * @param parsePrint
     * @param dataType
     * @return
     * @throws noakweather.utils.UtilsException
     */
    private static Metar getMetarFromLiveSource(String station, String parsePrint, String dataType) throws UtilsException {
        Optional<String> metarData = WeatherCondHttpClient.fetchMetarOrTaf(station, dataType);
        return processMetarData(station, parsePrint, metarData);
    }

    /**
     * Prepare to get the METAR information from file
     *
     * @param station
     * @param parsePrint
     * @return
     * @throws noakweather.utils.UtilsException
     */
    private static Metar getMetarFromFile(String filePath, String parsePrint) throws UtilsException, IOException {
        String cleanPath = filePath.startsWith("FILE:") ? filePath.substring(5) : filePath;
        LOGGER.info(Configs.getInstance().getString("FILENAME_DECODED_VALUE") + " " + cleanPath);
    
        WeatherDataFile weatherFile = WeatherDataFile.fromPath(Paths.get(cleanPath));
        String station = weatherFile.getPrimaryStationCode();
        Optional<String> metarData = weatherFile.findMetarData(station);

        return processMetarData(station, parsePrint, metarData);
    }

    /**
     * Get the METAR information
     *
     * @param station
     * @param parsePrint
     * @param metarData
     * @return
     * @throws noakweather.utils.UtilsException
     */
    private static Metar processMetarData(String station, String parsePrint, Optional<String> metarData) throws UtilsException {
        Metar metar = new Metar();
    
        LOGGER.info(Configs.getInstance().getString("MISC_STATION") + " " + station);
    
        if (metarData.isPresent()) {
            String rawData = metarData.get();
            String rawMetarMessage = Configs.getInstance().getString("MISC_RAW_METARDATA") + " #" + rawData + "#";
        
            System.out.println(rawMetarMessage);
            LOGGER.info(rawMetarMessage);
        
            metar.parse(rawData);
        
            if ("Y".equals(parsePrint)) {
                System.out.println("\n\n\n" + rawMetarMessage);
                metar.print();
            }
        } else {
            System.out.println(Configs.getInstance().getString("MISC_METAR_NONE") + " " + station);
        }

        return metar;
    }

    /**
     * Check if input file exists
     *
     * @param input
     * @return
     */
    private static boolean isFilePath(String input) {
        LOGGER.debug(input.startsWith("FILE:")
                ? Configs.getInstance().getString("FILENAME_DECODED_EXISTS") + " " + input 
                : Configs.getInstance().getString("FILENAME_DECODED_NOT_EXISTS"));
 
        return input.startsWith("FILE:");
    }
}

