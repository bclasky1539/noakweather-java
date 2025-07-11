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
 * Class representing the becoming test
 *
 * Author: quark95cos Since: Copyright(c) 2025
 */
public class BecomingTest {
    
    private Becoming becoming;
    private IndexedLinkedHashMap<Pattern, Pair<String, Boolean>> groupWeathHandlers;
    
    @BeforeEach
    public void setUp() {
        // Initialize Configs for testing
        Configs.getInstance().setLocale(java.util.Locale.ENGLISH);
        
        becoming = new Becoming();
        groupWeathHandlers = new IndexedLinkedHashMap<>();
        
        // Use the same proven patterns from Tempo tests
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
    public void testSetBecomingItems_ValidInput() throws UtilsException {
        // Arrange - Use simple time pattern that works
        String token = "1200/1400";
        String monthString = "12";
        String yearString = "2025";
        
        // Act
        String result = becoming.setBecomingItems(token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null");
        assertFalse(result.trim().isEmpty(), "Result should not be empty");
        assertTrue(result.length() > 10, "Result should contain meaningful content");
    }
    
    @Test
    public void testSetBecomingItems_EmptyToken() throws UtilsException {
        // Arrange
        String token = "";
        String monthString = "12";
        String yearString = "2025";
        
        // Act
        String result = becoming.setBecomingItems(token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null even with empty token");
    }
    
    @Test
    public void testSetBecomingItems_NullGroupHandlers() {
        // Arrange
        String token = "BECMG 1200/1400";
        String monthString = "12";
        String yearString = "2025";
        
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            becoming.setBecomingItems(token, monthString, yearString, null);
        }, "Should throw NullPointerException when groupWeathHandlers is null");
    }
    
    @Test
    public void testSetBecomingItems_ValidDateParameters() throws UtilsException {
        // Arrange
        String token = "1200/1400";
        String monthString = "06";
        String yearString = "2025";
        
        // Act
        String result = becoming.setBecomingItems(token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals("06", becoming.getMonthString(), "Month should be set correctly");
        assertEquals("2025", becoming.getYearString(), "Year should be set correctly");
    }
    
    @Test
    public void testSetBecomingItems_TimePatternProcessing() throws UtilsException {
        // Arrange
        String token = "1800/2100";
        String monthString = "09";
        String yearString = "2024";
        
        // Act
        String result = becoming.setBecomingItems(token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null");
        assertNotNull(becoming.getValidFromDate(), "Should have valid from date");
        assertNotNull(becoming.getValidToDate(), "Should have valid to date");
    }
    
    @Test
    public void testSetBecomingItems_UnparsedData() throws UtilsException {
        // Arrange
        String token = "BECMG UNKNOWNDATA";
        String monthString = "12";
        String yearString = "2025";
        
        // Act
        String result = becoming.setBecomingItems(token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null");
        assertTrue(result.toLowerCase().contains("unparsed") || result.contains("UNKNOWN"), 
                   "Result should indicate unparsed data");
    }
    
    @Test
    public void testSetBecomingItems_EdgeCaseMonth() throws UtilsException {
        // Arrange
        String token = "2300/0200";
        String monthString = "02"; // February
        String yearString = "2025";
        
        // Act
        String result = becoming.setBecomingItems(token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals("02", becoming.getMonthString(), "Month should be set correctly");
    }
    
    @Test
    public void testSetBecomingItems_LeapYear() throws UtilsException {
        // Arrange
        String token = "1500/1800";
        String monthString = "02"; // February in leap year
        String yearString = "2024"; // Leap year
        
        // Act
        String result = becoming.setBecomingItems(token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals("2024", becoming.getYearString(), "Year should be set correctly");
    }
    
    @Test
    public void testSetBecomingItems_InheritedGroupFunctionality() throws UtilsException {
        // Arrange
        String token = "1200/1400";
        String monthString = "07";
        String yearString = "2025";
        
        // Act
        String result = becoming.setBecomingItems(token, monthString, yearString, groupWeathHandlers);
        
        // Assert - Test that inherited Group functionality works
        assertNotNull(result, "Result should not be null");
        assertEquals("07", becoming.getMonthString(), "Inherited month setter should work");
        assertEquals("2025", becoming.getYearString(), "Inherited year setter should work");
        assertFalse(becoming.isValidFromToDate(), "Inherited boolean field should work");
        assertEquals(0, becoming.getSkyCondIndex(), "Inherited sky condition index should work");
    }
    
    @Test
    public void testSetBecomingItems_MultipleTimePatterns() throws UtilsException {
        // Arrange
        String token = "0600/0900 1200/1500";
        String monthString = "03";
        String yearString = "2025";
        
        // Act
        String result = becoming.setBecomingItems(token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null");
        assertTrue(result.length() > 15, "Result should contain content for multiple patterns");
    }
    
    @Test
    public void testSetBecomingItems_MixedPatterns() throws UtilsException {
        // Arrange - Test combination of time and unparsed patterns
        String token = "1200/1400 UNKNOWNOTHER";
        String monthString = "08";
        String yearString = "2025";
        
        // Act
        String result = becoming.setBecomingItems(token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null");
        assertTrue(result.length() > 10, "Result should contain meaningful content");
        // Should have time information
        assertNotNull(becoming.getValidFromDate(), "Should have valid from date");
        assertNotNull(becoming.getValidToDate(), "Should have valid to date");
    }
    
    @Test
    public void testBecoming_ConstructorInitialization() {
        // Assert - Test that Becoming initializes properly
        assertNotNull(becoming, "Becoming instance should be created");
        // Test inherited initial values
        assertEquals("", becoming.getMonthString(), "Month should be empty initially");
        assertEquals("", becoming.getYearString(), "Year should be empty initially");
        assertNull(becoming.getValidFromDate(), "Valid from date should be null initially");
        assertNull(becoming.getValidToDate(), "Valid to date should be null initially");
    }
}
