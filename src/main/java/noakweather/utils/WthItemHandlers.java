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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javatuples.Pair;
import java.util.Objects;

// Common interface for all config key enums
interface ConfigKey {
    String getKey();
}

// Domain-specific configuration key enums
enum RvrConfigKeys implements ConfigKey {
    REPORTABLE_ABOVE("RVR_REPORTABLE_ABOVE"),
    REPORTABLE_BELOW("RVR_REPORTABLE_BELOW");
    
    private final String key;
    
    RvrConfigKeys(String key) {
        this.key = key;
    }
    
    @Override
    public String getKey() {
        return key;
    }
}

enum LocationTimeConfigKeys implements ConfigKey {
    DECODED_GREATER_THAN("LOC_TIME_DECODED_GREATER_THAN"),
    DECODED_LESS_THAN("LOC_TIME_DECODED_LESS_THAN"),
    BEGIN("LOC_TIME_BEGIN"),
    END("LOC_TIME_END"),
    DECODED_BEGIN("LOC_TIME_DECODED_BEGIN"),
    DECODED_ENDING("LOC_TIME_DECODED_ENDING"),
    OHD("LOC_TIME_OHD"),
    DECODED_OHD("LOC_TIME_DECODED_OHD"),
    VC("LOC_TIME_VC"),
    DECODED_VC("LOC_TIME_DECODED_VC"),
    DISTANT("LOC_TIME_DISTANT"),
    DECODED_DISTANT("LOC_TIME_DECODED_DISTANT"),
    DISIPATED("LOC_TIME_DISIPATED"),
    DECODED_DISIPATED("LOC_TIME_DECODED_DISIPATED"),
    TOP("LOC_TIME_TOP"),
    DECODED_TOP("LOC_TIME_DECODED_TOP"),
    MOVG("LOC_TIME_MOVG"),
    DECODED_MOVNG("LOC_TIME_DECODED_MOVNG"),
    OCCASIONAL("LOC_TIME_OCCASIONAL"),
    DECODED_OCCASIONAL("LOC_TIME_DECODED_OCCASIONAL"),
    FREQUENT("LOC_TIME_FREQUENT"),
    DECODED_FREQUENT("LOC_TIME_DECODED_FREQUENT"),
    CONTINUOUS("LOC_TIME_CONTINUOUS"),
    DECODED_CONTINUOUS("LOC_TIME_DECODED_CONTINUOUS");
    
    private final String key;
    
    LocationTimeConfigKeys(String key) {
        this.key = key;
    }
    
    @Override
    public String getKey() {
        return key;
    }
}

enum MiscConfigKeys implements ConfigKey {
    VALUE_UL("MISC_VALUE_UL"),
    VALUE_UR("MISC_VALUE_UR"),
    VALUE_UC("MISC_VALUE_UC"),
    VALUE_UU("MISC_VALUE_UU"),
    VALUE_UD("MISC_VALUE_UD"),
    VALUE_UN("MISC_VALUE_UN"),
    DECODED_VALUE_L("MISC_DECODED_VALUE_L"),
    DECODED_VALUE_R("MISC_DECODED_VALUE_R"),
    DECODED_VALUE_C("MISC_DECODED_VALUE_C"),
    DECODED_VALUE_U("MISC_DECODED_VALUE_U"),
    DECODED_VALUE_D("MISC_DECODED_VALUE_D"),
    DECODED_VALUE_N("MISC_DECODED_VALUE_N");
    
    private final String key;
    
    MiscConfigKeys(String key) {
        this.key = key;
    }
    
    @Override
    public String getKey() {
        return key;
    }
}

enum SkyConditionConfigKeys implements ConfigKey {
    VERTICAL_VISIBILITY("SKY_COND_VERTICAL_VISIBILITY"),
    SKY_CLEAR("SKY_COND_SKY_CLEAR"),
    CLEAR("SKY_COND_CLEAR"),
    FEW("SKY_COND_FEW"),
    SCATTERED("SKY_COND_SCATTERED"),
    BROKEN("SKY_COND_BROKEN"),
    OVERCAST("SKY_COND_OVERCAST"),
    NO_CLOUDS_DETECTED("SKY_COND_NO_CLOUDS_DETECTED"),
    NO_SIGNIFICANT_CLOUDS("SKY_COND_NO_SIGNIFICANT_CLOUDS"),
    DECODED_VERTICAL_VISIBILITY("SKY_COND_DECODED_VERTICAL_VISIBILITY"),
    DECODED_SKY_CLEAR("SKY_COND_DECODED_SKY_CLEAR"),
    DECODED_CLEAR("SKY_COND_DECODED_CLEAR"),
    DECODED_FEW("SKY_COND_DECODED_FEW"),
    DECODED_SCATTERED("SKY_COND_DECODED_SCATTERED"),
    DECODED_BROKEN("SKY_COND_DECODED_BROKEN"),
    DECODED_OVERCAST("SKY_COND_DECODED_OVERCAST"),
    DECODED_NO_CLOUDS_DETECTED("SKY_COND_DECODED_NO_CLOUDS_DETECTED"),
    DECODED_NO_SIGNIFICANT_CLOUDS("SKY_COND_DECODED_NO_SIGNIFICANT_CLOUDS"),
    DECODED_ALQDS("SKY_COND_DECODED_ALQDS"),
    DECODED_OHD_ALQDS("SKY_COND_DECODED_OHD-ALQDS");
    
    private final String key;
    
    SkyConditionConfigKeys(String key) {
        this.key = key;
    }
    
    @Override
    public String getKey() {
        return key;
    }
}

enum CloudConfigKeys implements ConfigKey {
    CUMULONIMBUS("CLOUD_CUMULONIMBUS"),
    TOWERING_CUMULUS("CLOUD_TOWERING_CUMULUS"),
    ALTOCUMULUS_CASTELLANUS("CLOUD_ALTOCUMULUS_CASTELLANUS"),
    CUMULONIMBUS_MAMMATUS("CLOUD_CUMULONIMBUS_MAMMATUS"),
    CUMULUS("CLOUD_CUMULUS"),
    CUMULUS_FRACTUS("CLOUD_CUMULUS_FRACTUS"),
    STRATUS("CLOUD_STRATUS"),
    STRATUS_FRACTUS("CLOUD_STRATUS_FRACTUS"),
    STRATOCUMULUS("CLOUD_STRATOCUMULUS"),
    NIMBOSTRATUS("CLOUD_NIMBOSTRATUS"),
    ALTOSTRATUS("CLOUD_ALTOSTRATUS"),
    ALTOCUMULUS("CLOUD_ALTOCUMULUS"),
    CIRROSTRATUS("CLOUD_CIRROSTRATUS"),
    CIRROCUMULUS("CLOUD_CIRROCUMULUS"),
    CIRRUS("CLOUD_CIRRUS"),
    DECODED_CUMULONIMBUS("CLOUD_DECODED_CUMULONIMBUS"),
    DECODED_TOWERING_CUMULUS("CLOUD_DECODED_TOWERING_CUMULUS"),
    DECODED_ALTOCUMULUS_CASTELLANUS("CLOUD_DECODED_ALTOCUMULUS_CASTELLANUS"),
    DECODED_CUMULONIMBUS_MAMMATUS("CLOUD_DECODED_CUMULONIMBUS_MAMMATUS"),
    DECODED_CUMULUS("CLOUD_DECODED_CUMULUS"),
    DECODED_CUMULUS_FRACTUS("CLOUD_DECODED_CUMULUS_FRACTUS"),
    DECODED_STRATUS("CLOUD_DECODED_STRATUS"),
    DECODED_STRATUS_FRACTUS("CLOUD_DECODED_STRATUS_FRACTUS"),
    DECODED_STRATOCUMULUS("CLOUD_DECODED_STRATOCUMULUS"),
    DECODED_NIMBOSTRATUS("CLOUD_DECODED_NIMBOSTRATUS"),
    DECODED_ALTOSTRATUS("CLOUD_DECODED_ALTOSTRATUS"),
    DECODED_ALTOCUMULUS("CLOUD_DECODED_ALTOCUMULUS"),
    DECODED_CIRROSTRATUS("CLOUD_DECODED_CIRROSTRATUS"),
    DECODED_CIRROCUMULUS("CLOUD_DECODED_CIRROCUMULUS"),
    DECODED_CIRRUS("CLOUD_DECODED_CIRRUS"),
    OKTA_1("CLOUD_OKTA_1"),
    OKTA_2("CLOUD_OKTA_2"),
    OKTA_3("CLOUD_OKTA_3"),
    OKTA_4("CLOUD_OKTA_4"),
    OKTA_5("CLOUD_OKTA_5"),
    OKTA_6("CLOUD_OKTA_6"),
    OKTA_7("CLOUD_OKTA_7"),
    OKTA_8("CLOUD_OKTA_8"),
    OKTA_ALQDS("CLOUD_OKTA_ALQDS"),
    OKTA_OHD_ALQDS("CLOUD_OKTA_OHD-ALQDS");
    
    private final String key;
    
    CloudConfigKeys(String key) {
        this.key = key;
    }
    
    @Override
    public String getKey() {
        return key;
    }
}

enum WeatherConfigKeys implements ConfigKey {
    LIGHT("WEATHER_LIGHT"),
    HEAVY("WEATHER_HEAVY"),
    SHALLOW("WEATHER_SHALLOW"),
    PARTIAL("WEATHER_PARTIAL"),
    PATCHES("WEATHER_PATCHES"),
    LOW_DRIFTING("WEATHER_LOW_DRIFTING"),
    BLOWING("WEATHER_BLOWING"),
    SHOWERS("WEATHER_SHOWERS"),
    THUNDERSTORMS("WEATHER_THUNDERSTORMS"),
    FREEZING("WEATHER_FREEZING"),
    DRIZZLE("WEATHER_DRIZZLE"),
    RAIN("WEATHER_RAIN"),
    SNOW("WEATHER_SNOW"),
    SNOW_GRAINS("WEATHER_SNOW_GRAINS"),
    ICE_CRYSTALS("WEATHER_ICE_CRYSTALS"),
    ICE_PELLETS("WEATHER_ICE_PELLETS"),
    HAIL("WEATHER_HAIL"),
    SMALL_HAIL("WEATHER_SMALL_HAIL"),
    UNKNOWN_PRECIPITATION("WEATHER_UNKNOWN_PRECIPITATION"),
    DUST_SAND_WHIRLS("WEATHER_DUST_SAND_WHIRLS"),
    SQUALLS("WEATHER_SQUALLS"),
    FUNNEL_CLOUD("WEATHER_FUNNEL_CLOUD"),
    SAND_STORM("WEATHER_SAND_STORM"),
    DUST_STORM("WEATHER_DUST_STORM"),
    MIST("WEATHER_MIST"),
    FOG("WEATHER_FOG"),
    SMOKE("WEATHER_SMOKE"),
    VOLCANIC_ASH("WEATHER_VOLCANIC_ASH"),
    WIDESPREAD_DUST("WEATHER_WIDESPREAD_DUST"),
    SAND("WEATHER_SAND"),
    HAZE("WEATHER_HAZE"),
    SPRAY("WEATHER_SPRAY"),
    VIRGA("WEATHER_VIRGA"),
    TRACE("WEATHER_TRACE"),
    IN_CLD("WEATHER_IN_CLD"),
    IN_PRECIPITATION("WEATHER_IN_PRECIPITATION"),
    CLD_TO_CLD("WEATHER_CLD_TO_CLD"),
    CLD_GRND("WEATHER_CLD_GRND"),
    CLD_TO_AIR("WEATHER_CLD_TO_AIR"),
    CLD_TO_WATER("WEATHER_CLD_TO_WATER"),
    DECODED_LIGHT("WEATHER_DECODED_LIGHT"),
    DECODED_HEAVY("WEATHER_DECODED_HEAVY"),
    DECODED_SHALLOW("WEATHER_DECODED_SHALLOW"),
    DECODED_PARTIAL("WEATHER_DECODED_PARTIAL"),
    DECODED_PATCHES("WEATHER_DECODED_PATCHES"),
    DECODED_LOW_DRIFTING("WEATHER_DECODED_LOW_DRIFTING"),
    DECODED_BLOWING("WEATHER_DECODED_BLOWING"),
    DECODED_SHOWERS("WEATHER_DECODED_SHOWERS"),
    DECODED_THUNDERSTORMS("WEATHER_DECODED_THUNDERSTORMS"),
    DECODED_FREEZING("WEATHER_DECODED_FREEZING"),
    DECODED_DRIZZLE("WEATHER_DECODED_DRIZZLE"),
    DECODED_RAIN("WEATHER_DECODED_RAIN"),
    DECODED_SNOW("WEATHER_DECODED_SNOW"),
    DECODED_SNOW_GRAINS("WEATHER_DECODED_SNOW_GRAINS"),
    DECODED_ICE_CRYSTALS("WEATHER_DECODED_ICE_CRYSTALS"),
    DECODED_ICE_PELLETS("WEATHER_DECODED_ICE_PELLETS"),
    DECODED_HAIL("WEATHER_DECODED_HAIL"),
    DECODED_SMALL_HAIL("WEATHER_DECODED_SMALL_HAIL"),
    DECODED_UNKNOWN_PRECIP("WEATHER_DECODED_UNKNOWN_PRECIP"),
    DECODED_DUST_SAND_WHIRLS("WEATHER_DECODED_DUST_SAND_WHIRLS"),
    DECODED_SQUALLS("WEATHER_DECODED_SQUALLS"),
    DECODED_FUNNEL_CLOUD("WEATHER_DECODED_FUNNEL_CLOUD"),
    DECODED_SAND_STORM("WEATHER_DECODED_SAND_STORM"),
    DECODED_DUST_STORM("WEATHER_DECODED_DUST_STORM"),
    DECODED_MIST("WEATHER_DECODED_MIST"),
    DECODED_FOG("WEATHER_DECODED_FOG"),
    DECODED_SMOKE("WEATHER_DECODED_SMOKE"),
    DECODED_VOLCANIC_ASH("WEATHER_DECODED_VOLCANIC_ASH"),
    DECODED_WIDESPREAD_DUST("WEATHER_DECODED_WIDESPREAD_DUST"),
    DECODED_SAND("WEATHER_DECODED_SAND"),
    DECODED_HAZE("WEATHER_DECODED_HAZE"),
    DECODED_SPRAY("WEATHER_DECODED_SPRAY"),
    DECODED_VIRGA("WEATHER_DECODED_VIRGA"),
    DECODED_TRACE("WEATHER_DECODED_TRACE"),
    DECODED_IN_CLD("WEATHER_DECODED_IN_CLD"),
    DECODED_IN_PRECIPITATION("WEATHER_DECODED_IN_PRECIPITATION"),
    DECODED_CLD_TO_CLD("WEATHER_DECODED_CLD_TO_CLD"),
    DECODED_CLD_GRND("WEATHER_DECODED_CLD_GRND"),
    DECODED_CLD_TO_AIR("WEATHER_DECODED_CLD_TO_AIR"),
    DECODED_CLD_TO_WATER("WEATHER_DECODED_CLD_TO_WATER"),
    DECODED_LIGHTNING_OBSERVED("WEATHER_DECODED_LIGHTNING_OBSERVED");
    
    private final String key;
    
    WeatherConfigKeys(String key) {
        this.key = key;
    }
    
    @Override
    public String getKey() {
        return key;
    }
}

enum ExtendedConfigKeys implements ConfigKey {
    TORNADO("EXTENDED_TORNADO"),
    FUNNEL_CLOUD("EXTENDED_FUNNEL_CLOUD"),
    WATERSPOUT("EXTENDED_WATERSPOUT"),
    ICING("EXTENDED_ICING"),
    AUTO_A01("EXTENDED_AUTO_A01"),
    AUTO_AO1("EXTENDED_AUTO_AO1"),
    AUTO_A02("EXTENDED_AUTO_A02"),
    AUTO_AO2("EXTENDED_AUTO_AO2"),
    SIX_HOUR_PRECIPITATION_AMOUNT("EXTENDED_6_HOUR_PRECIPITATION_AMOUNT"),
    TWENTY_FOUR_HOUR_PRECIPITATION_AMOUNT("EXTENDED_24_HOUR_PRECIPITATION_AMOUNT"),
    MAINTENANCE_CHECK_INDICATOR("EXTENDED_MAINTENANCE_CHECK_INDICATOR"),
    DECODED_TORNADO("EXTENDED_DECODED_TORNADO"),
    DECODED_FUNNEL_CLOUD("EXTENDED_DECODED_FUNNEL_CLOUD"),
    DECODED_WATERSPOUT("EXTENDED_DECODED_WATERSPOUT"),
    DECODED_ICING("EXTENDED_DECODED_ICING"),
    DECODED_AUTO_AO1("EXTENDED_DECODED_AUTO_AO1"),
    DECODED_AUTO_AO2("EXTENDED_DECODED_AUTO_AO2"),
    DECODED_6_HOUR_PRECIPITATION("EXTENDED_DECODED_6_HOUR_PRECIPITATION"),
    DECODED_24_HOUR_PRECIPITATION("EXTENDED_DECODED_24_HOUR_PRECIPITATION"),
    DECODED_MAINTENANCE_CHECK_INDICATOR("EXTENDED_DECODED_MAINTENANCE_CHECK_INDICATOR"),
    DECODED_LTNG_FREQ_LESS_1("EXTENDED_DECODED_LTNG_FREQ_LESS_1"),
    DECODED_LTNG_FREQ_1_TO_6("EXTENDED_DECODED_LTNG_FREQ_1_TO_6"),
    DECODED_LTNG_FREQ_MORE_6("EXTENDED_DECODED_LTNG_FREQ_MORE_6");
    
    private final String key;
    
    ExtendedConfigKeys(String key) {
        this.key = key;
    }
    
    @Override
    public String getKey() {
        return key;
    }
}

enum PressureConfigKeys implements ConfigKey {
    FALLING("PRESS_FALLING"),
    RISING("PRESS_RISING"),
    DECODED_FALLING_RAPIDLY("EXTENDED_DECODED_PRESSURE_FALLING_RAPIDLY"),
    DECODED_RISING_RAPIDLY("EXTENDED_DECODED_PRESSURE_RISING_RAPIDLY"),
    TENDENCY_INCR_DEACR("EXTENDED_PRESSURE_TENDENCY_INCR_DEACR"),
    TENDENCY_INCR_STEADY_OR_INCR_INCR_SLOWLY("EXTENDED_PRESSURE_TENDENCY_INCR_STEADY_OR_INCR_INCR_SLOWLY"),
    TENDENCY_INCR_STEADY_UNSTEADY("EXTENDED_PRESSURE_TENDENCY_INCR_STEADY_UNSTEADY"),
    TENDENCY_DEACR_OR_STEADY_INCR("EXTENDED_PRESSURE_TENDENCY_DEACR_OR_STEADY_INCR"),
    TENDENCY_STEADY("EXTENDED_PRESSURE_TENDENCY_STEADY"),
    TENDENCY_DEACR_INCR("EXTENDED_PRESSURE_TENDENCY_DEACR_INCR"),
    TENDENCY_DEACR_STEADY_OR_DEACR_DEACR_SLOWLY("EXTENDED_PRESSURE_TENDENCY_DEACR_STEADY_OR_DEACR_DEACR_SLOWLY"),
    TENDENCY_DEACR_STEADY_UNSTEADY("EXTENDED_PRESSURE_TENDENCY_DEACR_STEADY_UNSTEADY"),
    TENDENCY_STEADY_INCR_DEACR("EXTENDED_PRESSURE_TENDENCY_STEADY_INCR_DEACR"),
    DECODED_TENDENCY_INCR_DEACR("EXTENDED_DECODED_PRESSURE_TENDENCY_INCR_DEACR"),
    DECODED_TENDENCY_INCR_STEADY_OR_INCR_INCR_SLOWLY("EXTENDED_DECODED_PRESSURE_TENDENCY_INCR_STEADY_OR_INCR_INCR_SLOWLY"),
    DECODED_TENDENCY_INCR_STEADY_UNSTEADY("EXTENDED_DECODED_PRESSURE_TENDENCY_INCR_STEADY_UNSTEADY"),
    DECODED_TENDENCY_DEACR_OR_STEADY_INCR("EXTENDED_DECODED_PRESSURE_TENDENCY_DEACR_OR_STEADY_INCR"),
    DECODED_TENDENCY_STEADY("EXTENDED_DECODED_PRESSURE_TENDENCY_STEADY"),
    DECODED_TENDENCY_DEACR_INCR("EXTENDED_DECODED_PRESSURE_TENDENCY_DEACR_INCR"),
    DECODED_TENDENCY_DEACR_STEADY_OR_DEACR_DEACR_SLOWLY("EXTENDED_DECODED_PRESSURE_TENDENCY_DEACR_STEADY_OR_DEACR_DEACR_SLOWLY"),
    DECODED_TENDENCY_DEACR_STEADY_UNSTEADY("EXTENDED_DECODED_PRESSURE_TENDENCY_DEACR_STEADY_UNSTEADY"),
    DECODED_TENDENCY_STEADY_INCR_DEACR("EXTENDED_DECODED_PRESSURE_TENDENCY_STEADY_INCR_DEACR");
    
    private final String key;
    
    PressureConfigKeys(String key) {
        this.key = key;
    }
    
    @Override
    public String getKey() {
        return key;
    }
}

enum SensorStatusConfigKeys implements ConfigKey {
    INDICATOR_RVRNO("EXTENDED_SENSOR_STATUS_INDICATOR_RVRNO"),
    INDICATOR_PWINO("EXTENDED_SENSOR_STATUS_INDICATOR_PWINO"),
    INDICATOR_PNO("EXTENDED_SENSOR_STATUS_INDICATOR_PNO"),
    INDICATOR_FZRANO("EXTENDED_SENSOR_STATUS_INDICATOR_FZRANO"),
    INDICATOR_TSNO("EXTENDED_SENSOR_STATUS_INDICATOR_TSNO"),
    INDICATOR_VISNO("EXTENDED_SENSOR_STATUS_INDICATOR_VISNO"),
    INDICATOR_CHINO("EXTENDED_SENSOR_STATUS_INDICATOR_CHINO"),
    DECODED_INDICATOR_RVRNO("EXTENDED_DECODED_SENSOR_STATUS_INDICATOR_RVRNO"),
    DECODED_INDICATOR_PWINO("EXTENDED_DECODED_SENSOR_STATUS_INDICATOR_PWINO"),
    DECODED_INDICATOR_PNO("EXTENDED_DECODED_SENSOR_STATUS_INDICATOR_PNO"),
    DECODED_INDICATOR_FZRANO("EXTENDED_DECODED_SENSOR_STATUS_INDICATOR_FZRANO"),
    DECODED_INDICATOR_TSNO("EXTENDED_DECODED_SENSOR_STATUS_INDICATOR_TSNO"),
    DECODED_INDICATOR_VISNO("EXTENDED_DECODED_SENSOR_STATUS_INDICATOR_VISNO"),
    DECODED_INDICATOR_CHINO("EXTENDED_DECODED_SENSOR_STATUS_INDICATOR_CHINO");
    
    private final String key;
    
    SensorStatusConfigKeys(String key) {
        this.key = key;
    }
    
    @Override
    public String getKey() {
        return key;
    }
}

/**
 * Class representing the Helper utility for getting config values
 *
 * Author: quark95cos Since: Copyright(c) 2025
 */
class ConfigHelper {
    
    /**
     * Private constructor to prevent instantiation of utility class
     *
     */
    private ConfigHelper() {
        // Utility class - not meant to be instantiated
    }

    /**
     * Gets configuration value for the given config key.
     * 
     * @param configKey the configuration key enum (cannot be null)
     * @return the configuration value from Configs.getInstance()
     * @throws NullPointerException if configKey is null
     */
    public static String getConfigValue(ConfigKey configKey) {
        Objects.requireNonNull(configKey, "Config key cannot be null");
        return Configs.getInstance().getString(configKey.getKey());
    }
}

/**
 * Class representing the handling of certain handlers
 *
 * Author: quark95cos Since: Copyright(c) 2022
 */
public final class WthItemHandlers {

    private static final Logger LOGGER
            = LogManager.getLogger(WthItemHandlers.class.getName());

    /**
     * Private constructor to prevent instantiation of utility class
     *
     */
    private WthItemHandlers() {
        // Utility class - not meant to be instantiated
    }

    /**
     * Parse the runway visual range weather information
     *
     * @return IndexedLinkedHashMap
     */
    public static final IndexedLinkedHashMap<String, String> setRVRWthItemsHandlers() {
        IndexedLinkedHashMap<String, String> rvrWthItemsHandlers;
        rvrWthItemsHandlers = new IndexedLinkedHashMap<>();

        rvrWthItemsHandlers.put(ConfigHelper.getConfigValue(RvrConfigKeys.REPORTABLE_ABOVE),
                ConfigHelper.getConfigValue(LocationTimeConfigKeys.DECODED_GREATER_THAN));
        rvrWthItemsHandlers.put(ConfigHelper.getConfigValue(RvrConfigKeys.REPORTABLE_BELOW),
                ConfigHelper.getConfigValue(LocationTimeConfigKeys.DECODED_LESS_THAN));
        rvrWthItemsHandlers.put(ConfigHelper.getConfigValue(MiscConfigKeys.VALUE_UL),
                ConfigHelper.getConfigValue(MiscConfigKeys.DECODED_VALUE_L));
        rvrWthItemsHandlers.put(ConfigHelper.getConfigValue(MiscConfigKeys.VALUE_UR),
                ConfigHelper.getConfigValue(MiscConfigKeys.DECODED_VALUE_R));
        rvrWthItemsHandlers.put(ConfigHelper.getConfigValue(MiscConfigKeys.VALUE_UC),
                ConfigHelper.getConfigValue(MiscConfigKeys.DECODED_VALUE_C));
        rvrWthItemsHandlers.put(ConfigHelper.getConfigValue(MiscConfigKeys.VALUE_UU),
                ConfigHelper.getConfigValue(MiscConfigKeys.DECODED_VALUE_U));
        rvrWthItemsHandlers.put(ConfigHelper.getConfigValue(MiscConfigKeys.VALUE_UD),
                ConfigHelper.getConfigValue(MiscConfigKeys.DECODED_VALUE_D));
        rvrWthItemsHandlers.put(ConfigHelper.getConfigValue(MiscConfigKeys.VALUE_UN),
                ConfigHelper.getConfigValue(MiscConfigKeys.DECODED_VALUE_N));

        LOGGER.debug("\n");
        LOGGER.debug("RVR Weather Items Handler");
        rvrWthItemsHandlers.forEach((k, v) -> LOGGER.debug((k + ":" + v)));
        LOGGER.debug("RVR Weather Items Handler" + "\n");

        return rvrWthItemsHandlers;
    }

    /**
     * Parse the sky condition weather information
     *
     * @return IndexedLinkedHashMap
     */
    public static final IndexedLinkedHashMap<String, String> setSkyCondWthItemsHandlers() {
        IndexedLinkedHashMap<String, String> skyCondWthItemsHandlers;
        skyCondWthItemsHandlers = new IndexedLinkedHashMap<>();

        skyCondWthItemsHandlers.put(ConfigHelper.getConfigValue(SkyConditionConfigKeys.VERTICAL_VISIBILITY),
                ConfigHelper.getConfigValue(SkyConditionConfigKeys.DECODED_VERTICAL_VISIBILITY));
        skyCondWthItemsHandlers.put(ConfigHelper.getConfigValue(SkyConditionConfigKeys.SKY_CLEAR),
                ConfigHelper.getConfigValue(SkyConditionConfigKeys.DECODED_SKY_CLEAR));
        skyCondWthItemsHandlers.put(ConfigHelper.getConfigValue(SkyConditionConfigKeys.CLEAR),
                ConfigHelper.getConfigValue(SkyConditionConfigKeys.DECODED_CLEAR));
        skyCondWthItemsHandlers.put(ConfigHelper.getConfigValue(SkyConditionConfigKeys.FEW),
                ConfigHelper.getConfigValue(SkyConditionConfigKeys.DECODED_FEW));
        skyCondWthItemsHandlers.put(ConfigHelper.getConfigValue(SkyConditionConfigKeys.SCATTERED),
                ConfigHelper.getConfigValue(SkyConditionConfigKeys.DECODED_SCATTERED));
        skyCondWthItemsHandlers.put(ConfigHelper.getConfigValue(SkyConditionConfigKeys.BROKEN),
                ConfigHelper.getConfigValue(SkyConditionConfigKeys.DECODED_BROKEN));
        skyCondWthItemsHandlers.put(ConfigHelper.getConfigValue(SkyConditionConfigKeys.OVERCAST),
                ConfigHelper.getConfigValue(SkyConditionConfigKeys.DECODED_OVERCAST));
        skyCondWthItemsHandlers.put(ConfigHelper.getConfigValue(SkyConditionConfigKeys.NO_CLOUDS_DETECTED),
                ConfigHelper.getConfigValue(SkyConditionConfigKeys.DECODED_NO_CLOUDS_DETECTED));
        skyCondWthItemsHandlers.put(ConfigHelper.getConfigValue(SkyConditionConfigKeys.NO_SIGNIFICANT_CLOUDS),
                ConfigHelper.getConfigValue(SkyConditionConfigKeys.DECODED_NO_SIGNIFICANT_CLOUDS));
        skyCondWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.CUMULONIMBUS),
                ConfigHelper.getConfigValue(CloudConfigKeys.DECODED_CUMULONIMBUS));
        skyCondWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.TOWERING_CUMULUS),
                ConfigHelper.getConfigValue(CloudConfigKeys.DECODED_TOWERING_CUMULUS));

        LOGGER.debug("\n");
        LOGGER.debug("Sky Condition Weather Items Handler");
        skyCondWthItemsHandlers.forEach((k, v) -> LOGGER.debug((k + ":" + v)));
        LOGGER.debug("Sky Condition Weather Items Handler" + "\n");

        return skyCondWthItemsHandlers;
    }

    /**
     * Parse the weather condition information
     *
     * @return IndexedLinkedHashMap
     */
    public static final IndexedLinkedHashMap<String, String> setWeathCondWthItemsHandlers() {
        IndexedLinkedHashMap<String, String> weathCondWthItemsHandlers;
        weathCondWthItemsHandlers = new IndexedLinkedHashMap<>();

        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.LIGHT),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_LIGHT));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.HEAVY),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_HEAVY));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.SHALLOW),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_SHALLOW));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.PARTIAL),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_PARTIAL));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.PATCHES),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_PATCHES));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.LOW_DRIFTING),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_LOW_DRIFTING));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.BLOWING),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_BLOWING));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.SHOWERS),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_SHOWERS));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.THUNDERSTORMS),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_THUNDERSTORMS));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.FREEZING),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_FREEZING));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.DRIZZLE),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_DRIZZLE));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.RAIN),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_RAIN));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.SNOW),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_SNOW));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.SNOW_GRAINS),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_SNOW_GRAINS));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.ICE_CRYSTALS),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_ICE_CRYSTALS));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.ICE_PELLETS),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_ICE_PELLETS));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.HAIL),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_HAIL));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.SMALL_HAIL),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_SMALL_HAIL));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.UNKNOWN_PRECIPITATION),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_UNKNOWN_PRECIP));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.THUNDERSTORMS),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_THUNDERSTORMS));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.DUST_SAND_WHIRLS),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_DUST_SAND_WHIRLS));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.SQUALLS),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_SQUALLS));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.FUNNEL_CLOUD),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_FUNNEL_CLOUD));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.SAND_STORM),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_SAND_STORM));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.DUST_STORM),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_DUST_STORM));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.MIST),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_MIST));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.FOG),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_FOG));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.SMOKE),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_SMOKE));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.VOLCANIC_ASH),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_VOLCANIC_ASH));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.WIDESPREAD_DUST),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_WIDESPREAD_DUST));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.SAND),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_SAND));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.HAZE),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_HAZE));
        weathCondWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.SPRAY),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_SPRAY));

        LOGGER.debug("\n");
        LOGGER.debug("Weather Condition Items Handler");
        weathCondWthItemsHandlers.forEach((k, v) -> LOGGER.debug((k + ":" + v)));
        LOGGER.debug("Weather Condition Items Handler" + "\n");

        return weathCondWthItemsHandlers;
    }

    /**
     * Parse the remarks weather information
     *
     * @return IndexedLinkedHashMap
     */
    public static final IndexedLinkedHashMap<String, String> setRemarksWthItemsHandlers() {
        IndexedLinkedHashMap<String, String> remarkWthItemsHandlers;
        remarkWthItemsHandlers = new IndexedLinkedHashMap<>();

        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(PressureConfigKeys.FALLING),
                ConfigHelper.getConfigValue(PressureConfigKeys.DECODED_FALLING_RAPIDLY));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(PressureConfigKeys.RISING),
                ConfigHelper.getConfigValue(PressureConfigKeys.DECODED_RISING_RAPIDLY));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(ExtendedConfigKeys.TORNADO),
                ConfigHelper.getConfigValue(ExtendedConfigKeys.DECODED_TORNADO));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(ExtendedConfigKeys.FUNNEL_CLOUD),
                ConfigHelper.getConfigValue(ExtendedConfigKeys.DECODED_FUNNEL_CLOUD));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(ExtendedConfigKeys.WATERSPOUT),
                ConfigHelper.getConfigValue(ExtendedConfigKeys.DECODED_WATERSPOUT));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(LocationTimeConfigKeys.BEGIN),
                ConfigHelper.getConfigValue(LocationTimeConfigKeys.DECODED_BEGIN));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(LocationTimeConfigKeys.END),
                ConfigHelper.getConfigValue(LocationTimeConfigKeys.DECODED_ENDING));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(ExtendedConfigKeys.ICING),
                ConfigHelper.getConfigValue(ExtendedConfigKeys.DECODED_ICING));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.IN_CLD),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_IN_CLD));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.IN_PRECIPITATION),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_IN_PRECIPITATION));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(ExtendedConfigKeys.AUTO_A01),
                ConfigHelper.getConfigValue(ExtendedConfigKeys.DECODED_AUTO_AO1));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(ExtendedConfigKeys.AUTO_AO1),
                ConfigHelper.getConfigValue(ExtendedConfigKeys.DECODED_AUTO_AO1));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(ExtendedConfigKeys.AUTO_A02),
                ConfigHelper.getConfigValue(ExtendedConfigKeys.DECODED_AUTO_AO2));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(ExtendedConfigKeys.AUTO_AO2),
                ConfigHelper.getConfigValue(ExtendedConfigKeys.DECODED_AUTO_AO2));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(LocationTimeConfigKeys.OHD),
                ConfigHelper.getConfigValue(LocationTimeConfigKeys.DECODED_OHD));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(LocationTimeConfigKeys.VC),
                ConfigHelper.getConfigValue(LocationTimeConfigKeys.DECODED_VC));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(LocationTimeConfigKeys.DISTANT),
                ConfigHelper.getConfigValue(LocationTimeConfigKeys.DECODED_DISTANT));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.THUNDERSTORMS),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_THUNDERSTORMS));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.CUMULONIMBUS),
                ConfigHelper.getConfigValue(CloudConfigKeys.DECODED_CUMULONIMBUS));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.TOWERING_CUMULUS),
                ConfigHelper.getConfigValue(CloudConfigKeys.DECODED_TOWERING_CUMULUS));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.ALTOCUMULUS_CASTELLANUS),
                ConfigHelper.getConfigValue(CloudConfigKeys.DECODED_ALTOCUMULUS_CASTELLANUS));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.CUMULONIMBUS_MAMMATUS),
                ConfigHelper.getConfigValue(CloudConfigKeys.DECODED_CUMULONIMBUS_MAMMATUS));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.VIRGA),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_VIRGA));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(LocationTimeConfigKeys.OHD),
                ConfigHelper.getConfigValue(LocationTimeConfigKeys.DECODED_OHD));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(LocationTimeConfigKeys.VC),
                ConfigHelper.getConfigValue(LocationTimeConfigKeys.DECODED_VC));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(LocationTimeConfigKeys.DECODED_DISTANT),
                ConfigHelper.getConfigValue(LocationTimeConfigKeys.DECODED_DISTANT));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(LocationTimeConfigKeys.DISIPATED),
                ConfigHelper.getConfigValue(LocationTimeConfigKeys.DECODED_DISIPATED));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(LocationTimeConfigKeys.TOP),
                ConfigHelper.getConfigValue(LocationTimeConfigKeys.DECODED_TOP));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.TRACE),
                ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_TRACE));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(ExtendedConfigKeys.SIX_HOUR_PRECIPITATION_AMOUNT),
                ConfigHelper.getConfigValue(ExtendedConfigKeys.DECODED_6_HOUR_PRECIPITATION));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(ExtendedConfigKeys.TWENTY_FOUR_HOUR_PRECIPITATION_AMOUNT),
                ConfigHelper.getConfigValue(ExtendedConfigKeys.DECODED_24_HOUR_PRECIPITATION));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(PressureConfigKeys.TENDENCY_INCR_DEACR),
                ConfigHelper.getConfigValue(PressureConfigKeys.DECODED_TENDENCY_INCR_DEACR));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(PressureConfigKeys.TENDENCY_INCR_STEADY_OR_INCR_INCR_SLOWLY),
                ConfigHelper.getConfigValue(PressureConfigKeys.DECODED_TENDENCY_INCR_STEADY_OR_INCR_INCR_SLOWLY));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(PressureConfigKeys.TENDENCY_INCR_STEADY_UNSTEADY),
                ConfigHelper.getConfigValue(PressureConfigKeys.DECODED_TENDENCY_INCR_STEADY_UNSTEADY));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(PressureConfigKeys.TENDENCY_DEACR_OR_STEADY_INCR),
                ConfigHelper.getConfigValue(PressureConfigKeys.DECODED_TENDENCY_DEACR_OR_STEADY_INCR));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(PressureConfigKeys.TENDENCY_STEADY),
                ConfigHelper.getConfigValue(PressureConfigKeys.DECODED_TENDENCY_STEADY));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(PressureConfigKeys.TENDENCY_DEACR_INCR),
                ConfigHelper.getConfigValue(PressureConfigKeys.DECODED_TENDENCY_DEACR_INCR));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(PressureConfigKeys.TENDENCY_DEACR_STEADY_OR_DEACR_DEACR_SLOWLY),
                ConfigHelper.getConfigValue(PressureConfigKeys.DECODED_TENDENCY_DEACR_STEADY_OR_DEACR_DEACR_SLOWLY));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(PressureConfigKeys.TENDENCY_DEACR_STEADY_UNSTEADY),
                ConfigHelper.getConfigValue(PressureConfigKeys.DECODED_TENDENCY_DEACR_STEADY_UNSTEADY));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(PressureConfigKeys.TENDENCY_STEADY_INCR_DEACR),
                ConfigHelper.getConfigValue(PressureConfigKeys.DECODED_TENDENCY_STEADY_INCR_DEACR));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.OKTA_1),
                ConfigHelper.getConfigValue(SkyConditionConfigKeys.DECODED_FEW));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.OKTA_2),
                ConfigHelper.getConfigValue(SkyConditionConfigKeys.DECODED_FEW));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.OKTA_3),
                ConfigHelper.getConfigValue(SkyConditionConfigKeys.DECODED_SCATTERED));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.OKTA_4),
                ConfigHelper.getConfigValue(SkyConditionConfigKeys.DECODED_SCATTERED));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.OKTA_5),
                ConfigHelper.getConfigValue(SkyConditionConfigKeys.DECODED_BROKEN));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.OKTA_6),
                ConfigHelper.getConfigValue(SkyConditionConfigKeys.DECODED_BROKEN));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.OKTA_7),
                ConfigHelper.getConfigValue(SkyConditionConfigKeys.DECODED_BROKEN));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.OKTA_8),
                ConfigHelper.getConfigValue(SkyConditionConfigKeys.DECODED_OVERCAST));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.OKTA_ALQDS),
                ConfigHelper.getConfigValue(SkyConditionConfigKeys.DECODED_ALQDS));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.OKTA_OHD_ALQDS),
                ConfigHelper.getConfigValue(SkyConditionConfigKeys.DECODED_OHD_ALQDS));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.CUMULUS),
                ConfigHelper.getConfigValue(CloudConfigKeys.DECODED_CUMULUS));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.CUMULONIMBUS),
                ConfigHelper.getConfigValue(CloudConfigKeys.DECODED_CUMULONIMBUS));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.CUMULUS_FRACTUS),
                ConfigHelper.getConfigValue(CloudConfigKeys.DECODED_CUMULUS_FRACTUS));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.STRATUS),
                ConfigHelper.getConfigValue(CloudConfigKeys.DECODED_STRATUS));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.STRATUS_FRACTUS),
                ConfigHelper.getConfigValue(CloudConfigKeys.DECODED_STRATUS_FRACTUS));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.STRATOCUMULUS),
                ConfigHelper.getConfigValue(CloudConfigKeys.DECODED_STRATOCUMULUS));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.NIMBOSTRATUS),
                ConfigHelper.getConfigValue(CloudConfigKeys.DECODED_NIMBOSTRATUS));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.ALTOSTRATUS),
                ConfigHelper.getConfigValue(CloudConfigKeys.DECODED_ALTOSTRATUS));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.ALTOCUMULUS),
                ConfigHelper.getConfigValue(CloudConfigKeys.DECODED_ALTOCUMULUS));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.CIRROSTRATUS),
                ConfigHelper.getConfigValue(CloudConfigKeys.DECODED_CIRROSTRATUS));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.CIRROCUMULUS),
                ConfigHelper.getConfigValue(CloudConfigKeys.DECODED_CIRROCUMULUS));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(CloudConfigKeys.CIRRUS),
                ConfigHelper.getConfigValue(CloudConfigKeys.DECODED_CIRRUS));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(LocationTimeConfigKeys.MOVG),
                ConfigHelper.getConfigValue(LocationTimeConfigKeys.DECODED_MOVNG));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(SensorStatusConfigKeys.INDICATOR_RVRNO),
                ConfigHelper.getConfigValue(SensorStatusConfigKeys.DECODED_INDICATOR_RVRNO));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(SensorStatusConfigKeys.INDICATOR_PWINO),
                ConfigHelper.getConfigValue(SensorStatusConfigKeys.DECODED_INDICATOR_PWINO));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(SensorStatusConfigKeys.INDICATOR_PNO),
                ConfigHelper.getConfigValue(SensorStatusConfigKeys.DECODED_INDICATOR_PNO));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(SensorStatusConfigKeys.INDICATOR_FZRANO),
                ConfigHelper.getConfigValue(SensorStatusConfigKeys.DECODED_INDICATOR_FZRANO));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(SensorStatusConfigKeys.INDICATOR_TSNO),
                ConfigHelper.getConfigValue(SensorStatusConfigKeys.DECODED_INDICATOR_TSNO));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(SensorStatusConfigKeys.INDICATOR_VISNO),
                ConfigHelper.getConfigValue(SensorStatusConfigKeys.DECODED_INDICATOR_VISNO));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(SensorStatusConfigKeys.INDICATOR_CHINO),
                ConfigHelper.getConfigValue(SensorStatusConfigKeys.DECODED_INDICATOR_CHINO));
        remarkWthItemsHandlers.put(ConfigHelper.getConfigValue(ExtendedConfigKeys.MAINTENANCE_CHECK_INDICATOR),
                ConfigHelper.getConfigValue(ExtendedConfigKeys.DECODED_MAINTENANCE_CHECK_INDICATOR));

        LOGGER.debug("\n");
        LOGGER.debug("Remarks Weather Items Handler");
        remarkWthItemsHandlers.forEach((k, v) -> LOGGER.debug((k + ":" + v)));
        LOGGER.debug("Remarks Weather Items Handler" + "\n");

        return remarkWthItemsHandlers;
    }

    /**
     * Parse the remarks weather alternate information
     *
     * @return IndexedLinkedHashMap
     */
    public static final IndexedLinkedHashMap<String, Pair<String, String>> setRemarksWthAltItemsHandlers() {
        IndexedLinkedHashMap<String, Pair<String, String>> remarkWthAltItemsHandlers;
        remarkWthAltItemsHandlers = new IndexedLinkedHashMap<>();

        remarkWthAltItemsHandlers.put(ConfigHelper.getConfigValue(LocationTimeConfigKeys.OCCASIONAL),
                Pair.with(ConfigHelper.getConfigValue(LocationTimeConfigKeys.DECODED_OCCASIONAL),
                        ConfigHelper.getConfigValue(ExtendedConfigKeys.DECODED_LTNG_FREQ_LESS_1)));
        remarkWthAltItemsHandlers.put(ConfigHelper.getConfigValue(LocationTimeConfigKeys.FREQUENT),
                Pair.with(ConfigHelper.getConfigValue(LocationTimeConfigKeys.DECODED_FREQUENT),
                        ConfigHelper.getConfigValue(ExtendedConfigKeys.DECODED_LTNG_FREQ_1_TO_6)));
        remarkWthAltItemsHandlers.put(ConfigHelper.getConfigValue(LocationTimeConfigKeys.CONTINUOUS),
                Pair.with(ConfigHelper.getConfigValue(LocationTimeConfigKeys.DECODED_CONTINUOUS),
                        ConfigHelper.getConfigValue(ExtendedConfigKeys.DECODED_LTNG_FREQ_MORE_6)));
        remarkWthAltItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.IN_CLD),
                Pair.with(ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_IN_CLD),
                        ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_LIGHTNING_OBSERVED)));
        remarkWthAltItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.CLD_TO_CLD),
                Pair.with(ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_CLD_TO_CLD),
                        ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_LIGHTNING_OBSERVED)));
        remarkWthAltItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.CLD_GRND),
                Pair.with(ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_CLD_GRND),
                        ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_LIGHTNING_OBSERVED)));
        remarkWthAltItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.CLD_TO_AIR),
                Pair.with(ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_CLD_TO_AIR),
                        ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_LIGHTNING_OBSERVED)));
        remarkWthAltItemsHandlers.put(ConfigHelper.getConfigValue(WeatherConfigKeys.CLD_TO_WATER),
                Pair.with(ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_CLD_TO_WATER),
                        ConfigHelper.getConfigValue(WeatherConfigKeys.DECODED_LIGHTNING_OBSERVED)));

        LOGGER.debug("\n");
        LOGGER.debug("Remarks Weather Alternate Items Handler");
        remarkWthAltItemsHandlers.forEach((k, v) -> LOGGER.debug((k + ":" + v)));
        LOGGER.debug("Remarks Weather Alternate Items Handler" + "\n");

        return remarkWthAltItemsHandlers;
    }
}
