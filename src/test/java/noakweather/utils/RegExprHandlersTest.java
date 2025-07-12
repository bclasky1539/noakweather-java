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

import java.util.regex.Pattern;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Class representing the RegExprHandlers test
 *
 * Author: quark95cos Since: Copyright(c) 2025
 */
class RegExprHandlersTest {
    
    @Test
    void testRegExprHandlers_IsUtilityClass() {
        // Test that RegExprHandlers is properly designed as a utility class
        
        // Test that all methods are static
        try {
            // Verify methods exist and are static by checking they don't throw NoSuchMethodException
            RegExprHandlers.class.getMethod("setMainHandlers");
            RegExprHandlers.class.getMethod("setRemarksHandlers");
            RegExprHandlers.class.getMethod("setGroupHandlers");
        } catch (NoSuchMethodException e) {
            fail("Expected static method not found: " + e.getMessage());
        }
    }
    
    @Test
    void testMethodsHaveCorrectReturnTypes() {
        // Test that methods have the expected return types
        try {
            assertEquals("IndexedLinkedHashMap", 
                RegExprHandlers.class.getMethod("setMainHandlers")
                    .getReturnType().getSimpleName());
            
            assertEquals("IndexedLinkedHashMap", 
                RegExprHandlers.class.getMethod("setRemarksHandlers")
                    .getReturnType().getSimpleName());
            
            assertEquals("IndexedLinkedHashMap", 
                RegExprHandlers.class.getMethod("setGroupHandlers")
                    .getReturnType().getSimpleName());
            
        } catch (NoSuchMethodException e) {
            fail("Method not found: " + e.getMessage());
        }
    }
    
    @Test
    void testMethodsArePublicAndStatic() {
        // Test that all handler methods are public and static
        try {
            assertTrue(java.lang.reflect.Modifier.isStatic(
                RegExprHandlers.class.getMethod("setMainHandlers").getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isPublic(
                RegExprHandlers.class.getMethod("setMainHandlers").getModifiers()));
            
            assertTrue(java.lang.reflect.Modifier.isStatic(
                RegExprHandlers.class.getMethod("setRemarksHandlers").getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isPublic(
                RegExprHandlers.class.getMethod("setRemarksHandlers").getModifiers()));
            
            assertTrue(java.lang.reflect.Modifier.isStatic(
                RegExprHandlers.class.getMethod("setGroupHandlers").getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isPublic(
                RegExprHandlers.class.getMethod("setGroupHandlers").getModifiers()));
            
        } catch (NoSuchMethodException e) {
            fail("Method not found: " + e.getMessage());
        }
    }
    
    @Test
    void testClassDesign_UtilityClassContract() {
        // Test that RegExprHandlers follows utility class best practices
        
        // Should be final class
        assertTrue(java.lang.reflect.Modifier.isFinal(RegExprHandlers.class.getModifiers()));
        
        // Should be public class
        assertTrue(java.lang.reflect.Modifier.isPublic(RegExprHandlers.class.getModifiers()));
        
        // Verify it's not an interface or abstract class
        assertFalse(RegExprHandlers.class.isInterface());
        assertFalse(java.lang.reflect.Modifier.isAbstract(RegExprHandlers.class.getModifiers()));
    }
    
    @Test
    void testAllMethodsExist() {
        // Simple test to verify all expected methods exist
        String[] expectedMethods = {
            "setMainHandlers",
            "setRemarksHandlers", 
            "setGroupHandlers"
        };
        
        for (String methodName : expectedMethods) {
            try {
                RegExprHandlers.class.getMethod(methodName);
            } catch (NoSuchMethodException e) {
                fail("Expected method not found: " + methodName);
            }
        }
    }
    
    @Test 
    void testRefactoringPreservesInterface() {
        // Test that your constants refactoring preserved the public interface
        // This ensures your refactoring didn't break existing API contracts
        
        // Count of expected public static methods
        long staticPublicMethods = java.util.Arrays.stream(RegExprHandlers.class.getMethods())
            .filter(method -> java.lang.reflect.Modifier.isStatic(method.getModifiers()))
            .filter(method -> java.lang.reflect.Modifier.isPublic(method.getModifiers()))
            .filter(method -> method.getName().startsWith("set"))
            .count();
        
        // Should have exactly 3 setter methods
        assertEquals(3, staticPublicMethods, 
            "Expected 3 public static setter methods after refactoring");
    }
    
    @Test
    void testConstantsRefactoringSuccess() {
        // Test that verifies the constants-based refactoring approach
        // This test validates that the class can be instantiated for reflection
        // without needing actual method calls that require configuration
        
        // Verify class has the expected structure
        assertTrue(RegExprHandlers.class.getDeclaredFields().length > 0, 
            "Should have constants defined as fields");
        
        // Count private static final fields (our constants)
        long constantFields = java.util.Arrays.stream(RegExprHandlers.class.getDeclaredFields())
            .filter(field -> java.lang.reflect.Modifier.isStatic(field.getModifiers()))
            .filter(field -> java.lang.reflect.Modifier.isFinal(field.getModifiers()))
            .filter(field -> java.lang.reflect.Modifier.isPrivate(field.getModifiers()))
            .filter(field -> field.getType() == String.class)
            .count();
        
        // Should have handler name constants defined (found: " + constantFields + ")");
        assertTrue(constantFields > 20, 
            "Should have handler name constants defined (found: " + constantFields + ")");
    }
    
    @Test
    void testUtilityClassCannotBeInstantiated() {
        // Test that the utility class has a private constructor
        try {
            java.lang.reflect.Constructor<?> constructor = RegExprHandlers.class.getDeclaredConstructor();
            assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()),
                "Constructor should be private for utility class");
        } catch (NoSuchMethodException e) {
            fail("Default constructor not found");
        }
    }
}
