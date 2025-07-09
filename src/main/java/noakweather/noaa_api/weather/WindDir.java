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
package noakweather.noaa_api.weather;

import noakweather.utils.Configs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class representing wind direction information
 *
 * Author: quark95cos Since: Copyright(c) 2022
 */
public class WindDir {

    public static final Logger LOGGER
            = LogManager.getLogger(WindDir.class.getName());

    // Private constructor to prevent instantiation of utility class
    private WindDir() {
        // Utility class - not meant to be instantiated
    }

    /**
     * Get formatted wind direction From wind direction in degrees, determine
     * compass direction as a string (e.g NW)
     *
     * @param degrees
     * @return wind direction compass
     */
    public static String getFormattedWindDir(Integer degrees) {
        Configs config = Configs.getInstance();

        LOGGER.debug("Input degrees: {}", degrees);

        // Normalize degrees to 0-360 range
        degrees = ((degrees % 360) + 360) % 360;

        // Define direction names in clockwise order starting from North
        final String[] directionKeys = {
            "WIND_DIR_NORTH",
            "WIND_DIR_NORTH_NORTH_EAST",
            "WIND_DIR_NORTH_EAST",
            "WIND_DIR_EAST_NORTH_EAST",
            "WIND_DIR_EAST",
            "WIND_DIR_EAST_SOUTH_EAST",
            "WIND_DIR_SOUTH_EAST",
            "WIND_DIR_SOUTH_SOUTH_EAST",
            "WIND_DIR_SOUTH",
            "WIND_DIR_SOUTH_SOUTH_WEST",
            "WIND_DIR_SOUTH_WEST",
            "WIND_DIR_WEST_SOUTH_WEST",
            "WIND_DIR_WEST",
            "WIND_DIR_WEST_NORTH_WEST",
            "WIND_DIR_NORTH_WEST",
            "WIND_DIR_NORTH_NORTH_WEST"
        };

        // Each direction covers 22.5 degrees, calculate index directly
        int index = (int)Math.round(degrees / 22.5) % 16;

        return config.getString(directionKeys[index]);
    }
}
