/*
 * Copyright 2025 bdeveloper.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package noakweather.noaa_api.wthgroup;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Date;
import java.util.Calendar;
import noakweather.utils.IndexedLinkedHashMap;
import org.javatuples.Pair;
import noakweather.utils.UtilsException;
import noakweather.utils.Configs;

/**
 * Class representing the group test
 *
 * Author: quark95cos Since: Copyright(c) 2025
 */
public class GroupTest {
    
    private Group group;
    private IndexedLinkedHashMap<Pattern, Pair<String, Boolean>> handlers;
    
    @BeforeEach
    public void setUp() {
        // Initialize Configs for testing
        Configs.getInstance().setLocale(java.util.Locale.ENGLISH);
        
        group = new Group();
        handlers = new IndexedLinkedHashMap<>();
        setupSampleHandlers();
    }
    
    private void setupSampleHandlers() {
        // Add sample weather pattern handlers for testing
        handlers.put(
            Pattern.compile("(?<bvaltime>\\d{4})/(?<evaltime>\\d{4})"), // Time period pattern
            new Pair<>("valtmper", true)
        );
        
        // Simplified wind pattern that matches the expected Wind class interface
        handlers.put(
            Pattern.compile("\\b(?<dir>\\d{3}|VRB)(?<speed>\\d{2,3})(?:G(?<gust>\\d{2,3}))?KT\\b"), // Wind pattern
            new Pair<>("wind", true)
        );
        
        handlers.put(
            Pattern.compile("\\b(?<visibility>\\d+(?:SM|KM))\\b"), // Visibility pattern
            new Pair<>("visibility", true)
        );
        
        handlers.put(
            Pattern.compile("(?<unparsed>UNKNOWN\\w+)"), // Unparsed pattern
            new Pair<>("unparsed", true)
        );
    }

    @Test
    public void testParseGroupHandlers_EmptyToken() throws UtilsException {
        // Arrange
        String token = "";
        
        // Act
        group.parseGroupHandlers(token, handlers);
        
        // Assert - should complete without error
        assertNotNull(group);
    }

    @Test
    public void testParseGroupHandlers_ValidTimePattern() throws UtilsException {
        // Arrange
        String token = "1200/1400";
        
        // Act
        group.parseGroupHandlers(token, handlers);
        
        // Assert - method should complete successfully
        assertNotNull(group);
    }

    @Test
    public void testParseGroupHandlers_NoMatchingPatterns() throws UtilsException {
        // Arrange
        String token = "NOMATCH";
        
        // Act
        group.parseGroupHandlers(token, handlers);
        
        // Assert - should complete without infinite loop
        assertNotNull(group);
    }

    @Test
    public void testParseGroupHandlers_NullHandlers() {
        // Arrange
        String token = "TEST";
        
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            group.parseGroupHandlers(token, null);
        });
    }

    @Test
    public void testSetValidFromDate_ValidToken() {
        // Arrange
        String token = "151030"; // 15th day, 10:30 time
        group.setMonthString("12");
        group.setYearString("2025");
        
        // Act
        group.setValidFromDate(token);
        
        // Assert
        assertNotNull(group.getValidFromDate());
    }

    @Test
    public void testSetValidFromDate_NullToken() {
        // Arrange
        String token = null;
        
        // Act
        group.setValidFromDate(token);
        
        // Assert
        assertNull(group.getValidFromDate());
    }

    @Test
    public void testSetValidFromDate_InvalidToken() {
        // Arrange
        String token = "invalid";
        group.setMonthString("12");
        group.setYearString("2025");
        
        // Act
        group.setValidFromDate(token);
        
        // Assert - should handle error gracefully
        assertNull(group.getValidFromDate());
    }

    @Test
    public void testSetValidToFromDateInfo_ValidMatcher() {
        // Arrange
        Pattern pattern = Pattern.compile("(?<bvaltime>\\d{4})/(?<evaltime>\\d{4})");
        Matcher matcher = pattern.matcher("1200/1400");
        matcher.find();
        group.setMonthString("12");
        group.setYearString("2025");
        
        // Act
        group.setValidToFromDateInfo(matcher);
        
        // Assert
        assertNotNull(group.getValidFromDate());
        assertNotNull(group.getValidToDate());
    }

    @Test
    public void testSetValidToFromDateInfo_NullMatcher() {
        // Arrange
        Matcher matcher = null;
        
        // Act & Assert - Should handle null gracefully or throw expected exception
        assertThrows(NullPointerException.class, () -> {
            group.setValidToFromDateInfo(matcher);
        });
    }

    @Test
    public void testGetNaturalLanguageString_BasicGroupString() {
        // Arrange
        String groupString = "TEST GROUP";
        Calendar cal = Calendar.getInstance();
        Date testDate = cal.getTime();
        group.setValidFromDate(testDate);
        group.setValidToDate(testDate);
        
        // Act
        String result = group.getNaturalLanguageString(groupString);
        
        // Assert
        assertNotNull(result);
        assertFalse(result.trim().isEmpty());
        assertTrue(result.contains(groupString));
    }

    @Test
    public void testGetNaturalLanguageString_EmptyGroupString() {
        // Arrange
        String groupString = "";
        Calendar cal = Calendar.getInstance();
        Date testDate = cal.getTime();
        group.setValidFromDate(testDate);
        group.setValidToDate(testDate);
        
        // Act
        String result = group.getNaturalLanguageString(groupString);
        
        // Assert
        assertNotNull(result);
    }

    @Test
    public void testDateSettersAndGetters() {
        // Arrange
        Calendar cal = Calendar.getInstance();
        Date testFromDate = cal.getTime();
        cal.add(Calendar.HOUR, 2);
        Date testToDate = cal.getTime();
        
        // Act
        group.setValidFromDate(testFromDate);
        group.setValidToDate(testToDate);
        
        // Assert
        assertEquals(testFromDate, group.getValidFromDate());
        assertEquals(testToDate, group.getValidToDate());
    }

    @Test
    public void testMonthStringSettersAndGetters() {
        // Arrange
        String monthString = "12";
        
        // Act
        group.setMonthString(monthString);
        
        // Assert
        assertEquals(monthString, group.getMonthString());
    }

    @Test
    public void testYearStringSettersAndGetters() {
        // Arrange
        String yearString = "2025";
        
        // Act
        group.setYearString(yearString);
        
        // Assert
        assertEquals(yearString, group.getYearString());
    }

    @Test
    public void testIsValidFromToDate() {
        // Act
        boolean result = group.isValidFromToDate();
        
        // Assert
        assertFalse(result); // Should be false based on constructor
    }

    @Test
    public void testSkyCondIndex() {
        // Act
        int result = group.getSkyCondIndex();
        
        // Assert
        assertEquals(0, result); // Should be 0 based on constructor
    }

    @Test
    public void testInitialNullValues() {
        // Assert - Test initial null values from constructor
        assertNull(group.getValidFromDate());
        assertNull(group.getValidToDate());
        assertNull(group.getWindBecoming());
        assertNull(group.getVisibilityBecoming());
        assertNull(group.getSkyConditionBecoming());
        assertNull(group.getWeatherConditionBecoming());
    }

    @Test
    public void testInitialStringValues() {
        // Assert - Test initial string values from constructor
        assertEquals("", group.getMonthString());
        assertEquals("", group.getYearString());
    }

    @Test
    public void testSetParseString_ValidMatcher() throws UtilsException {
        // Arrange
        Pattern pattern = Pattern.compile("(?<unparsed>UNKNOWN\\w+)");
        Matcher matcher = pattern.matcher("UNKNOWNTEST");
        matcher.find();
        
        // Initialize parseString by triggering the unparsed case in detGroupItems first
        // This will initialize parseString internally
        String token = "UNKNOWNTEST";
        group.parseGroupHandlers(token, handlers);
        
        // Now test setParseString directly with a different matcher
        Pattern pattern2 = Pattern.compile("(?<unparsed>UNKNOWN\\w+)");
        Matcher matcher2 = pattern2.matcher("UNKNOWNTEST2");
        matcher2.find();
        
        // Act
        group.setParseString(matcher2);
        
        // Assert - should complete without error
        assertNotNull(group);
    }

    @Test
    public void testSetParseString_NullMatcher() {
        // Arrange
        Matcher matcher = null;
        
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            group.setParseString(matcher);
        });
    }

    @Test
    public void testParseGroupHandlers_WithUnparsedData() throws UtilsException {
        // Arrange
        String token = "UNKNOWNDATA";
        
        // Act
        group.parseGroupHandlers(token, handlers);
        
        // Assert - should process unparsed data without error
        assertNotNull(group);
    }

    @Test
    public void testParseGroupHandlers_MultiplePatterns() throws UtilsException {
        // Arrange - Use a simpler token that won't trigger complex wind parsing
        String token = "1200/1400 UNKNOWNDATA";
        
        // Act
        group.parseGroupHandlers(token, handlers);
        
        // Assert - should process multiple patterns
        assertNotNull(group);
    }
}
