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
 * Class representing the prob test
 *
 * Author: quark95cos Since: Copyright(c) 2025
 */
public class ProbTest {
    
    private Prob prob;
    private IndexedLinkedHashMap<Pattern, Pair<String, Boolean>> groupWeathHandlers;
    
    @BeforeEach
    public void setUp() {
        // Initialize Configs for testing
        Configs.getInstance().setLocale(java.util.Locale.ENGLISH);
        
        prob = new Prob();
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
    public void testSetProbItems_ValidInput() throws UtilsException {
        // Arrange - Test with PROB30 (30% probability)
        String group = "PROB30";
        String token = "1200/1400";
        String monthString = "12";
        String yearString = "2025";
        
        // Act
        String result = prob.setProbItems(group, token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null");
        assertFalse(result.trim().isEmpty(), "Result should not be empty");
        assertTrue(result.length() > 10, "Result should contain meaningful content");
        assertTrue(result.contains("30"), "Result should contain probability percentage");
    }
    
    @Test
    public void testSetProbItems_EmptyToken() throws UtilsException {
        // Arrange
        String group = "PROB40";
        String token = "";
        String monthString = "12";
        String yearString = "2025";
        
        // Act
        String result = prob.setProbItems(group, token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null even with empty token");
        assertTrue(result.contains("40"), "Result should contain probability percentage");
    }
    
    @Test
    public void testSetProbItems_NullGroupHandlers() {
        // Arrange
        String group = "PROB30";
        String token = "1200/1400";
        String monthString = "12";
        String yearString = "2025";
        
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            prob.setProbItems(group, token, monthString, yearString, null);
        }, "Should throw NullPointerException when groupWeathHandlers is null");
    }
    
    @Test
    public void testSetProbItems_DifferentProbabilities() throws UtilsException {
        // Test different probability values (PROB30, PROB40, etc.)
        String[] probGroups = {"PROB30", "PROB40", "PROB50"};
        String token = "1200/1400";
        String monthString = "06";
        String yearString = "2025";
        
        for (String group : probGroups) {
            // Act
            String result = prob.setProbItems(group, token, monthString, yearString, groupWeathHandlers);
            
            // Assert
            assertNotNull(result, "Result should not be null for " + group);
            String expectedProb = group.substring(4); // Extract "30", "40", "50"
            assertTrue(result.contains(expectedProb), 
                      "Result should contain probability " + expectedProb + " for group " + group);
        }
    }
    
    @Test
    public void testSetProbItems_ValidDateParameters() throws UtilsException {
        // Arrange
        String group = "PROB40";
        String token = "1200/1400";
        String monthString = "06";
        String yearString = "2025";
        
        // Act
        String result = prob.setProbItems(group, token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals("06", prob.getMonthString(), "Month should be set correctly");
        assertEquals("2025", prob.getYearString(), "Year should be set correctly");
    }
    
    @Test
    public void testSetProbItems_TimePatternProcessing() throws UtilsException {
        // Arrange
        String group = "PROB30";
        String token = "1800/2100";
        String monthString = "09";
        String yearString = "2024";
        
        // Act
        String result = prob.setProbItems(group, token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null");
        assertNotNull(prob.getValidFromDate(), "Should have valid from date");
        assertNotNull(prob.getValidToDate(), "Should have valid to date");
    }
    
    @Test
    public void testSetProbItems_UnparsedData() throws UtilsException {
        // Arrange
        String group = "PROB50";
        String token = "UNKNOWNDATA";
        String monthString = "12";
        String yearString = "2025";
        
        // Act
        String result = prob.setProbItems(group, token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null");
        assertTrue(result.toLowerCase().contains("unparsed") || result.contains("UNKNOWN"), 
                   "Result should indicate unparsed data");
        assertTrue(result.contains("50"), "Result should contain probability percentage");
    }
    
    @Test
    public void testSetProbItems_EdgeCaseMonth() throws UtilsException {
        // Arrange
        String group = "PROB40";
        String token = "2300/0200";
        String monthString = "02"; // February
        String yearString = "2025";
        
        // Act
        String result = prob.setProbItems(group, token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals("02", prob.getMonthString(), "Month should be set correctly");
    }
    
    @Test
    public void testSetProbItems_LeapYear() throws UtilsException {
        // Arrange
        String group = "PROB30";
        String token = "1500/1800";
        String monthString = "02"; // February in leap year
        String yearString = "2024"; // Leap year
        
        // Act
        String result = prob.setProbItems(group, token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals("2024", prob.getYearString(), "Year should be set correctly");
    }
    
    @Test
    public void testSetProbItems_InheritedGroupFunctionality() throws UtilsException {
        // Arrange
        String group = "PROB40";
        String token = "1200/1400";
        String monthString = "07";
        String yearString = "2025";
        
        // Act
        String result = prob.setProbItems(group, token, monthString, yearString, groupWeathHandlers);
        
        // Assert - Test that inherited Group functionality works
        assertNotNull(result, "Result should not be null");
        assertEquals("07", prob.getMonthString(), "Inherited month setter should work");
        assertEquals("2025", prob.getYearString(), "Inherited year setter should work");
        assertFalse(prob.isValidFromToDate(), "Inherited boolean field should work");
        assertEquals(0, prob.getSkyCondIndex(), "Inherited sky condition index should work");
    }
    
    @Test
    public void testSetProbItems_MixedPatterns() throws UtilsException {
        // Arrange - Test combination of time and unparsed patterns
        String group = "PROB50";
        String token = "1200/1400 UNKNOWNOTHER";
        String monthString = "08";
        String yearString = "2025";
        
        // Act
        String result = prob.setProbItems(group, token, monthString, yearString, groupWeathHandlers);
        
        // Assert
        assertNotNull(result, "Result should not be null");
        assertTrue(result.length() > 10, "Result should contain meaningful content");
        assertTrue(result.contains("50"), "Result should contain probability percentage");
        // Should have time information
        assertNotNull(prob.getValidFromDate(), "Should have valid from date");
        assertNotNull(prob.getValidToDate(), "Should have valid to date");
    }
    
    @Test
    public void testProb_ConstructorInitialization() {
        // Assert - Test that Prob initializes properly
        assertNotNull(prob, "Prob instance should be created");
        // Test inherited initial values
        assertEquals("", prob.getMonthString(), "Month should be empty initially");
        assertEquals("", prob.getYearString(), "Year should be empty initially");
        assertNull(prob.getValidFromDate(), "Valid from date should be null initially");
        assertNull(prob.getValidToDate(), "Valid to date should be null initially");
    }
}
