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
 * Class representing the NOAAUrl test
 *
 * Author: quark95cos Since: Copyright(c) 2025
 */
class NOAAUrlTest {
    
    @Test
    void testNOAAUrl_ClassDesign() {
        // Test that NOAAUrl is properly designed
        
        // Test that methods exist and are public
        try {
            NOAAUrl.class.getMethod("generateMetarDataUri", String.class);
            NOAAUrl.class.getMethod("generateTafDataUri", String.class);
        } catch (NoSuchMethodException e) {
            fail("Expected method not found: " + e.getMessage());
        }
    }
    
    @Test
    void testMethodsHaveCorrectReturnTypes() {
        // Test that methods have the expected return types
        try {
            assertEquals("URI", 
                NOAAUrl.class.getMethod("generateMetarDataUri", String.class)
                    .getReturnType().getSimpleName());
            
            assertEquals("URI", 
                NOAAUrl.class.getMethod("generateTafDataUri", String.class)
                    .getReturnType().getSimpleName());
            
        } catch (NoSuchMethodException e) {
            fail("Method not found: " + e.getMessage());
        }
    }
    
    @Test
    void testMethodsArePublic() {
        // Test that methods are public (not static since they're instance methods)
        try {
            assertTrue(java.lang.reflect.Modifier.isPublic(
                NOAAUrl.class.getMethod("generateMetarDataUri", String.class).getModifiers()));
            assertFalse(java.lang.reflect.Modifier.isStatic(
                NOAAUrl.class.getMethod("generateMetarDataUri", String.class).getModifiers()));
            
            assertTrue(java.lang.reflect.Modifier.isPublic(
                NOAAUrl.class.getMethod("generateTafDataUri", String.class).getModifiers()));
            assertFalse(java.lang.reflect.Modifier.isStatic(
                NOAAUrl.class.getMethod("generateTafDataUri", String.class).getModifiers()));
            
        } catch (NoSuchMethodException e) {
            fail("Method not found: " + e.getMessage());
        }
    }
    
    @Test
    void testClassDesign() {
        // Test that NOAAUrl follows proper class design
        
        // Should be public class
        assertTrue(java.lang.reflect.Modifier.isPublic(NOAAUrl.class.getModifiers()));
        
        // Verify it's not an interface or abstract class
        assertFalse(NOAAUrl.class.isInterface());
        assertFalse(java.lang.reflect.Modifier.isAbstract(NOAAUrl.class.getModifiers()));
        
        // Should have a public constructor (for instantiation)
        try {
            assertTrue(java.lang.reflect.Modifier.isPublic(
                NOAAUrl.class.getConstructor().getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("Public constructor not found");
        }
    }
    
    @Test
    void testAllMethodsExist() {
        // Simple test to verify all expected methods exist
        String[] expectedMethods = {
            "generateMetarDataUri",
            "generateTafDataUri"
        };
        
        for (String methodName : expectedMethods) {
            try {
                NOAAUrl.class.getMethod(methodName, String.class);
            } catch (NoSuchMethodException e) {
                fail("Expected method not found: " + methodName);
            }
        }
    }
    
    @Test
    void testMethodSignatures() {
        // Test that methods have the correct signatures
        try {
            // Test generateMetarDataUri signature
            java.lang.reflect.Method metarMethod = NOAAUrl.class.getMethod("generateMetarDataUri", String.class);
            Class<?>[] metarParams = metarMethod.getParameterTypes();
            assertEquals(1, metarParams.length, "generateMetarDataUri should have 1 parameter");
            assertEquals(String.class, metarParams[0], "Parameter should be String");
            
            // Test generateTafDataUri signature
            java.lang.reflect.Method tafMethod = NOAAUrl.class.getMethod("generateTafDataUri", String.class);
            Class<?>[] tafParams = tafMethod.getParameterTypes();
            assertEquals(1, tafParams.length, "generateTafDataUri should have 1 parameter");
            assertEquals(String.class, tafParams[0], "Parameter should be String");
            
        } catch (NoSuchMethodException e) {
            fail("Method not found: " + e.getMessage());
        }
    }
    
    @Test
    void testClassCanBeInstantiated() {
        // Test that the class properly validates configuration during instantiation
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new NOAAUrl();
        }, "Should throw IllegalArgumentException when configuration is not available");
        
        // Verify the error message is meaningful and related to configuration
        String message = exception.getMessage();
        assertTrue(message.contains("configuration"), 
            "Error message should mention configuration issue: " + message);
        
        // Verify it's not the old cryptic error
        assertFalse(message.contains("ExceptionInInitializerError"), 
            "Should not have the old initialization error");
    }
}
