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
package noakweather.noaa_api.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
public class WeatherDataFileTest {
    
    private Path tempFile;
    
    public WeatherDataFileTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws IOException {
        // Create a temporary file for testing
        tempFile = Files.createTempFile("weather-test", ".dat");
    }
    
    @AfterEach
    public void tearDown() throws IOException {
        // Clean up temporary file
        if (tempFile != null && Files.exists(tempFile)) {
            Files.delete(tempFile);
        }
    }
    
    /**
     * Test of fromPath method with valid METAR data
     */
    @Test
    public void testFromPathWithMetarData() throws Exception {
        System.out.println("testFromPathWithMetarData");
        
        // Create test data
        List<String> testData = Arrays.asList(
            "2025/07/08 18:52 KCLT 081852Z 24010KT 10SM FEW065 SCT140 36/17 A3003",
            "2025/07/08 19:52 KJFK 081952Z 22008KT 10SM CLR 32/15 A3001"
        );
        Files.write(tempFile, testData);
        
        WeatherDataFile result = WeatherDataFile.fromPath(tempFile);
        
        assertNotNull(result);
        assertEquals("KCLT", result.getPrimaryStationCode());
    }
    
    /**
     * Test of fromPath method with valid TAF data
     * @throws java.lang.Exception
     */
    @Test
    public void testFromPathWithTafData() throws Exception {
        System.out.println("testFromPathWithTafData");
        
        List<String> testData = Arrays.asList(
            "2025/07/08 18:22 TAF KCLT 081720Z 0818/0924 23005KT P6SM FEW050",
            "2025/07/08 19:22 TAF AMD KJFK 081920Z 0819/0925 22008KT P6SM CLR"
        );
        Files.write(tempFile, testData);
        
        WeatherDataFile result = WeatherDataFile.fromPath(tempFile);
        
        assertNotNull(result);
        assertEquals("KCLT", result.getPrimaryStationCode());
    }
    
    /**
     * Test of fromPath method with mixed METAR and TAF data
     * @throws java.lang.Exception
     */
    @Test
    public void testFromPathWithMixedData() throws Exception {
        System.out.println("testFromPathWithMixedData");
        
        List<String> testData = Arrays.asList(
            "2025/07/08 18:52 KCLT 081852Z 24010KT 10SM FEW065 SCT140 36/17 A3003",
            "2025/07/08 18:22 TAF KCLT 081720Z 0818/0924 23005KT P6SM FEW050",
            "",  // Empty line should be filtered out
            "2025/07/08 19:52 KJFK 081952Z 22008KT 10SM CLR 32/15 A3001"
        );
        Files.write(tempFile, testData);
        
        WeatherDataFile result = WeatherDataFile.fromPath(tempFile);
        
        assertNotNull(result);
        assertEquals("KCLT", result.getPrimaryStationCode());
    }
    
    /**
     * Test of fromPath method with empty file
     * @throws java.lang.Exception
     */
    @Test
    public void testFromPathEmptyFile() throws Exception {
        System.out.println("testFromPathEmptyFile");
        
        // Write empty content
        Files.write(tempFile, Collections.emptyList());
        
        WeatherDataFile result = WeatherDataFile.fromPath(tempFile);
        
        assertNotNull(result);
        assertEquals("UNKN", result.getPrimaryStationCode());
    }
    
    /**
     * Test of fromPath method with non-existent file
     */
    @Test
    public void testFromPathNonExistentFile() {
        System.out.println("testFromPathNonExistentFile");
        
        Path nonExistentPath = Paths.get("non-existent-file.dat");
        
        assertThrows(IOException.class, () -> {
            WeatherDataFile.fromPath(nonExistentPath);
        });
    }
    
    /**
     * Test of findMetarData method with valid station
     * @throws java.lang.Exception
     */
    @Test
    public void testFindMetarDataFound() throws Exception {
        System.out.println("testFindMetarDataFound");
        
        List<String> testData = Arrays.asList(
            "2025/07/08 18:52 KCLT 081852Z 24010KT 10SM FEW065 SCT140 36/17 A3003",
            "2025/07/08 19:52 KJFK 081952Z 22008KT 10SM CLR 32/15 A3001"
        );
        Files.write(tempFile, testData);
        
        WeatherDataFile instance = WeatherDataFile.fromPath(tempFile);
        Optional<String> result = instance.findMetarData("KCLT");
        
        assertTrue(result.isPresent());
        assertEquals("2025/07/08 18:52 KCLT 081852Z 24010KT 10SM FEW065 SCT140 36/17 A3003", result.get());
    }
    
    /**
     * Test of findMetarData method with non-existent station
     * @throws java.lang.Exception
     */
    @Test
    public void testFindMetarDataNotFound() throws Exception {
        System.out.println("testFindMetarDataNotFound");
        
        List<String> testData = Arrays.asList(
            "2025/07/08 18:52 KCLT 081852Z 24010KT 10SM FEW065 SCT140 36/17 A3003"
        );
        Files.write(tempFile, testData);
        
        WeatherDataFile instance = WeatherDataFile.fromPath(tempFile);
        Optional<String> result = instance.findMetarData("UNKN");
        
        assertFalse(result.isPresent());
    }
    
    /**
     * Test of findTafData method with valid station
     * @throws java.lang.Exception
     */
    @Test
    public void testFindTafDataFound() throws Exception {
        System.out.println("testFindTafDataFound");
        
        List<String> testData = Arrays.asList(
            "2025/07/08 18:22 TAF KCLT 081720Z 0818/0924 23005KT P6SM FEW050",
            "2025/07/08 19:22 TAF AMD KJFK 081920Z 0819/0925 22008KT P6SM CLR"
        );
        Files.write(tempFile, testData);
        
        WeatherDataFile instance = WeatherDataFile.fromPath(tempFile);
        Optional<String> result = instance.findTafData("KCLT");
        
        assertTrue(result.isPresent());
        assertEquals("2025/07/08 18:22 TAF KCLT 081720Z 0818/0924 23005KT P6SM FEW050", result.get());
    }
    
    /**
     * Test of findTafData method with amended TAF
     * @throws java.lang.Exception
     */
    @Test
    public void testFindTafDataAmended() throws Exception {
        System.out.println("testFindTafDataAmended");
        
        List<String> testData = Arrays.asList(
            "2025/07/08 19:22 TAF AMD KJFK 081920Z 0819/0925 22008KT P6SM CLR"
        );
        Files.write(tempFile, testData);
        
        WeatherDataFile instance = WeatherDataFile.fromPath(tempFile);
        Optional<String> result = instance.findTafData("KJFK");
        
        assertTrue(result.isPresent());
        assertEquals("2025/07/08 19:22 TAF AMD KJFK 081920Z 0819/0925 22008KT P6SM CLR", result.get());
    }
    
    /**
     * Test of findTafData method with non-existent station
     * @throws java.lang.Exception
     */
    @Test
    public void testFindTafDataNotFound() throws Exception {
        System.out.println("testFindTafDataNotFound");
        
        List<String> testData = Arrays.asList(
            "2025/07/08 18:22 TAF KCLT 081720Z 0818/0924 23005KT P6SM FEW050"
        );
        Files.write(tempFile, testData);
        
        WeatherDataFile instance = WeatherDataFile.fromPath(tempFile);
        Optional<String> result = instance.findTafData("UNKN");
        
        assertFalse(result.isPresent());
    }
    
    /**
     * Test of getPrimaryStationCode method
     * @throws java.lang.Exception
     */
    @Test
    public void testGetPrimaryStationCode() throws Exception {
        System.out.println("testGetPrimaryStationCode");
        
        List<String> testData = Arrays.asList(
            "2025/07/08 18:52 KJFK 081852Z 24010KT 10SM FEW065 SCT140 36/17 A3003",
            "2025/07/08 19:52 KCLT 081952Z 22008KT 10SM CLR 32/15 A3001"
        );
        Files.write(tempFile, testData);
        
        WeatherDataFile instance = WeatherDataFile.fromPath(tempFile);
        String result = instance.getPrimaryStationCode();
        
        assertEquals("KJFK", result); // Should be first record's station
    }
    
    /**
     * Test invalid weather data line format
     * @throws java.lang.Exception
     */
    @Test
    public void testInvalidWeatherDataLine() throws Exception {
        System.out.println("testInvalidWeatherDataLine");
        
        List<String> testData = Arrays.asList(
            "invalid line format"  // Not enough parts
        );
        Files.write(tempFile, testData);
        
        assertThrows(IllegalArgumentException.class, () -> {
            WeatherDataFile.fromPath(tempFile);
        });
    }
}
