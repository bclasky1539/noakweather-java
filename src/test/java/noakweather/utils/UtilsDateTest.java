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
package noakweather.utils;

import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Class representing the date utilities test
 *
 * Author: quark95cos Since: Copyright(c) 2025
 */
public class UtilsDateTest {
    
    public UtilsDateTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    /**
     * Test of setDate method with valid inputs
     */
    @Test
    public void testSetDateValid() throws Exception {
        System.out.println("testSetDateValid");
        String day = "15";
        String hour = "12";
        String minute = "30";
        String month = "06";
        String year = "2025";
        
        Date result = UtilsDate.setDate(day, hour, minute, month, year);
        assertNotNull(result);
    }
    
    /**
     * Test of setDate method with null inputs
     */
    @Test
    public void testSetDateNull() {
        System.out.println("testSetDateNull");
        assertThrows(UtilsException.class, () -> {
            UtilsDate.setDate(null, "12", "30", "06", "2025");
        });
    }
    
    /**
     * Test of setDate method with invalid number format
     */
    @Test
    public void testSetDateInvalidFormat() {
        System.out.println("testSetDateInvalidFormat");
        assertThrows(UtilsException.class, () -> {
            UtilsDate.setDate("XX", "12", "30", "06", "2025");
        });
    }
    
    /**
     * Test of getValidToFromDate method with slash format
     */
    @Test
    public void testGetValidToFromDateSlash() throws Exception {
        System.out.println("testGetValidToFromDateSlash");
        String text = "1918/2018";
        String[] result = UtilsDate.getValidToFromDate(text);
        
        assertEquals("191800Z", result[0]);
        assertEquals("201800Z", result[1]);
    }
    
    /**
     * Test of getValidToFromDate method with Z format
     */
    @Test
    public void testGetValidToFromDateZ() throws Exception {
        System.out.println("testGetValidToFromDateZ");
        String text = "2900Z-2912Z";
        String[] result = UtilsDate.getValidToFromDate(text);
        
        assertEquals("290000Z", result[0]);
        assertEquals("291200Z", result[1]);
    }
    
    /**
     * Test of getValidToFromDate method with 6-digit format
     */
    @Test
    public void testGetValidToFromDate6Digit() throws Exception {
        System.out.println("testGetValidToFromDate6Digit");
        String text = "091818";
        String[] result = UtilsDate.getValidToFromDate(text);
        
        assertEquals("091800Z", result[0]);
        assertEquals("101800Z", result[1]);
    }
    
    /**
     * Test of getValidToFromDate method with null input
     */
    @Test
    public void testGetValidToFromDateNull() {
        System.out.println("testGetValidToFromDateNull");
        assertThrows(UtilsException.class, () -> {
            UtilsDate.getValidToFromDate(null);
        });
    }
    
    /**
     * Test of getValidToFromDate method with invalid format
     */
    @Test
    public void testGetValidToFromDateInvalid() {
        System.out.println("testGetValidToFromDateInvalid");
        assertThrows(UtilsException.class, () -> {
            UtilsDate.getValidToFromDate("invalid");
        });
    }
}
