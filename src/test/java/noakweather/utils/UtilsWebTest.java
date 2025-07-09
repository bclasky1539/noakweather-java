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

import java.net.URL;
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
public class UtilsWebTest {
    
    public UtilsWebTest() {
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
     * Test of getUrl method with valid URL
     */
    @Test
    public void testGetUrlValid() throws Exception {
        System.out.println("testGetUrlValid");
        String urlString = "https://www.example.com";
        URL result = UtilsWeb.getUrl(urlString);
        
        assertNotNull(result);
        assertEquals("https", result.getProtocol());
        assertEquals("www.example.com", result.getHost());
    }
    
    /**
     * Test of getUrl method with another valid URL
     */
    @Test
    public void testGetUrlValidHttp() throws Exception {
        System.out.println("testGetUrlValidHttp");
        String urlString = "http://localhost:8080/path";
        URL result = UtilsWeb.getUrl(urlString);
        
        assertNotNull(result);
        assertEquals("http", result.getProtocol());
        assertEquals("localhost", result.getHost());
        assertEquals(8080, result.getPort());
        assertEquals("/path", result.getPath());
    }
    
    /**
     * Test of getUrl method with file URL
     */
    @Test
    public void testGetUrlFile() throws Exception {
        System.out.println("testGetUrlFile");
        String urlString = "file:///path/to/file.txt";
        URL result = UtilsWeb.getUrl(urlString);
        
        assertNotNull(result);
        assertEquals("file", result.getProtocol());
    }
    
    /**
     * Test of getUrl method with null input
     */
    @Test
    public void testGetUrlNull() {
        System.out.println("testGetUrlNull");
        assertThrows(UtilsException.class, () -> {
            UtilsWeb.getUrl(null);
        });
    }
    
    /**
     * Test of getUrl method with empty string
     */
    @Test
    public void testGetUrlEmpty() {
        System.out.println("testGetUrlEmpty");
        assertThrows(UtilsException.class, () -> {
            UtilsWeb.getUrl("");
        });
    }
    
    /**
     * Test of getUrl method with malformed URL
     */
    @Test
    public void testGetUrlMalformed() {
        System.out.println("testGetUrlMalformed");
        assertThrows(UtilsException.class, () -> {
            UtilsWeb.getUrl("not-a-valid-url");
        });
    }
    
    /**
     * Test of getUrl method with invalid protocol
     */
    @Test
    public void testGetUrlInvalidProtocol() {
        System.out.println("testGetUrlInvalidProtocol");
        assertThrows(UtilsException.class, () -> {
            UtilsWeb.getUrl("invalid://www.example.com");
        });
    }
    
    /**
     * Test of getUrl method with spaces in URL
     */
    @Test
    public void testGetUrlWithSpaces() {
        System.out.println("testGetUrlWithSpaces");
        assertThrows(UtilsException.class, () -> {
            UtilsWeb.getUrl("http://www.example.com/path with spaces");
        });
    }
}
