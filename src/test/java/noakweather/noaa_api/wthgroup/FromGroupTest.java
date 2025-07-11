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
import noakweather.utils.IndexedLinkedHashMap;
import org.javatuples.Pair;
import noakweather.utils.UtilsException;
import noakweather.utils.Configs;

/**
 * Class representing the from group test
 *
 * Author: quark95cos Since: Copyright(c) 2025
 */
public class FromGroupTest {
    
    private FromGroup fromGroup;
    private IndexedLinkedHashMap<Pattern, Pair<String, Boolean>> groupWeathHandlers;
    
    @BeforeEach
    public void setUp() {
        // Initialize Configs for testing
        Configs.getInstance().setLocale(java.util.Locale.ENGLISH);
        
        fromGroup = new FromGroup();
        groupWeathHandlers = new IndexedLinkedHashMap<>();
        
        // Use the same proven patterns from previous tests
        setupSampleWeatherHandlers();
    }
    
    private void setupSampleWeatherHandlers() {
        // Focus on patterns that work reliably for testing
        groupWeathHandlers.put(
            Pattern.compile("(?<bvaltime>\\d{4})/(?<evaltime>\\d{4})"), // Time period pattern - WORKS
            new Pair<>("valtmper", true)
        );
        
        groupWeathHandlers.put(
            Pattern.compile("(?<unparsed>UNKNOWN\\w+)"), // Unparsed pattern - WORKS
            new Pair<>("unparsed", true)
        );
    }
    
    @Test
    public void testSetFromGroupItems_ValidInput() throws UtilsException {
        // Arrange - Test with FM group (From daytime)
        String daytime = "151200"; // 15th day, 12:00Z
        String token = "1200/1400";
        String monthString = "12";
        String yearString = "2025";
        
        // Act
        String result = fromGroup.setFromGroupItems(daytime, token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null");
        assertFalse(result.trim().isEmpty(), "Result should not be empty");
        assertTrue(result.length() > 10, "Result should contain meaningful content");
        assertNotNull(fromGroup.getValidFromDate(), "Should have valid from date set");
    }
    
    @Test
    public void testSetFromGroupItems_EmptyToken() throws UtilsException {
        // Arrange
        String daytime = "101500"; // 10th day, 15:00Z
        String token = "";
        String monthString = "12";
        String yearString = "2025";
        
        // Act
        String result = fromGroup.setFromGroupItems(daytime, token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null even with empty token");
        assertNotNull(fromGroup.getValidFromDate(), "Should have valid from date set");
    }
    
    @Test
    public void testSetFromGroupItems_NullGroupHandlers() {
        // Arrange
        String daytime = "201800"; // 20th day, 18:00Z
        String token = "1200/1400";
        String monthString = "12";
        String yearString = "2025";
        
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            fromGroup.setFromGroupItems(daytime, token, monthString, yearString, null);
        }, "Should throw NullPointerException when groupWeathHandlers is null");
    }
    
    @Test
    public void testSetFromGroupItems_DifferentDaytimes() throws UtilsException {
        // Test different daytime formats
        String[] daytimes = {"151200", "010600", "302100"};
        String token = "1200/1400";
        String monthString = "06";
        String yearString = "2025";
        
        for (String daytime : daytimes) {
            // Act
            String result = fromGroup.setFromGroupItems(daytime, token, monthString, yearString, groupWeathHandlers);
            
            // Assert
            assertNotNull(result, "Result should not be null for daytime " + daytime);
            assertNotNull(fromGroup.getValidFromDate(), 
                         "Should have valid from date for daytime " + daytime);
        }
    }
    
    @Test
    public void testSetFromGroupItems_ValidDateParameters() throws UtilsException {
        // Arrange
        String daytime = "250900"; // 25th day, 09:00Z
        String token = "1200/1400";
        String monthString = "06";
        String yearString = "2025";
        
        // Act
        String result = fromGroup.setFromGroupItems(daytime, token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals("06", fromGroup.getMonthString(), "Month should be set correctly");
        assertEquals("2025", fromGroup.getYearString(), "Year should be set correctly");
    }
    
    @Test
    public void testSetFromGroupItems_TimePatternProcessing() throws UtilsException {
        // Arrange - Test with time pattern in token
        String daytime = "121800"; // 12th day, 18:00Z
        String token = "1800/2100";
        String monthString = "09";
        String yearString = "2024";
        
        // Act
        String result = fromGroup.setFromGroupItems(daytime, token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null");
        assertNotNull(fromGroup.getValidFromDate(), "Should have valid from date from daytime");
        assertNotNull(fromGroup.getValidToDate(), "Should have valid to date from token pattern");
    }
    
    @Test
    public void testSetFromGroupItems_UnparsedData() throws UtilsException {
        // Arrange
        String daytime = "051200"; // 5th day, 12:00Z
        String token = "UNKNOWNDATA";
        String monthString = "12";
        String yearString = "2025";
        
        // Act
        String result = fromGroup.setFromGroupItems(daytime, token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null");
        assertTrue(result.toLowerCase().contains("unparsed") || result.contains("UNKNOWN"), 
                   "Result should indicate unparsed data");
        assertNotNull(fromGroup.getValidFromDate(), "Should have valid from date");
    }
    
    @Test
    public void testSetFromGroupItems_EdgeCaseMonth() throws UtilsException {
        // Arrange
        String daytime = "282300"; // 28th day, 23:00Z
        String token = "2300/0200";
        String monthString = "02"; // February
        String yearString = "2025";
        
        // Act
        String result = fromGroup.setFromGroupItems(daytime, token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals("02", fromGroup.getMonthString(), "Month should be set correctly");
    }
    
    @Test
    public void testSetFromGroupItems_LeapYear() throws UtilsException {
        // Arrange
        String daytime = "291500"; // 29th day, 15:00Z (leap year)
        String token = "1500/1800";
        String monthString = "02"; // February in leap year
        String yearString = "2024"; // Leap year
        
        // Act
        String result = fromGroup.setFromGroupItems(daytime, token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals("2024", fromGroup.getYearString(), "Year should be set correctly");
    }
    
    @Test
    public void testSetFromGroupItems_InheritedGroupFunctionality() throws UtilsException {
        // Arrange
        String daytime = "151200"; // 15th day, 12:00Z
        String token = "1200/1400";
        String monthString = "07";
        String yearString = "2025";
        
        // Act
        String result = fromGroup.setFromGroupItems(daytime, token, monthString, yearString, groupWeathHandlers);
        
        // Assert - Test that inherited Group functionality works
        assertNotNull(result, "Result should not be null");
        assertEquals("07", fromGroup.getMonthString(), "Inherited month setter should work");
        assertEquals("2025", fromGroup.getYearString(), "Inherited year setter should work");
        assertFalse(fromGroup.isValidFromToDate(), "Inherited boolean field should work");
        assertEquals(0, fromGroup.getSkyCondIndex(), "Inherited sky condition index should work");
    }
    
    @Test
    public void testSetFromGroupItems_MixedPatterns() throws UtilsException {
        // Arrange - Test combination of time and unparsed patterns
        String daytime = "201200"; // 20th day, 12:00Z
        String token = "1200/1400 UNKNOWNOTHER";
        String monthString = "08";
        String yearString = "2025";
        
        // Act
        String result = fromGroup.setFromGroupItems(daytime, token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null");
        assertTrue(result.length() > 10, "Result should contain meaningful content");
        // Should have time information from both daytime and token
        assertNotNull(fromGroup.getValidFromDate(), "Should have valid from date from daytime");
        assertNotNull(fromGroup.getValidToDate(), "Should have valid to date from token");
    }
    
    @Test
    public void testFromGroup_ConstructorInitialization() {
        // Assert - Test that FromGroup initializes properly
        assertNotNull(fromGroup, "FromGroup instance should be created");
        // Test inherited initial values
        assertEquals("", fromGroup.getMonthString(), "Month should be empty initially");
        assertEquals("", fromGroup.getYearString(), "Year should be empty initially");
        assertNull(fromGroup.getValidFromDate(), "Valid from date should be null initially");
        assertNull(fromGroup.getValidToDate(), "Valid to date should be null initially");
    }
}
