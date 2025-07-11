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
package noakweather.noaa_api.wthgroup;

import java.util.regex.Pattern;
import noakweather.utils.Configs;
import noakweather.utils.IndexedLinkedHashMap;
import noakweather.utils.UtilsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javatuples.Pair;

/**
 * Class representing the becoming section of the report
 *
 * Author: quark95cos Since: Copyright(c) 2022
 */
public class Becoming extends Group {

    private static final Logger LOGGER
            = LogManager.getLogger(Becoming.class.getName());

    // Config string constant to fix conditional method invocation
    private static final String MSG_EXTENDED_DECODED_BECMG = 
        Configs.getInstance().getString("EXTENDED_DECODED_BECMG");

    public Becoming() {
        LOGGER.debug("in Becoming constructor");
    }

    /**
     * Set the becoming information and return the natural language in human
     * readable form
     *
     * @param token the weather token to process
     * @param monthString the month string for date processing
     * @param yearString the year string for date processing
     * @param groupWeathHandlers map of regex patterns to their corresponding handler information
     * @return natural language string representation of the becoming conditions
     * @throws noakweather.utils.UtilsException if error occurs during pattern processing
     */
    public String setBecomingItems(String token, String monthString, String yearString,
            IndexedLinkedHashMap<Pattern, Pair<String, Boolean>> groupWeathHandlers) throws UtilsException {

        LOGGER.debug("token: #{}", token);
        LOGGER.debug("monthString: #{}", monthString);
        LOGGER.debug("yearString: #{}\n", yearString);

        setMonthString(monthString);
        setYearString(yearString);

        parseGroupHandlers(token, groupWeathHandlers);

        return getNaturalLanguageString(MSG_EXTENDED_DECODED_BECMG);
    }
}
