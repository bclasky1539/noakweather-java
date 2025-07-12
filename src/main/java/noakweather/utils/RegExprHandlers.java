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
package noakweather.utils;

import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javatuples.Pair;

/**
 * Class representing the regular expression handlers
 *
 * Author: quark95cos Since: Copyright(c) 2022
 */
public final class RegExprHandlers {

    private static final Logger LOGGER
            = LogManager.getLogger(RegExprHandlers.class.getName());

    // Handler name constants to eliminate duplicate string literals
    private static final String HANDLER_MONTH_DAY_YEAR = "mnthdayyr";
    private static final String HANDLER_STATION = "station";
    private static final String HANDLER_REPORT_MODIFIER = "reportmodifier";
    private static final String HANDLER_WIND = "wind";
    private static final String HANDLER_VISIBILITY = "visibility";
    private static final String HANDLER_RUNWAY = "runway";
    private static final String HANDLER_PRESENT_WEATHER = "presentweather";
    private static final String HANDLER_SKY_CONDITION = "skycondition";
    private static final String HANDLER_TEMP_DEWPOINT = "tempdewpoint";
    private static final String HANDLER_ALTIMETER = "altimeter";
    private static final String HANDLER_NO_SIG_CHANGE = "nosigchng";
    private static final String HANDLER_VALID_TIME_PERIOD = "valtmper";
    private static final String HANDLER_UNPARSED = "unparsed";
    
    // Remarks-specific handler constants
    private static final String HANDLER_PRESSURE_RISE_FALL = "presrisfal";
    private static final String HANDLER_TORNADO_FUNNEL_WATERSPOUT = "trnfcwsp";
    private static final String HANDLER_AUTO = "auto";
    private static final String HANDLER_BEGIN_END_WEATHER = "beginendwthr";
    private static final String HANDLER_ICING = "icing";
    private static final String HANDLER_PEAK_WIND = "peakwind";
    private static final String HANDLER_WIND_SHIFT = "windshift";
    private static final String HANDLER_LIGHTNING = "lightning";
    private static final String HANDLER_SEA_LEVEL_PRESSURE = "sealvlpress";
    private static final String HANDLER_SIX_HOUR_MAX_MIN_TEMP = "sixhrmaxmintemp";
    private static final String HANDLER_PRECIP_1HR = "precip1hr";
    private static final String HANDLER_TEMP_1HR = "temp1hr";
    private static final String HANDLER_TEMP_24HR = "temp24hr";
    private static final String HANDLER_PRESS_3HR = "press3hr";
    private static final String HANDLER_PRECIP_3HR_24HR = "precip3hr24hr";
    private static final String HANDLER_DENSITY_ALTITUDE = "denalt";
    private static final String HANDLER_CLOUD_OKTA = "cloudokta";
    private static final String HANDLER_LAST_OBS = "lastobs";
    private static final String HANDLER_PRESS_Q = "pressqfn";
    private static final String HANDLER_AUTOMATED_MAINTENANCE = "automaint";
    private static final String HANDLER_TOWER_SURFACE_VIS = "twrsfcvis";
    private static final String HANDLER_VPV_SV_VSL = "vpvsvvsl";
    private static final String HANDLER_TS_CLOUD_LOCATION = "tsloc";
    private static final String HANDLER_SNOW_ON_GROUND = "snwongrnd";
    private static final String HANDLER_NEXT_FORECAST_BY = "nxtfcstby";
    private static final String HANDLER_WIND_REMARKS = "windre";
    
    // Config key constants for debug messages
    private static final String CONFIG_MAIN_HANDLERS = "REG_EXPR_DECODED_MAIN_HANDLERS";
    private static final String CONFIG_REMARKS_HANDLERS = "REG_EXPR_DECODED_REMARKS_HANDLERS";
    private static final String CONFIG_GROUPS_HANDLERS = "REG_EXPR_DECODED_GROUPS_HANDLERS";

    /**
     * Private constructor to prevent instantiation of utility class
     */
    private RegExprHandlers() {
        // Utility class - not meant to be instantiated
    }

    /**
     * Parse the main information
     *
     * @return mainHandlers
     */
    public static final IndexedLinkedHashMap<Pattern, Pair<String, Boolean>> setMainHandlers() {
        IndexedLinkedHashMap<Pattern, Pair<String, Boolean>> mainHandlers;
        mainHandlers = new IndexedLinkedHashMap<>();
        mainHandlers.put(RegExprConst.MONTH_DAY_YEAR_PATTERN,
                Pair.with(HANDLER_MONTH_DAY_YEAR, false));
        mainHandlers.put(RegExprConst.STATION_DAY_TIME_VALTMPER_PATTERN,
                Pair.with(HANDLER_STATION, false));
        mainHandlers.put(RegExprConst.REPORT_MODIFIER_PATTERN,
                Pair.with(HANDLER_REPORT_MODIFIER, false));
        mainHandlers.put(RegExprConst.WIND_PATTERN,
                Pair.with(HANDLER_WIND, false));
        mainHandlers.put(RegExprConst.VISIBILITY_PATTERN,
                Pair.with(HANDLER_VISIBILITY, false));
        mainHandlers.put(RegExprConst.RUNWAY_PATTERN,
                Pair.with(HANDLER_RUNWAY, true));
        mainHandlers.put(RegExprConst.PRESENT_WEATHER_PATTERN,
                Pair.with(HANDLER_PRESENT_WEATHER, true));
        mainHandlers.put(RegExprConst.SKY_CONDITION_PATTERN,
                Pair.with(HANDLER_SKY_CONDITION, true));
        mainHandlers.put(RegExprConst.TEMP_DEWPOINT_PATTERN,
                Pair.with(HANDLER_TEMP_DEWPOINT, false));
        mainHandlers.put(RegExprConst.ALTIMETER_PATTERN,
                Pair.with(HANDLER_ALTIMETER, false));
        mainHandlers.put(RegExprConst.NO_SIG_CHANGE_PATTERN,
                Pair.with(HANDLER_NO_SIG_CHANGE, false));

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("\n");
            LOGGER.debug(Configs.getInstance().getString(CONFIG_MAIN_HANDLERS));
            mainHandlers.forEach((k, v) -> LOGGER.debug("{}:{}", k, v));
            LOGGER.debug("{}\n", Configs.getInstance().getString(CONFIG_MAIN_HANDLERS));
        }

        return mainHandlers;
    }

    /**
     * Parse the remarks information
     *
     * @return remarkHandlers
     */
    public static final IndexedLinkedHashMap<Pattern, Pair<String, Boolean>> setRemarksHandlers() {
        IndexedLinkedHashMap<Pattern, Pair<String, Boolean>> remarkHandlers;
        remarkHandlers = new IndexedLinkedHashMap<>();
        remarkHandlers.put(RegExprConst.PRES_RF_RAPDLY_PATTERN,
                Pair.with(HANDLER_PRESSURE_RISE_FALL, false));
        remarkHandlers.put(RegExprConst.TRN_FC_WSP_PATTERN,
                Pair.with(HANDLER_TORNADO_FUNNEL_WATERSPOUT, false));
        remarkHandlers.put(RegExprConst.AUTO_PATTERN,
                Pair.with(HANDLER_AUTO, false));
        remarkHandlers.put(RegExprConst.BEGIN_END_WEATHER_PATTERN,
                Pair.with(HANDLER_BEGIN_END_WEATHER, true));
        remarkHandlers.put(RegExprConst.ICING_PATTERN,
                Pair.with(HANDLER_ICING, false));
        remarkHandlers.put(RegExprConst.PEAK_WIND_PATTERN,
                Pair.with(HANDLER_PEAK_WIND, false));
        remarkHandlers.put(RegExprConst.WIND_SHIFT_PATTERN,
                Pair.with(HANDLER_WIND_SHIFT, false));
        remarkHandlers.put(RegExprConst.LIGHTNING_PATTERN,
                Pair.with(HANDLER_LIGHTNING, true));
        remarkHandlers.put(RegExprConst.SEALVL_PRESS_PATTERN,
                Pair.with(HANDLER_SEA_LEVEL_PRESSURE, false));
        remarkHandlers.put(RegExprConst.TEMP_6HR_MAX_MIN_PATTERN,
                Pair.with(HANDLER_SIX_HOUR_MAX_MIN_TEMP, true));
        remarkHandlers.put(RegExprConst.PRECIP_1HR_PATTERN,
                Pair.with(HANDLER_PRECIP_1HR, false));
        remarkHandlers.put(RegExprConst.TEMP_1HR_PATTERN,
                Pair.with(HANDLER_TEMP_1HR, false));
        remarkHandlers.put(RegExprConst.TEMP_24HR_PATTERN,
                Pair.with(HANDLER_TEMP_24HR, false));
        remarkHandlers.put(RegExprConst.PRESS_3HR_PATTERN,
                Pair.with(HANDLER_PRESS_3HR, false));
        remarkHandlers.put(RegExprConst.PRECIP_3HR_24HR_PATTERN,
                Pair.with(HANDLER_PRECIP_3HR_24HR, true));
        remarkHandlers.put(RegExprConst.DENSITY_ALTITUDE_PATTERN,
                Pair.with(HANDLER_DENSITY_ALTITUDE, false));
        remarkHandlers.put(RegExprConst.CLOUD_OKTA_PATTERN,
                Pair.with(HANDLER_CLOUD_OKTA, true));
        remarkHandlers.put(RegExprConst.LAST_OBS_PATTERN,
                Pair.with(HANDLER_LAST_OBS, false));
        remarkHandlers.put(RegExprConst.PRESS_Q_PATTERN,
                Pair.with(HANDLER_PRESS_Q, false));
        remarkHandlers.put(RegExprConst.AUTOMATED_MAINTENANCE_PATTERN,
                Pair.with(HANDLER_AUTOMATED_MAINTENANCE, true));
        remarkHandlers.put(RegExprConst.TWR_SFC_VIS_PATTERN,
                Pair.with(HANDLER_TOWER_SURFACE_VIS, false));
        remarkHandlers.put(RegExprConst.VPV_SV_VSL_PATTERN,
                Pair.with(HANDLER_VPV_SV_VSL, true));
        remarkHandlers.put(RegExprConst.TS_CLD_LOC_PATTERN,
                Pair.with(HANDLER_TS_CLOUD_LOCATION, true));
        remarkHandlers.put(RegExprConst.SNOW_ON_GRND_PATTERN,
                Pair.with(HANDLER_SNOW_ON_GROUND, true));
        remarkHandlers.put(RegExprConst.NXT_FCST_BY_PATTERN,
                Pair.with(HANDLER_NEXT_FORECAST_BY, false));
        // Wind situations can occur in remarks in certain cases
        remarkHandlers.put(RegExprConst.WIND_PATTERN,
                Pair.with(HANDLER_WIND_REMARKS, false));
        remarkHandlers.put(RegExprConst.UNPARSED_PATTERN,
                Pair.with(HANDLER_UNPARSED, false));

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("\n");
            LOGGER.debug(Configs.getInstance().getString(CONFIG_REMARKS_HANDLERS));
            remarkHandlers.forEach((k, v) -> LOGGER.debug("{}:{}", k, v));
            LOGGER.debug("{}\n", Configs.getInstance().getString(CONFIG_REMARKS_HANDLERS));
        }

        return remarkHandlers;
    }

    /**
     * Parse the group information
     *
     * @return groupHandlers
     */
    public static final IndexedLinkedHashMap<Pattern, Pair<String, Boolean>> setGroupHandlers() {
        IndexedLinkedHashMap<Pattern, Pair<String, Boolean>> groupHandlers;
        groupHandlers = new IndexedLinkedHashMap<>();
        groupHandlers.put(RegExprConst.VALTMPER_PATTERN,
                Pair.with(HANDLER_VALID_TIME_PERIOD, false));
        groupHandlers.put(RegExprConst.WIND_PATTERN,
                Pair.with(HANDLER_WIND, false));
        groupHandlers.put(RegExprConst.VISIBILITY_PATTERN,
                Pair.with(HANDLER_VISIBILITY, false));
        groupHandlers.put(RegExprConst.RUNWAY_PATTERN,
                Pair.with(HANDLER_RUNWAY, true));
        groupHandlers.put(RegExprConst.PRESENT_WEATHER_PATTERN,
                Pair.with(HANDLER_PRESENT_WEATHER, true));
        groupHandlers.put(RegExprConst.SKY_CONDITION_PATTERN,
                Pair.with(HANDLER_SKY_CONDITION, true));
        groupHandlers.put(RegExprConst.TEMP_DEWPOINT_PATTERN,
                Pair.with(HANDLER_TEMP_DEWPOINT, false));
        groupHandlers.put(RegExprConst.ALTIMETER_PATTERN,
                Pair.with(HANDLER_ALTIMETER, false));
        groupHandlers.put(RegExprConst.NO_SIG_CHANGE_PATTERN,
                Pair.with(HANDLER_NO_SIG_CHANGE, false));
        groupHandlers.put(RegExprConst.UNPARSED_PATTERN,
                Pair.with(HANDLER_UNPARSED, false));

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("\n");
            LOGGER.debug(Configs.getInstance().getString(CONFIG_GROUPS_HANDLERS));
            groupHandlers.forEach((k, v) -> LOGGER.debug("{}:{}", k, v));
            LOGGER.debug("{}\n", Configs.getInstance().getString(CONFIG_GROUPS_HANDLERS));
        }

        return groupHandlers;
    }
}
