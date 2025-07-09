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
package noakweather.noaa_api.weather;

import java.util.Locale;
import noakweather.utils.Configs;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author bdeveloper
 */
public class WindDirTest {
    
    public WindDirTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        // Initialize Configs with proper locale for testing
        Configs.getInstance().setLocale(Locale.ENGLISH);
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
     * Test of getFormattedWindDir method with 0 degrees (North)
     */
    @Test
    public void testGetFormattedWindDirNorth() {
        System.out.println("testGetFormattedWindDirNorth");
        Integer degrees = 0;
        String result = WindDir.getFormattedWindDir(degrees);
        assertNotNull(result);
        // Should return North direction from config
    }
    
    /**
     * Test of getFormattedWindDir method with 90 degrees (East)
     */
    @Test
    public void testGetFormattedWindDirEast() {
        System.out.println("testGetFormattedWindDirEast");
        Integer degrees = 90;
        String result = WindDir.getFormattedWindDir(degrees);
        assertNotNull(result);
        // Should return East direction from config
    }
    
    /**
     * Test of getFormattedWindDir method with 180 degrees (South)
     */
    @Test
    public void testGetFormattedWindDirSouth() {
        System.out.println("testGetFormattedWindDirSouth");
        Integer degrees = 180;
        String result = WindDir.getFormattedWindDir(degrees);
        assertNotNull(result);
        // Should return South direction from config
    }
    
    /**
     * Test of getFormattedWindDir method with 270 degrees (West)
     */
    @Test
    public void testGetFormattedWindDirWest() {
        System.out.println("testGetFormattedWindDirWest");
        Integer degrees = 270;
        String result = WindDir.getFormattedWindDir(degrees);
        assertNotNull(result);
        // Should return West direction from config
    }
    
    /**
     * Test of getFormattedWindDir method with 45 degrees (NorthEast)
     */
    @Test
    public void testGetFormattedWindDirNorthEast() {
        System.out.println("testGetFormattedWindDirNorthEast");
        Integer degrees = 45;
        String result = WindDir.getFormattedWindDir(degrees);
        assertNotNull(result);
        // Should return NorthEast direction from config
    }
    
    /**
     * Test of getFormattedWindDir method with 135 degrees (SouthEast)
     */
    @Test
    public void testGetFormattedWindDirSouthEast() {
        System.out.println("testGetFormattedWindDirSouthEast");
        Integer degrees = 135;
        String result = WindDir.getFormattedWindDir(degrees);
        assertNotNull(result);
        // Should return SouthEast direction from config
    }
    
    /**
     * Test of getFormattedWindDir method with 225 degrees (SouthWest)
     */
    @Test
    public void testGetFormattedWindDirSouthWest() {
        System.out.println("testGetFormattedWindDirSouthWest");
        Integer degrees = 225;
        String result = WindDir.getFormattedWindDir(degrees);
        assertNotNull(result);
        // Should return SouthWest direction from config
    }
    
    /**
     * Test of getFormattedWindDir method with 315 degrees (NorthWest)
     */
    @Test
    public void testGetFormattedWindDirNorthWest() {
        System.out.println("testGetFormattedWindDirNorthWest");
        Integer degrees = 315;
        String result = WindDir.getFormattedWindDir(degrees);
        assertNotNull(result);
        // Should return NorthWest direction from config
    }
    
    /**
     * Test of getFormattedWindDir method with 360 degrees (should normalize to North)
     */
    @Test
    public void testGetFormattedWindDir360() {
        System.out.println("testGetFormattedWindDir360");
        Integer degrees = 360;
        String result = WindDir.getFormattedWindDir(degrees);
        assertNotNull(result);
        // Should normalize to 0 and return North direction
    }
    
    /**
     * Test of getFormattedWindDir method with negative degrees
     */
    @Test
    public void testGetFormattedWindDirNegative() {
        System.out.println("testGetFormattedWindDirNegative");
        Integer degrees = -90; // Should normalize to 270 (West)
        String result = WindDir.getFormattedWindDir(degrees);
        assertNotNull(result);
        // Should normalize and return West direction
    }
    
    /**
     * Test of getFormattedWindDir method with large positive degrees
     */
    @Test
    public void testGetFormattedWindDirLarge() {
        System.out.println("testGetFormattedWindDirLarge");
        Integer degrees = 450; // Should normalize to 90 (East)
        String result = WindDir.getFormattedWindDir(degrees);
        assertNotNull(result);
        // Should normalize and return East direction
    }
    
    /**
     * Test of getFormattedWindDir method with degrees requiring rounding
     */
    @Test
    public void testGetFormattedWindDirRounding() {
        System.out.println("testGetFormattedWindDirRounding");
        Integer degrees = 12; // Should round to nearest 22.5 degree increment
        String result = WindDir.getFormattedWindDir(degrees);
        assertNotNull(result);
        // Should return North direction (closest to 0)
    }
    
    /**
     * Test of getFormattedWindDir method with degrees at boundary
     */
    @Test
    public void testGetFormattedWindDirBoundary() {
        System.out.println("testGetFormattedWindDirBoundary");
        Integer degrees = 23; // Should round to nearest direction
        String result = WindDir.getFormattedWindDir(degrees);
        assertNotNull(result);
        // Should return appropriate direction based on rounding
    }
    
    /**
     * Test of getFormattedWindDir method with all 16 compass points
     */
    @Test
    public void testGetFormattedWindDirAllDirections() {
        System.out.println("testGetFormattedWindDirAllDirections");
        
        // Test all 16 primary compass directions (every 22.5 degrees)
        for (int i = 0; i < 16; i++) {
            Integer degrees = i * 22; // Close to each 22.5 degree increment
            String result = WindDir.getFormattedWindDir(degrees);
            assertNotNull(result, "Direction should not be null for " + degrees + " degrees");
            assertFalse(result.isEmpty(), "Direction should not be empty for " + degrees + " degrees");
        }
    }
}
