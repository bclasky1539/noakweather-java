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
package noakweather;

import java.io.IOException;
import java.util.Locale;
import noakweather.noaa_api.common.Weather;
import noakweather.noaa_api.wthtype.Metar;
import noakweather.noaa_api.wthtype.Taf;
import noakweather.utils.Configs;
import noakweather.utils.UtilsException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * Class representing the main program
 *
 * Author: quark95cos Since: Copyright(c) 2022
 */
public class NoakWeatherMain {

    static String station = "";
    static Metar metar = null;
    static Taf taf = null;

    private static final Logger LOGGER
            = LogManager.getLogger(NoakWeatherMain.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Configs.getInstance().setLocale(Locale.ENGLISH);
        
        final String MSG_NOT_EN = Configs.getInstance().getString("LOG_DECODED_MSG_NOT_EN");
        final String MSG_MET_PARM = Configs.getInstance().getString("LOG_DECODED_MSG_MET_PARM");
        final String MSG_TAF_PARM = Configs.getInstance().getString("LOG_DECODED_MSG_TAF_PARM");
        final String MSG_EXIT = Configs.getInstance().getString("LOG_DECODED_MSG_EXIT");
        final String MSG_UNK_WTH_TYP = Configs.getInstance().getString("LOG_DECODED_MSG_UNK_WTH_TYP");
        final String CHECK_LOG_FILE = Configs.getInstance().getString("EXCEP_CHECK_LOG_FILE");
        final String LOG_DECODED_INFO = Configs.getInstance().getString("LOG_DECODED_INFO");
        final String LOG_DECODED_WARN = Configs.getInstance().getString("LOG_DECODED_WARN");
        final String LOG_DECODED_DEBUG = Configs.getInstance().getString("LOG_DECODED_DEBUG");
        final String LOG_DECODED_UNKN = Configs.getInstance().getString("LOG_DECODED_UNKN");
        final String PROCESS_METAR_DATA = Configs.getInstance().getString("LOG_DECODED_PROCESS_METAR_DATA");
        final String PROCESS_TAF_DATA = Configs.getInstance().getString("LOG_DECODED_PROCESS_TAF_DATA");

        if (args.length < 4) {
            LOGGER.fatal(MSG_NOT_EN);
            LOGGER.fatal(MSG_MET_PARM);
            LOGGER.fatal(MSG_TAF_PARM);
            LOGGER.fatal(MSG_EXIT);
            return;
        }

        // This works to set a particular Logger's logging level
        // For future use
        //Configurator.setLevel(AviaWeath.class.getName(), Level.WARN);

        // Parse parameters
        if (args[3].toUpperCase().matches("I")) {
            // Set the root LOGGER to Level.INFO
            Configurator.setRootLevel(Level.INFO);
            LOGGER.info(LOG_DECODED_INFO);
        } else if (args[3].toUpperCase().matches("W")) {
            // Set the root LOGGER to Level.WARN
            Configurator.setRootLevel(Level.WARN);
            LOGGER.info(LOG_DECODED_WARN);
        } else if (args[3].toUpperCase().matches("D")) {
            // Set the root LOGGER to Level.DEBUG
            Configurator.setRootLevel(Level.DEBUG);
            LOGGER.info(LOG_DECODED_DEBUG);
        } else {
            // Set the root LOGGER to Level.INFO
            Configurator.setRootLevel(Level.INFO);
            LOGGER.info(LOG_DECODED_UNKN);
        }

        try {
            if (args[0].toUpperCase().matches(Configs.getInstance().getString("MISC_METAR_M"))) {
                LOGGER.info(PROCESS_METAR_DATA);
                station = args[1].toUpperCase();
                metar = Weather.getMetar(station, args[2].toUpperCase(), args[0].toUpperCase());
            } else if (args[0].toUpperCase().matches(Configs.getInstance().getString("MISC_TAF_T"))) {
                LOGGER.info(PROCESS_TAF_DATA);
                station = args[1].toUpperCase();
                taf = Weather.getTaf(station, args[2].toUpperCase(), args[0].toUpperCase());
            } else {
                LOGGER.fatal(MSG_UNK_WTH_TYP);
                LOGGER.fatal(MSG_MET_PARM);
                LOGGER.fatal(MSG_TAF_PARM);
            }
        } catch (UtilsException | IOException err) {
            LOGGER.fatal(CHECK_LOG_FILE);
        }
    }
}
