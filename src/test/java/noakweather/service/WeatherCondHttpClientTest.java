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
package noakweather.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Class representing the WeatherCondHttpClient test
 *
 * Author: quark95cos Since: Copyright(c) 2025
 */
class WeatherCondHttpClientTest {
    
    @Test
    void testWeatherCondHttpClient_IsUtilityClass() {
        // Test that WeatherCondHttpClient is properly designed as a utility class
        
        // Test that the method is static
        try {
            // Verify method exists and is static
            WeatherCondHttpClient.class.getMethod("fetchMetarOrTaf", String.class, String.class);
        } catch (NoSuchMethodException e) {
            fail("Expected static method not found: " + e.getMessage());
        }
    }
    
    @Test
    void testMethodHasCorrectReturnType() {
        // Test that method has the expected return type
        try {
            assertEquals("Optional", 
                WeatherCondHttpClient.class.getMethod("fetchMetarOrTaf", String.class, String.class)
                    .getReturnType().getSimpleName());
        } catch (NoSuchMethodException e) {
            fail("Method not found: " + e.getMessage());
        }
    }
    
    @Test
    void testMethodIsPublicAndStatic() {
        // Test that the method is public and static
        try {
            assertTrue(java.lang.reflect.Modifier.isStatic(
                WeatherCondHttpClient.class.getMethod("fetchMetarOrTaf", String.class, String.class).getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isPublic(
                WeatherCondHttpClient.class.getMethod("fetchMetarOrTaf", String.class, String.class).getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("Method not found: " + e.getMessage());
        }
    }
    
    @Test
    void testClassDesign_UtilityClassContract() {
        // Test that WeatherCondHttpClient follows utility class best practices
        
        // Should be public class
        assertTrue(java.lang.reflect.Modifier.isPublic(WeatherCondHttpClient.class.getModifiers()));
        
        // Verify it's not an interface or abstract class
        assertFalse(WeatherCondHttpClient.class.isInterface());
        assertFalse(java.lang.reflect.Modifier.isAbstract(WeatherCondHttpClient.class.getModifiers()));
    }
    
    @Test
    void testMethodExists() {
        // Simple test to verify the expected method exists
        try {
            WeatherCondHttpClient.class.getMethod("fetchMetarOrTaf", String.class, String.class);
        } catch (NoSuchMethodException e) {
            fail("Expected method not found: fetchMetarOrTaf");
        }
    }
    
    @Test
    void testMethodSignature() {
        // Test that the method has the correct signature
        try {
            java.lang.reflect.Method method = WeatherCondHttpClient.class.getMethod("fetchMetarOrTaf", String.class, String.class);
            
            // Check parameter types
            Class<?>[] paramTypes = method.getParameterTypes();
            assertEquals(2, paramTypes.length, "Method should have 2 parameters");
            assertEquals(String.class, paramTypes[0], "First parameter should be String");
            assertEquals(String.class, paramTypes[1], "Second parameter should be String");
            
            // Check return type is Optional
            assertTrue(method.getReturnType().getName().contains("Optional"), 
                "Return type should be Optional");
                
        } catch (NoSuchMethodException e) {
            fail("Method not found: " + e.getMessage());
        }
    }
    
    @Test
    void testUtilityClassCannotBeInstantiated() {
        // Test that the utility class has a private constructor
        try {
            java.lang.reflect.Constructor<?> constructor = WeatherCondHttpClient.class.getDeclaredConstructor();
            assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()),
                "Constructor should be private for utility class");
        } catch (NoSuchMethodException e) {
            fail("Default constructor not found");
        }
    }
    
    @Test
    void testConvenienceMethodsExist() {
        // Test that the new convenience methods exist
        try {
            // Verify fetchMetar method exists and is static
            java.lang.reflect.Method fetchMetar = WeatherCondHttpClient.class.getMethod("fetchMetar", String.class);
            assertTrue(java.lang.reflect.Modifier.isStatic(fetchMetar.getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isPublic(fetchMetar.getModifiers()));
            assertEquals("Optional", fetchMetar.getReturnType().getSimpleName());
            
            // Verify fetchTaf method exists and is static
            java.lang.reflect.Method fetchTaf = WeatherCondHttpClient.class.getMethod("fetchTaf", String.class);
            assertTrue(java.lang.reflect.Modifier.isStatic(fetchTaf.getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isPublic(fetchTaf.getModifiers()));
            assertEquals("Optional", fetchTaf.getReturnType().getSimpleName());
            
        } catch (NoSuchMethodException e) {
            fail("Convenience method not found: " + e.getMessage());
        }
    }
    
    @Test
    void testConvenienceMethodSignatures() {
        // Test that convenience methods have correct signatures
        try {
            // Test fetchMetar signature
            java.lang.reflect.Method fetchMetar = WeatherCondHttpClient.class.getMethod("fetchMetar", String.class);
            Class<?>[] metarParams = fetchMetar.getParameterTypes();
            assertEquals(1, metarParams.length, "fetchMetar should have 1 parameter");
            assertEquals(String.class, metarParams[0], "Parameter should be String");
            
            // Test fetchTaf signature
            java.lang.reflect.Method fetchTaf = WeatherCondHttpClient.class.getMethod("fetchTaf", String.class);
            Class<?>[] tafParams = fetchTaf.getParameterTypes();
            assertEquals(1, tafParams.length, "fetchTaf should have 1 parameter");
            assertEquals(String.class, tafParams[0], "Parameter should be String");
            
        } catch (NoSuchMethodException e) {
            fail("Method not found: " + e.getMessage());
        }
    }
    
    @Test
    void testAllExpectedMethodsExist() {
        // Comprehensive test to verify all expected methods exist
        String[] expectedMethods = {
            "fetchMetarOrTaf",
            "fetchMetar", 
            "fetchTaf"
        };
        
        for (String methodName : expectedMethods) {
            try {
                if ("fetchMetarOrTaf".equals(methodName)) {
                    WeatherCondHttpClient.class.getMethod(methodName, String.class, String.class);
                } else {
                    WeatherCondHttpClient.class.getMethod(methodName, String.class);
                }
            } catch (NoSuchMethodException e) {
                fail("Expected method not found: " + methodName);
            }
        }
    }
    
    @Test
    void testMethodConsistency() {
        // Test that all public static methods follow consistent patterns
        java.lang.reflect.Method[] methods = WeatherCondHttpClient.class.getDeclaredMethods();
        
        for (java.lang.reflect.Method method : methods) {
            // Only check public methods
            if (java.lang.reflect.Modifier.isPublic(method.getModifiers())) {
                // All public methods should be static for utility class
                assertTrue(java.lang.reflect.Modifier.isStatic(method.getModifiers()),
                    "Public method " + method.getName() + " should be static in utility class");
                
                // All public methods should return Optional<String>
                assertTrue(method.getReturnType().getName().contains("Optional"),
                    "Public method " + method.getName() + " should return Optional");
                
                // All public methods should take String parameters
                for (Class<?> paramType : method.getParameterTypes()) {
                    assertEquals(String.class, paramType,
                        "All parameters should be String type in method " + method.getName());
                }
            }
        }
    }
    
    @Test
    void testFetchMetarOrTaf_InvalidInputs() {
        // Test that the method properly validates inputs
        // Note: These tests will only work if your configuration is properly set up
        
        // Test null station
        assertThrows(Exception.class, () -> {
            WeatherCondHttpClient.fetchMetarOrTaf(null, "M");
        }, "Should throw exception for null station");
        
        // Test empty station
        assertThrows(Exception.class, () -> {
            WeatherCondHttpClient.fetchMetarOrTaf("", "M");
        }, "Should throw exception for empty station");
        
        // Test null data type
        assertThrows(Exception.class, () -> {
            WeatherCondHttpClient.fetchMetarOrTaf("KJFK", null);
        }, "Should throw exception for null data type");
        
        // Test empty data type
        assertThrows(Exception.class, () -> {
            WeatherCondHttpClient.fetchMetarOrTaf("KJFK", "");
        }, "Should throw exception for empty data type");
    }
}
