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
public class IndexedLinkedHashMapTest {
    
    public IndexedLinkedHashMapTest() {
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
     * Test of constructor with initial capacity
     */
    @Test
    public void testConstructorWithCapacity() {
        System.out.println("testConstructorWithCapacity");
        IndexedLinkedHashMap<String, String> instance = new IndexedLinkedHashMap<>(10);
        assertNotNull(instance);
        assertTrue(instance.isEmpty());
    }
    
    /**
     * Test of default constructor
     */
    @Test
    public void testDefaultConstructor() {
        System.out.println("testDefaultConstructor");
        IndexedLinkedHashMap<String, String> instance = new IndexedLinkedHashMap<>();
        assertNotNull(instance);
        assertTrue(instance.isEmpty());
    }
    
    /**
     * Test of equals method - same object
     */
    @Test
    public void testEqualsSameObject() {
        System.out.println("testEqualsSameObject");
        IndexedLinkedHashMap<String, String> instance = new IndexedLinkedHashMap<>();
        boolean result = instance.equals(instance);
        assertTrue(result);
    }
    
    /**
     * Test of equals method - null object
     */
    @Test
    public void testEqualsNull() {
        System.out.println("testEqualsNull");
        IndexedLinkedHashMap<String, String> instance = new IndexedLinkedHashMap<>();
        boolean result = instance.equals(null);
        assertFalse(result);
    }
    
    /**
     * Test of equals method - different type
     */
    @Test
    public void testEqualsDifferentType() {
        System.out.println("testEqualsDifferentType");
        IndexedLinkedHashMap<String, String> instance = new IndexedLinkedHashMap<>();
        boolean result = instance.equals("not a map");
        assertFalse(result);
    }
    
    /**
     * Test of equals method - equal maps
     */
    @Test
    public void testEqualsEqualMaps() {
        System.out.println("testEqualsEqualMaps");
        IndexedLinkedHashMap<String, String> instance1 = new IndexedLinkedHashMap<>();
        IndexedLinkedHashMap<String, String> instance2 = new IndexedLinkedHashMap<>();
        
        instance1.put("key1", "value1");
        instance2.put("key1", "value1");
        
        boolean result = instance1.equals(instance2);
        assertTrue(result);
    }
    
    /**
     * Test of hashCode method
     */
    @Test
    public void testHashCode() {
        System.out.println("testHashCode");
        IndexedLinkedHashMap<String, String> instance = new IndexedLinkedHashMap<>();
        instance.put("key1", "value1");
        
        int result = instance.hashCode();
        assertNotEquals(0, result); // Should not be zero for non-empty map
    }
    
    /**
     * Test of put method - new key
     */
    @Test
    public void testPutNewKey() {
        System.out.println("testPutNewKey");
        IndexedLinkedHashMap<String, String> instance = new IndexedLinkedHashMap<>();
        
        String result = instance.put("key1", "value1");
        
        assertNull(result); // Should return null for new key
        assertEquals("value1", instance.get("key1"));
        assertEquals(1, instance.size());
    }
    
    /**
     * Test of put method - existing key
     */
    @Test
    public void testPutExistingKey() {
        System.out.println("testPutExistingKey");
        IndexedLinkedHashMap<String, String> instance = new IndexedLinkedHashMap<>();
        
        instance.put("key1", "value1");
        String result = instance.put("key1", "value2");
        
        assertEquals("value1", result); // Should return old value
        assertEquals("value2", instance.get("key1"));
        assertEquals(1, instance.size()); // Size should not change
    }
    
    /**
     * Test of getValueAtIndex method
     */
    @Test
    public void testGetValueAtIndex() {
        System.out.println("testGetValueAtIndex");
        IndexedLinkedHashMap<String, String> instance = new IndexedLinkedHashMap<>();
        
        instance.put("key1", "value1");
        instance.put("key2", "value2");
        
        String result = instance.getValueAtIndex(0);
        assertEquals("value1", result);
        
        String result2 = instance.getValueAtIndex(1);
        assertEquals("value2", result2);
    }
    
    /**
     * Test of getKeyAtIndex method
     */
    @Test
    public void testGetKeyAtIndex() {
        System.out.println("testGetKeyAtIndex");
        IndexedLinkedHashMap<String, String> instance = new IndexedLinkedHashMap<>();
        
        instance.put("key1", "value1");
        instance.put("key2", "value2");
        
        String result = instance.getKeyAtIndex(0);
        assertEquals("key1", result);
        
        String result2 = instance.getKeyAtIndex(1);
        assertEquals("key2", result2);
    }
    
    /**
     * Test of getIndexOf method - existing key
     */
    @Test
    public void testGetIndexOfExisting() {
        System.out.println("testGetIndexOfExisting");
        IndexedLinkedHashMap<String, String> instance = new IndexedLinkedHashMap<>();
        
        instance.put("key1", "value1");
        instance.put("key2", "value2");
        
        int result = instance.getIndexOf("key2");
        assertEquals(1, result);
    }
    
    /**
     * Test of getIndexOf method - non-existing key
     */
    @Test
    public void testGetIndexOfNonExisting() {
        System.out.println("testGetIndexOfNonExisting");
        IndexedLinkedHashMap<String, String> instance = new IndexedLinkedHashMap<>();
        
        instance.put("key1", "value1");
        
        int result = instance.getIndexOf("nonexistent");
        assertEquals(-1, result);
    }
    
    /**
     * Test of getIndexOf method - null key
     */
    @Test
    public void testGetIndexOfNull() {
        System.out.println("testGetIndexOfNull");
        IndexedLinkedHashMap<String, String> instance = new IndexedLinkedHashMap<>();
        
        instance.put("key1", "value1");
        
        int result = instance.getIndexOf(null);
        assertEquals(-1, result);
    }
}
