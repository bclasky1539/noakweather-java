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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import noakweather.noaa_api.common.SkyCondition;
import noakweather.noaa_api.common.WeatherCondition;
import noakweather.noaa_api.weather.Pressure;
import noakweather.noaa_api.weather.Temperature;
import noakweather.noaa_api.weather.Visibility;
import noakweather.noaa_api.weather.Wind;
import noakweather.utils.Configs;
import noakweather.utils.IndexedLinkedHashMap;
import noakweather.utils.UtilsDate;
import noakweather.utils.UtilsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javatuples.Pair;

/**
 * Class representing the base class of the Prob and Tempo classes
 *
 * Author: quark95cos Since: Copyright(c) 2022
 */
public class Group {

    private int skyCondIndex;
    private final boolean isValidFromToDate;
    private String monthString;
    private String yearString;
    private Date validFromDate;
    private Date validToDate;
    private Wind windGroup;
    private Visibility visibilityGroup;
    private Temperature temperatureGroup;
    private Pressure pressureGroup;
    private WeatherCondition weatherConditionGroup;
    private IndexedLinkedHashMap<WeatherCondition, String> weatherConditionsGroup;
    private SkyCondition skyConditionGroup;
    private IndexedLinkedHashMap<SkyCondition, String> skyConditionsGroup;
    private ArrayList<String> parseString = new ArrayList<>();

    private static final Logger LOGGER
            = LogManager.getLogger(Group.class.getName());

    // Logging constants for parseGroupHandlers method
    private static final String MSG_MATCH_DECODED_TOKEN_PROCESSING = 
        Configs.getInstance().getString("MATCH_DECODED_TOKEN_PROCESSING");
    private static final String MSG_MATCH_DECODED_PATTERN_I = 
        Configs.getInstance().getString("MATCH_DECODED_PATTERN_I");
    private static final String MSG_MATCH_DECODED_MATCHER_GROUP_CNT = 
        Configs.getInstance().getString("MATCH_DECODED_MATCHER_GROUP_CNT");
    private static final String MSG_MATCH_DECODED_MATCHER_GROUP_0 = 
        Configs.getInstance().getString("MATCH_DECODED_MATCHER_GROUP_0");
    private static final String MSG_MATCH_DECODED_CAPTURE_GROUP_NUMBER = 
        Configs.getInstance().getString("MATCH_DECODED_CAPTURE_GROUP_NUMBER");
    private static final String MSG_MATCH_DECODED_CAPTURED_TEXT = 
        Configs.getInstance().getString("MATCH_DECODED_CAPTURED_TEXT");
    private static final String MSG_MATCH_DECODED_TOKEN_AFTER_LAST_MATCH = 
        Configs.getInstance().getString("MATCH_DECODED_TOKEN_AFTER_LAST_MATCH");
    private static final String MSG_MATCH_DECODED_PATTERN = 
        Configs.getInstance().getString("MATCH_DECODED_PATTERN");

    // Logging constants for detGroupItems method
    private static final String MSG_LOG_DECODED_FOUND_VALID_TO_FROM_TP = 
        Configs.getInstance().getString("LOG_DECODED_FOUND_VALID_TO_FROM_TP");
    private static final String MSG_LOG_DECODED_FOUND_WIND = 
        Configs.getInstance().getString("LOG_DECODED_FOUND_WIND");
    private static final String MSG_LOG_DECODED_FOUND_VISIBILITY = 
        Configs.getInstance().getString("LOG_DECODED_FOUND_VISIBILITY");
    private static final String MSG_LOG_DECODED_FOUND_RVR = 
        Configs.getInstance().getString("LOG_DECODED_FOUND_RVR");
    private static final String MSG_LOG_DECODED_FOUND_WEATHER_GROUPS = 
        Configs.getInstance().getString("LOG_DECODED_FOUND_WEATHER_GROUPS");
    private static final String MSG_LOG_DECODED_WEATHER_CONDITIONS = 
        Configs.getInstance().getString("LOG_DECODED_WEATHER_CONDITIONS");
    private static final String MSG_LOG_DECODED_FOUND_SKY_CONDITIONS = 
        Configs.getInstance().getString("LOG_DECODED_FOUND_SKY_CONDITIONS");
    private static final String MSG_LOG_DECODED_SKY_CONDITIONS = 
        Configs.getInstance().getString("LOG_DECODED_SKY_CONDITIONS");
    private static final String MSG_LOG_DECODED_FOUND_TEMPERATURE_DEWPOINT = 
        Configs.getInstance().getString("LOG_DECODED_FOUND_TEMPERATURE_DEWPOINT");
    private static final String MSG_LOG_DECODED_FOUND_PRESSURE = 
        Configs.getInstance().getString("LOG_DECODED_FOUND_PRESSURE");
    private static final String MSG_LOG_DECODED_FOUND_NO_SIGNIFICANT_CHANGE = 
        Configs.getInstance().getString("LOG_DECODED_FOUND_NO_SIGNIFICANT_CHANGE");
    private static final String MSG_LOG_DECODED_FOUND_UNPARSED_DATA = 
        Configs.getInstance().getString("LOG_DECODED_FOUND_UNPARSED_DATA");

    // Logging constants for date/time methods
    private static final String MSG_LOC_TIME_DECODED_VALID_FROM_DATE = 
        Configs.getInstance().getString("LOC_TIME_DECODED_VALID_FROM_DATE");
    private static final String MSG_LOC_TIME_DECODED_VALID_TO_DATE = 
        Configs.getInstance().getString("LOC_TIME_DECODED_VALID_TO_DATE");
    private static final String MSG_LOC_TIME_DECODED_UNABLE_PARSE_DATE_VALUE = 
        Configs.getInstance().getString("LOC_TIME_DECODED_UNABLE_PARSE_DATE_VALUE");

    // Constants for getNaturalLanguageString method
    private static final String MSG_EXTENDED_DECODED_FM = 
        Configs.getInstance().getString("EXTENDED_DECODED_FM");
    private static final String MSG_WEATHER_DECODED_CAVOK = 
        Configs.getInstance().getString("WEATHER_DECODED_CAVOK");
    private static final String MSG_MSRMNT_DECODED_MILES = 
        Configs.getInstance().getString("MSRMNT_DECODED_MILES");
    private static final String MSG_MSRMNT_DECODED_KILOMETERS = 
        Configs.getInstance().getString("MSRMNT_DECODED_KILOMETERS");
    
    // String literal constants for switch cases and method parameters
    private static final String TYPE_UNPARSED = "unparsed";

    // Named group constants for regex groups
    private static final String GROUP_UNPARSED = "unparsed";
    private static final String GROUP_EVALTIME = "evaltime";

    /**
     * Constructor
     */
    public Group() {
        LOGGER.debug("in Group constructor");
        this.skyCondIndex = 0;
        this.isValidFromToDate = false;
        this.monthString = "";
        this.yearString = "";
        this.validFromDate = null;
        this.validToDate = null;
        this.windGroup = null;
        this.visibilityGroup = null;
        this.temperatureGroup = null;
        this.pressureGroup = null;
        this.weatherConditionGroup = null;
        this.weatherConditionsGroup = null;
        this.skyConditionGroup = null;
        this.skyConditionsGroup = null;
        this.parseString = null;
    }

    /**
    * Parse the group handlers information - Refactored for reduced cognitive complexity
    *
    * @param token the weather token to be parsed
    * @param handlers map of regex patterns to their corresponding handler information
    * @throws UtilsException if error occurs during pattern processing
    */
    protected void parseGroupHandlers(String token, IndexedLinkedHashMap<Pattern, Pair<String, Boolean>> handlers) throws UtilsException {
        String remainingToken = token;
        
        while (!remainingToken.isEmpty()) {
            logTokenProcessing(remainingToken);
            String processedToken = processAllPatterns(remainingToken, handlers);
            
            // Prevent infinite loop - if no progress made, break
            if (processedToken.equals(remainingToken)) {
                LOGGER.warn("No pattern matched token: #{}, stopping processing", remainingToken);
                break;
            }
            
            remainingToken = processedToken;
        }
    }

    /**
     * Log token processing information
     *
     * @param token the current token being processed
     */
    private void logTokenProcessing(String token) {
        LOGGER.debug("\n");
        LOGGER.debug("{}#{}", MSG_MATCH_DECODED_TOKEN_PROCESSING, token);
    }

    /**
     * Process all patterns against the token and return the modified token
     *
     * @param token the token to process against all patterns
     * @param handlers map of regex patterns to their corresponding handler information
     * @return the modified token after pattern processing, or original token if no matches
     * @throws UtilsException if error occurs during pattern processing
     */
    private String processAllPatterns(String token, IndexedLinkedHashMap<Pattern, Pair<String, Boolean>> handlers) throws UtilsException {
        for (Pattern pattern : handlers.keySet()) {
            String modifiedToken = processSinglePattern(token, pattern, handlers.get(pattern));
            if (!modifiedToken.equals(token)) {
                return modifiedToken; // Pattern matched and modified token
            }
        }
        return token; // No patterns matched
    }

    /**
     * Process a single pattern against the token
     *
     * @param token the token to match against the pattern
     * @param pattern the regex pattern to apply
     * @param handlerInfo the handler information containing type and continuation flag
     * @return the modified token after pattern processing, or original token if no match
     * @throws UtilsException if error occurs during pattern processing
     */
    private String processSinglePattern(String token, Pattern pattern, Pair<String, Boolean> handlerInfo) throws UtilsException {
        logPatternProcessing(pattern, handlerInfo);
        
        Matcher matcher = pattern.matcher(token);
        if (!matcher.find()) {
            return token; // No match found
        }
        
        return processMatches(token, matcher, handlerInfo);
    }

    /**
     * Log pattern processing information
     *
     * @param pattern the regex pattern being processed
     * @param handlerInfo the handler information for this pattern
     */
    private void logPatternProcessing(Pattern pattern, Pair<String, Boolean> handlerInfo) {
        LOGGER.debug("{}#{} #{}", MSG_MATCH_DECODED_PATTERN_I, pattern, handlerInfo);
    }

    /**
     * Process all matches for a pattern and return modified token
     *
     * @param token the original token to process
     * @param matcher the regex matcher containing the match results
     * @param handlerInfo the handler information containing type and continuation flag
     * @return the modified token after processing all matches
     * @throws UtilsException if error occurs during match processing
     */
    private String processMatches(String token, Matcher matcher, Pair<String, Boolean> handlerInfo) throws UtilsException {
        String modifiedToken = token;
        
        do {
            if (isEmptyMatch(matcher)) {
                break;
            }
            
            logMatchDetails(matcher);
            detGroupItems(handlerInfo.getValue0(), matcher);
            
            modifiedToken = updateTokenAfterMatch(matcher);
            logTokenAfterMatch(modifiedToken, handlerInfo);
            
            if (!shouldContinueMatching(handlerInfo)) {
                break;
            }
            
            matcher = matcher.pattern().matcher(modifiedToken);
            
        } while (matcher.find());
        
        return modifiedToken;
    }

    /**
     * Check if the match is empty (which could cause infinite loops)
     *
     * @param matcher the regex matcher to check
     * @return true if the match is empty, false otherwise
     */
    private boolean isEmptyMatch(Matcher matcher) {
        return matcher.group(0).isEmpty();
    }

    /**
     * Log match details including group count and captured groups
     *
     * @param matcher the regex matcher containing match information
     */
    private void logMatchDetails(Matcher matcher) {
        LOGGER.debug("{} {}", MSG_MATCH_DECODED_MATCHER_GROUP_CNT, matcher.groupCount());
        LOGGER.debug("{}#{}", MSG_MATCH_DECODED_MATCHER_GROUP_0, matcher.group(0));
        
        logCapturedGroups(matcher);
    }

    /**
     * Log all captured groups
     *
     * @param matcher the regex matcher containing captured group information
     */
    private void logCapturedGroups(Matcher matcher) {
        for (int j = 1; j <= matcher.groupCount(); j++) {
            LOGGER.debug("{} {} {} {} #{}", 
            MSG_MATCH_DECODED_CAPTURE_GROUP_NUMBER,
            MSG_MATCH_DECODED_CAPTURE_GROUP_NUMBER,
            j,
            MSG_MATCH_DECODED_CAPTURED_TEXT,
            matcher.group(j));
        }
    }

    /**
     * Update token after a successful match
     *
     * @param token the original token
     * @param matcher the regex matcher used to replace the matched portion
     * @return the updated token with matched portion removed and properly formatted
     */
    private String updateTokenAfterMatch(Matcher matcher) {
        String updatedToken = matcher.replaceFirst("").trim();
        
        // Add space if token still has content to ensure proper processing
        if (updatedToken.length() > 0) {
            updatedToken += " ";
        }
        
        return updatedToken;
    }

    /**
     * Log token state after match processing
     *
     * @param token the token after match processing
     * @param handlerInfo the handler information for the current pattern
     */
    private void logTokenAfterMatch(String token, Pair<String, Boolean> handlerInfo) {
        LOGGER.debug("{}#{}", MSG_MATCH_DECODED_TOKEN_AFTER_LAST_MATCH, token);
        LOGGER.debug("{} {}", MSG_MATCH_DECODED_PATTERN, handlerInfo.getValue1());
    }

    /**
     * Determine if pattern matching should continue based on handler configuration
     *
     * @param handlerInfo the handler information containing continuation flag
     * @return true if pattern matching should continue, false otherwise
     */
    private boolean shouldContinueMatching(Pair<String, Boolean> handlerInfo) {
        return !handlerInfo.getValue1().equals(false);
    }

    /**
     * Determine the group (BECMG, TEMPO , PROB and FM)
     *
     * @param type
     * @param value
     */
    private void detGroupItems(String type, Matcher value) throws UtilsException {
        switch (type) {
            case "valtmper":
                // We have a valid to and from time period
                LOGGER.debug(MSG_LOG_DECODED_FOUND_VALID_TO_FROM_TP);
                setValidToFromDateInfo(value);
                break;
            case "wind":
                // We have a wind
                LOGGER.debug(MSG_LOG_DECODED_FOUND_WIND);
                if (windGroup == null) {
                    windGroup = new Wind();
                }
                windGroup.setMainWindItems(value, 'M');
                break;
            case "visibility":
                // We have visibility
                LOGGER.debug(MSG_LOG_DECODED_FOUND_VISIBILITY);
                if (visibilityGroup == null) {
                    visibilityGroup = new Visibility();
                }
                visibilityGroup.setVisibilityItems(value);
                break;
            case "runway":
                // We have Runaway group
                LOGGER.debug(MSG_LOG_DECODED_FOUND_RVR);
                break;
            case "presentweather":
                // We have Present Weather
                LOGGER.debug(MSG_LOG_DECODED_FOUND_WEATHER_GROUPS);
                if (weatherConditionsGroup == null) {
                    weatherConditionsGroup = new IndexedLinkedHashMap<>();
                }
                weatherConditionGroup = new WeatherCondition();
                weatherConditionGroup.setWeatherConditionItems(value);
                weatherConditionsGroup.put(weatherConditionGroup,
                        weatherConditionGroup.getNaturalLanguageString());
                LOGGER.debug("{} {}", MSG_LOG_DECODED_WEATHER_CONDITIONS,
                    weatherConditionGroup.getNaturalLanguageString());
                break;
            case "skycondition":
                // We have a sky condition
                LOGGER.debug(MSG_LOG_DECODED_FOUND_SKY_CONDITIONS);
                if (skyConditionsGroup == null) {
                    skyConditionsGroup = new IndexedLinkedHashMap<>();
                }
                skyConditionGroup = new SkyCondition();
                skyConditionGroup.setSkyConditionItems(value);
                skyCondIndex++;
                skyConditionsGroup.put(skyConditionGroup,
                        String.valueOf(skyCondIndex) + " "
                        + skyConditionGroup.getNaturalLanguageString());
                LOGGER.debug("{} {}", MSG_LOG_DECODED_SKY_CONDITIONS,
                    skyConditionGroup.getNaturalLanguageString());
                break;
            case "tempdewpoint":
                // We have Temperature / Dew Point
                LOGGER.debug(MSG_LOG_DECODED_FOUND_TEMPERATURE_DEWPOINT);
                if (temperatureGroup == null) {
                    temperatureGroup = new Temperature();
                }
                temperatureGroup.setTemperatureItems(value);
                break;
            case "altimeter":
                // We have pressure
                LOGGER.debug(MSG_LOG_DECODED_FOUND_PRESSURE);
                if (pressureGroup == null) {
                    pressureGroup = new Pressure();
                }
                pressureGroup.setPressureItems(value);
                break;
            case "nosigchng":
                // We have no significant change
                LOGGER.debug(MSG_LOG_DECODED_FOUND_NO_SIGNIFICANT_CHANGE);
                break;
            case TYPE_UNPARSED:
                // We have Unparsed Data
                LOGGER.debug(MSG_LOG_DECODED_FOUND_UNPARSED_DATA);
                if (parseString == null) {
                    parseString = new ArrayList<>();
                }
                setParseString(value);
                break;
            default:
                break;
        }
    }

    /**
     * Set the valid from date information
     *
     * @param token
     */
    public void setValidFromDate(String token) {
        if (token != null) {
            try {
                Group.this.setValidFromDate(UtilsDate.setDate(token.substring(0, 2),
                        token.substring(2, 4), token.substring(4, 6),
                        monthString, yearString));
                LOGGER.debug("{} {}\n", MSG_LOC_TIME_DECODED_VALID_FROM_DATE, getValidFromDate());
            } catch (NumberFormatException | UtilsException e) {
                String errMsg = Configs.getInstance()
                        .getString("LOC_TIME_DECODED_UNABLE_PARSE_DATE_VALUE") + " " + e;
                LOGGER.error(errMsg);
            }
        }
    }

    /**
     * Set the valid to and from date information
     *
     * @param token
     */
    protected void setValidToFromDateInfo(Matcher token) {
        if (token.group("bvaltime") != null && token.group(GROUP_EVALTIME) != null) {
            // 1918/2018 means it is valid from the 19th 1800Z to the 20th 1800Z
            try {
                setValidFromDate(UtilsDate.setDate(token.group("bvaltime").substring(0, 2),
                        token.group("bvaltime").substring(2, 4), "00",
                        monthString, yearString));
                setValidToDate(UtilsDate.setDate(token.group(GROUP_EVALTIME).substring(0, 2),
                        token.group(GROUP_EVALTIME).substring(2, 4), "00",
                        monthString, yearString));
                LOGGER.debug("{} {}", MSG_LOC_TIME_DECODED_VALID_FROM_DATE, getValidFromDate());
                LOGGER.debug("{} {}\n", MSG_LOC_TIME_DECODED_VALID_TO_DATE, getValidToDate());
            } catch (NumberFormatException | UtilsException e) {
                String errMsg = MSG_LOC_TIME_DECODED_UNABLE_PARSE_DATE_VALUE + " " + e;
                LOGGER.error(errMsg);
            }
        }
    }

    /**
     * Get the natural language in human readable form. This method will return a
     * string that represents this weather condition using natural language (as
     * opposed to METAR)
     *
     * @param groupString the group string identifier
     * @return a string that represents the sky condition in natural language
     */
    protected String getNaturalLanguageString(String groupString) {
        StringBuilder result = new StringBuilder(groupString);

        appendDateInfo(result, groupString);
        appendWindInfo(result);
        appendVisibilityInfo(result);
        appendPressureInfo(result);
        appendSkyConditionsInfo(result);
        appendWeatherConditionsInfo(result);
        appendUnparsedDataInfo(result);

        result.append("\n");
        return result.toString();
    }

    /**
     * Append date information to the result string
     *
     * @param result the StringBuilder to append to
     * @param groupString the group string to check for FM type
     */
    private void appendDateInfo(StringBuilder result, String groupString) {
        if (groupString.equals(MSG_EXTENDED_DECODED_FM)) {
            result.append(" ").append(validFromDate);
        } else {
            result.append(" from ").append(validFromDate).append(" to ").append(validToDate);
        }
    }

    /**
     * Append wind information to the result string
     *
     * @param result the StringBuilder to append to
     */
    private void appendWindInfo(StringBuilder result) {
        if (windGroup == null) {
            return;
        }

        if (windGroup.getWindNotDetermined() != null) {
            result.append("  Wind : The wind direction and speed cannot be determined");
            return;
        }

        appendWindDirection(result);
        appendWindSpeed(result);
        appendWindGusts(result);
    }

    /**
     * Append wind direction information
     *
     * @param result the StringBuilder to append to
     */
    private void appendWindDirection(StringBuilder result) {
        if (windGroup.isWindDirectionIsVariable()) {
            result.append("\n     Wind Direction is variable");
        } else if (windGroup.isWindDirectionIsVarGtrSix()) {
            result.append("\n     Wind Direction is variable (greater than 6 knots)");
            result.append("  Variable between ")
                  .append(windGroup.getwindDirectionVarOneCompass())
                  .append(" and ")
                  .append(windGroup.getwindDirectionVarTwoCompass())
                  .append(" (")
                  .append(windGroup.getWindDirectionVarOne())
                  .append(" degrees and ")
                  .append(windGroup.getWindDirectionVarTwo())
                  .append(" degrees)");
        } else {
            result.append("\n     Wind dir : ")
                  .append(windGroup.getWindDirectionCompass())
                  .append(" (")
                  .append(windGroup.getWindDirection())
                  .append(" degrees)");
        }
    }

    /**
     * Append wind speed information
     *
     * @param result the StringBuilder to append to
     */
    private void appendWindSpeed(StringBuilder result) {
        result.append("\n     Wind speed : ")
              .append(windGroup.getWindSpeedInMPH())
              .append(" mph, ")
              .append(windGroup.getWindSpeedInKnots())
              .append(" knots");
    }

    /**
     * Append wind gust information
     *
     * @param result the StringBuilder to append to
     */
    private void appendWindGusts(StringBuilder result) {
        result.append("\n     Wind gusts : ")
              .append(windGroup.getWindGustsInMPH())
              .append(" mph, ")
              .append(windGroup.getWindGustsInKnots())
              .append(" knots");
    }

    /**
     * Append visibility information to the result string
     *
     * @param result the StringBuilder to append to
     */
    private void appendVisibilityInfo(StringBuilder result) {
        if (visibilityGroup == null) {
            return;
        }

        
        

        if (visibilityGroup.isCavok()) {
            result.append("\n     Visibility : ").append(MSG_WEATHER_DECODED_CAVOK);
        } else if (visibilityGroup.isVisibilityGreaterThan()) {
            result.append("\n     Visibility : Greater than ")
                  .append(visibilityGroup.getVisibility())
                  .append(" ")
                  .append(MSG_MSRMNT_DECODED_MILES)
                  .append(", ")
                  .append(visibilityGroup.getVisibilityKilometers())
                  .append(" ")
                  .append(MSG_MSRMNT_DECODED_KILOMETERS);
        } else if (visibilityGroup.isVisibilityLessThan()) {
            result.append("\n     Visibility : Less than ")
                  .append(visibilityGroup.getVisibility())
                  .append(" ")
                  .append(MSG_MSRMNT_DECODED_MILES)
                  .append(", ")
                  .append(visibilityGroup.getVisibilityKilometers())
                  .append(" ")
                  .append(MSG_MSRMNT_DECODED_KILOMETERS);
        } else {
            result.append("\n     Visibility : ")
                  .append(visibilityGroup.getVisibility())
                  .append(" ")
                  .append(MSG_MSRMNT_DECODED_MILES)
                  .append(", ")
                  .append(visibilityGroup.getVisibilityKilometers())
                  .append(" ")
                  .append(MSG_MSRMNT_DECODED_KILOMETERS);
        }
    }

    /**
     * Append pressure information to the result string
     *
     * @param result the StringBuilder to append to
     */
    private void appendPressureInfo(StringBuilder result) {
        if (pressureGroup == null) {
            return;
        }

        if (pressureGroup.getPressure() == null) {
            result.append("\n     Pressure   : The pressure cannot be determined");
        } else {
            result.append("\n     Pressure   : ")
                  .append(pressureGroup.getPressure())
                  .append(" in Hg, ")
                  .append(pressureGroup.getPressureInHectoPascals())
                  .append(" in hPa");
        }
    }

    /**
     * Append sky conditions information to the result string
     *
     * @param result the StringBuilder to append to
     */
    private void appendSkyConditionsInfo(StringBuilder result) {
        if (skyConditionGroup == null) {
            return;
        }

        result.append("\n\n     Total sky conditions: ").append(skyConditionsGroup.size());

        List<String> skyCondList = new ArrayList<>(skyConditionsGroup.values());
        Collections.sort(skyCondList);

        for (String value : skyCondList) {
            result.append("\n     Value: ").append(value.substring(2));
        }
    }

    /**
     * Append weather conditions information to the result string
     *
     * @param result the StringBuilder to append to
     */
    private void appendWeatherConditionsInfo(StringBuilder result) {
        if (weatherConditionsGroup == null) {
            return;
        }

        result.append("\n\n     Total weather conditions: ").append(weatherConditionsGroup.size());

        List<String> weatherCondList = new ArrayList<>(weatherConditionsGroup.values());
        Collections.sort(weatherCondList);

        for (String value : weatherCondList) {
            result.append("\n     Value: ").append(value);
        }
    }

    /**
     * Append unparsed data information to the result string
     *
     * @param result the StringBuilder to append to
     */
    private void appendUnparsedDataInfo(StringBuilder result) {
        if (parseString != null) {
            result.append("\n\n     Unparsed Data is as follows");
            result.append("\n     ").append(parseString);
        } else {
            result.append("\n\n     There is no unparsed data for this");
        }
    }

    /**
     * Set validFromDate
     *
     * @param validFromDate
     */
    public void setValidFromDate(Date validFromDate) {
        this.validFromDate = validFromDate;
    }

    /**
     * Get validFromDate
     *
     * @return validFromDate
     */
    public Date getValidFromDate() {
        return validFromDate;
    }

    /**
     * Set validToDate
     *
     * @param validToDate
     */
    public void setValidToDate(Date validToDate) {
        this.validToDate = validToDate;
    }

    /**
     * Get validToDate
     *
     * @return validToDate
     */
    public Date getValidToDate() {
        return validToDate;
    }

    /**
     * Get isValidFromToDate
     *
     * @return isValidFromToDate
     */
    public boolean isValidFromToDate() {
        return isValidFromToDate;
    }

    /**
     * Get monthString
     *
     * @return monthString
     */
    public String getMonthString() {
        return monthString;
    }

    /**
     * Set monthString
     *
     * @param monthString
     */
    public void setMonthString(String monthString) {
        this.monthString = monthString;
    }

    /**
     * Get yearString
     *
     * @return yearString
     */
    public String getYearString() {
        return yearString;
    }

    /**
     * Set yearString
     *
     * @param yearString
     */
    public void setYearString(String yearString) {
        this.yearString = yearString;
    }

    /**
     * Get windGroup
     *
     * @return windGroup
     */
    public Wind getWindBecoming() {
        return windGroup;
    }

    /**
     * Get skyCondIndex
     *
     * @return skyCondIndex
     */
    public int getSkyCondIndex() {
        return skyCondIndex;
    }

    /**
     * Get visibilityGroup
     *
     * @return visibilityGroup
     */
    public Visibility getVisibilityBecoming() {
        return visibilityGroup;
    }

    /**
     * Get skyConditionGroup
     *
     * @return skyConditionGroup
     */
    public SkyCondition getSkyConditionBecoming() {
        return skyConditionGroup;
    }

    /**
     * Get weatherConditionGroup
     *
     * @return weatherConditionGroup
     */
    public WeatherCondition getWeatherConditionBecoming() {
        return weatherConditionGroup;
    }

    /**
     * Set unparsed data string
     *
     * @param token
     */
    public void setParseString(Matcher token) {
        LOGGER.debug("Unparsed: #" + token.group(GROUP_UNPARSED) + "#");
        this.parseString.add(token.group(GROUP_UNPARSED));
    }
}
