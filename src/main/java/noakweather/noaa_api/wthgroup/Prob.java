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
 * Class representing the probability section of the report. It inherits from
 * the Group class
 *
 * Author: quark95cos Since: Copyright(c) 2022
 */
public class Prob extends Group {

    private static final Logger LOGGER
            = LogManager.getLogger(Prob.class.getName());

    // Config string constant to fix conditional method invocation
    private static final String MSG_EXTENDED_DECODED_PROB = 
        Configs.getInstance().getString("EXTENDED_DECODED_PROB");

    public Prob() {
        LOGGER.debug("in Prob constructor");
    }

    /**
     * Set probability information and return the natural language in human
     * readable form
     *
     * @param group the group to process
     * @param token the weather token to process
     * @param monthString the month string for date processing
     * @param yearString the year string for date processing
     * @param groupWeathHandlers map of regex patterns to their corresponding handler information
     * @return natural language string representation of the prob conditions
     * @throws noakweather.utils.UtilsException if error occurs during pattern processing
     */
    public String setProbItems(String group, String token, String monthString, String yearString,
            IndexedLinkedHashMap<Pattern, Pair<String, Boolean>> groupWeathHandlers) throws UtilsException {

        LOGGER.debug("Inside setProbItems group processing: #{}", group);
        LOGGER.debug("Inside setProbItems token processing: #{}", token);
        LOGGER.debug("Inside setProbItems monthString: #{}", monthString);
        LOGGER.debug("Inside setProbItems yearString: #{}\n", yearString);

        setMonthString(monthString);
        setYearString(yearString);

        parseGroupHandlers(token, groupWeathHandlers);

        return getNaturalLanguageString(group.substring(4)
                + MSG_EXTENDED_DECODED_PROB);
    }
}
