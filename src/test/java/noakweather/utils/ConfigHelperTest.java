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

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Class representing the Config Helper test
 *
 * Author: quark95cos Since: Copyright(c) 2025
 */
public class ConfigHelperTest {
    
    @Test
    public void testGetConfigValue_WithNullKey_ThrowsNullPointerException() {
        // Test null handling - this works without any config setup
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> ConfigHelper.getConfigValue(null)
        );
        
        assertEquals("Config key cannot be null", exception.getMessage());
    }
    
    @Test
    public void testEnumsImplementConfigKeyCorrectly() {
        // Test that all enums properly implement ConfigKey interface
        // and return non-null, non-empty keys
        
        // Test WeatherConfigKeys
        assertNotNull(WeatherConfigKeys.LIGHT.getKey());
        assertFalse(WeatherConfigKeys.LIGHT.getKey().trim().isEmpty());
        assertEquals("WEATHER_LIGHT", WeatherConfigKeys.LIGHT.getKey());
        
        // Test CloudConfigKeys  
        assertNotNull(CloudConfigKeys.CUMULUS.getKey());
        assertFalse(CloudConfigKeys.CUMULUS.getKey().trim().isEmpty());
        assertEquals("CLOUD_CUMULUS", CloudConfigKeys.CUMULUS.getKey());
        
        // Test RvrConfigKeys
        assertNotNull(RvrConfigKeys.REPORTABLE_ABOVE.getKey());
        assertFalse(RvrConfigKeys.REPORTABLE_ABOVE.getKey().trim().isEmpty());
        assertEquals("RVR_REPORTABLE_ABOVE", RvrConfigKeys.REPORTABLE_ABOVE.getKey());
        
        // Test SkyConditionConfigKeys
        assertNotNull(SkyConditionConfigKeys.CLEAR.getKey());
        assertFalse(SkyConditionConfigKeys.CLEAR.getKey().trim().isEmpty());
        assertEquals("SKY_COND_CLEAR", SkyConditionConfigKeys.CLEAR.getKey());
    }
    
    @Test
    public void testAllEnumValuesHaveValidKeys() {
        // Test that all enum values have proper keys
        
        // Test a few values from each enum to ensure consistency
        for (WeatherConfigKeys key : new WeatherConfigKeys[]{
            WeatherConfigKeys.LIGHT, WeatherConfigKeys.HEAVY, WeatherConfigKeys.RAIN}) {
            assertNotNull(key.getKey());
            assertFalse(key.getKey().trim().isEmpty());
            assertTrue(key.getKey().startsWith("WEATHER_"));
        }
        
        for (CloudConfigKeys key : new CloudConfigKeys[]{
            CloudConfigKeys.CUMULUS, CloudConfigKeys.STRATUS, CloudConfigKeys.CIRRUS}) {
            assertNotNull(key.getKey());
            assertFalse(key.getKey().trim().isEmpty());
            assertTrue(key.getKey().startsWith("CLOUD_"));
        }
        
        for (RvrConfigKeys key : RvrConfigKeys.values()) {
            assertNotNull(key.getKey());
            assertFalse(key.getKey().trim().isEmpty());
            assertTrue(key.getKey().startsWith("RVR_"));
        }
    }
    
    @Test
    public void testConfigKeyInterfaceContract() {
        // Test that enums properly implement ConfigKey interface
        ConfigKey weatherKey = WeatherConfigKeys.LIGHT;
        ConfigKey cloudKey = CloudConfigKeys.CUMULUS;
        ConfigKey rvrKey = RvrConfigKeys.REPORTABLE_ABOVE;
        
        // Test interface methods work
        assertNotNull(weatherKey.getKey());
        assertNotNull(cloudKey.getKey());
        assertNotNull(rvrKey.getKey());
        
        // Test that they're actually ConfigKey instances
        assertTrue(weatherKey instanceof ConfigKey);
        assertTrue(cloudKey instanceof ConfigKey);
        assertTrue(rvrKey instanceof ConfigKey);
    }
    
    @Test
    public void testConfigHelper_NullSafetyWithDifferentEnums() {
        // Test that ConfigHelper properly handles the Objects.requireNonNull
        // for different enum types (without actually calling Configs)
        
        // These should all throw NullPointerException due to our null check
        assertThrows(NullPointerException.class, () -> {
            ConfigKey nullKey = null;
            ConfigHelper.getConfigValue(nullKey);
        });
        
        // Test that non-null enums at least pass the null check
        // (They'll fail later due to missing config, but that's expected)
        
        assertThrows(Exception.class, () -> 
            ConfigHelper.getConfigValue(WeatherConfigKeys.LIGHT));
        assertThrows(Exception.class, () -> 
            ConfigHelper.getConfigValue(CloudConfigKeys.CUMULUS));
        assertThrows(Exception.class, () -> 
            ConfigHelper.getConfigValue(RvrConfigKeys.REPORTABLE_ABOVE));
    }
}
