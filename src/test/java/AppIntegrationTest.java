package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest {

    static App app;

    @BeforeAll
    static void init() {
        app = new App();

        String host = System.getenv()
                .getOrDefault("DB_HOST", "localhost:33060");

        app.connect(host, 120000);
    }

    @Test
    void testGetCity() {
        City city = app.getCity(1);

        assertNotNull(city);
        assertEquals(1, city.getId());
        assertEquals("Kabul", city.getName());
        assertEquals("AFG", city.getCountryCode());
    }

    @Test
    void testCityNotFound() {
        City city = app.getCity(-1);

        assertNull(city);
    }

    @AfterAll
    static void cleanup() {
        if (app != null) {
            app.disconnect();
        }
    }
}
