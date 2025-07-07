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
public class UtilsExceptionTest {
    
    public UtilsExceptionTest() {
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
     * Test of default constructor
     */
    @Test
    public void testDefaultConstructor() {
        System.out.println("testDefaultConstructor");
        UtilsException instance = new UtilsException();
        
        assertNotNull(instance);
        assertNull(instance.getMessage());
        assertNull(instance.getCause());
        assertNull(instance.getRecordException());
    }
    
    /**
     * Test of constructor with message
     */
    @Test
    public void testConstructorWithMessage() {
        System.out.println("testConstructorWithMessage");
        String message = "Test error message";
        UtilsException instance = new UtilsException(message);
        
        assertNotNull(instance);
        assertEquals(message, instance.getMessage());
        assertNull(instance.getCause());
        assertNull(instance.getRecordException());
    }
    
    /**
     * Test of constructor with cause
     */
    @Test
    public void testConstructorWithCause() {
        System.out.println("testConstructorWithCause");
        RuntimeException cause = new RuntimeException("Original cause");
        UtilsException instance = new UtilsException(cause);
        
        assertNotNull(instance);
        assertEquals(cause, instance.getCause());
        assertNull(instance.getRecordException());
    }
    
    /**
     * Test of constructor with message and cause
     */
    @Test
    public void testConstructorWithMessageAndCause() {
        System.out.println("testConstructorWithMessageAndCause");
        String message = "Test error message";
        RuntimeException cause = new RuntimeException("Original cause");
        UtilsException instance = new UtilsException(message, cause);
        
        assertNotNull(instance);
        assertEquals(message, instance.getMessage());
        assertEquals(cause, instance.getCause());
        assertNull(instance.getRecordException());
    }
    
    /**
     * Test of getRecordException method - default null
     */
    @Test
    public void testGetRecordExceptionDefault() {
        System.out.println("testGetRecordExceptionDefault");
        UtilsException instance = new UtilsException();
        String result = instance.getRecordException();
        assertNull(result);
    }
    
    /**
     * Test of setRecordException and getRecordException methods
     */
    @Test
    public void testSetAndGetRecordException() {
        System.out.println("testSetAndGetRecordException");
        String recordException = "Test record exception";
        UtilsException instance = new UtilsException();
        
        instance.setRecordException(recordException);
        String result = instance.getRecordException();
        
        assertEquals(recordException, result);
    }
    
    /**
     * Test of setRecordException with null value
     */
    @Test
    public void testSetRecordExceptionNull() {
        System.out.println("testSetRecordExceptionNull");
        UtilsException instance = new UtilsException();
        
        // Set to non-null first
        instance.setRecordException("test");
        
        // Then set to null
        instance.setRecordException(null);
        String result = instance.getRecordException();
        
        assertNull(result);
    }
    
    /**
     * Test of setRecordException with empty string
     */
    @Test
    public void testSetRecordExceptionEmpty() {
        System.out.println("testSetRecordExceptionEmpty");
        String recordException = "";
        UtilsException instance = new UtilsException();
        
        instance.setRecordException(recordException);
        String result = instance.getRecordException();
        
        assertEquals("", result);
    }
    
    /**
     * Test exception can be thrown and caught
     */
    @Test
    public void testExceptionCanBeThrown() {
        System.out.println("testExceptionCanBeThrown");
        String message = "Test exception throwing";
        
        assertThrows(UtilsException.class, () -> {
            throw new UtilsException(message);
        });
    }
    
    /**
     * Test exception with record exception can be thrown
     */
    @Test
    public void testExceptionWithRecordCanBeThrown() {
        System.out.println("testExceptionWithRecordCanBeThrown");
        
        try {
            UtilsException exception = new UtilsException("Test message");
            exception.setRecordException("Record info");
            throw exception;
        } catch (UtilsException e) {
            assertEquals("Test message", e.getMessage());
            assertEquals("Record info", e.getRecordException());
        }
    }
}
