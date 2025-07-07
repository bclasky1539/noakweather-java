/*
 * noakweather(TM) is a Java library for parsing weather data
 * Copyright (C) 2022 quark95cos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package noakweather;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author quark95cos
 */
public class NoakWeatherMainTest {

    public NoakWeatherMainTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        System.out.println("Setup class");
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        System.out.println("Setup before each test");
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of main method, of class NoakWeatherMain.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = new String[4];
        args[0] = "m";
        args[1] = "kclt";
        args[2] = "y";
        args[3] = "i";
        NoakWeatherMain.main(args);
    }

    @Test
    public void testMainWithInsufficientArgs() {
        System.out.println("testMainWithInsufficientArgs");
        String[] args = new String[2]; // Less than 4 args
        args[0] = "m";
        args[1] = "kclt";
        
        // This tests the early exit path when args.length < 4
        NoakWeatherMain.main(args);
    }

    @Test
    public void testMainWithTafType() {
        System.out.println("testMainWithTafType");
        String[] args = new String[4];
        args[0] = "t"; // TAF instead of METAR
        args[1] = "kclt";
        args[2] = "y";
        args[3] = "i";
        
        NoakWeatherMain.main(args);
    }

    @Test
    public void testMainWithInvalidWeatherType() {
        System.out.println("testMainWithInvalidWeatherType");
        String[] args = new String[4];
        args[0] = "x"; // Invalid type (not 'm' or 't')
        args[1] = "kclt";
        args[2] = "y";
        args[3] = "i";
        
        NoakWeatherMain.main(args);
    }

    @Test
    public void testMainWithWarningLogLevel() {
        System.out.println("testMainWithWarningLogLevel");
        String[] args = new String[4];
        args[0] = "m";
        args[1] = "kclt";
        args[2] = "y";
        args[3] = "w"; // Warning log level
        
        NoakWeatherMain.main(args);
    }

    @Test
    public void testMainWithDebugLogLevel() {
        System.out.println("testMainWithDebugLogLevel");
        String[] args = new String[4];
        args[0] = "m";
        args[1] = "kclt";
        args[2] = "y";
        args[3] = "d"; // Debug log level
        
        NoakWeatherMain.main(args);
    }

    @Test
    public void testMainWithUnknownLogLevel() {
        System.out.println("testMainWithUnknownLogLevel");
        String[] args = new String[4];
        args[0] = "m";
        args[1] = "kclt";
        args[2] = "y";
        args[3] = "x"; // Unknown log level
        
        NoakWeatherMain.main(args);
    }
}
