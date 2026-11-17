
package com.napier.sem;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MyTest {

    @Test
    void testCityConstructorAndGetters() {
        City city = new City(
                458, "Glasgow", "GBR",
                "Scotland", 619680
        );

        assertEquals(458, city.getId());
        assertEquals("Glasgow", city.getName());
        assertEquals("GBR", city.getCountryCode());
        assertEquals("Scotland", city.getDistrict());
        assertEquals(619680, city.getPopulation());
    }

    @Test
    void testCitySetters() {
        City city = new City(
                1, "Test", "TST", "Test", 100
        );

        city.setId(2);
        city.setName("Glasgow");
        city.setCountryCode("GBR");
        city.setDistrict("Scotland");
        city.setPopulation(619680);

        assertEquals(2, city.getId());
        assertEquals("Glasgow", city.getName());
        assertEquals("GBR", city.getCountryCode());
        assertEquals("Scotland", city.getDistrict());
        assertEquals(619680, city.getPopulation());
    }

    @Test
    void testCityToString() {
        City city = new City(
                458, "Glasgow", "GBR",
                "Scotland", 619680
        );

        String result = city.toString();

        assertTrue(result.contains("Glasgow"));
        assertTrue(result.contains("Scotland"));
        assertTrue(result.contains("619680"));
    }

    @Test
    void testPrintEmptyCityList() {
        App app = new App();

        ArrayList<City> cities = new ArrayList<>();

        assertDoesNotThrow(
                () -> app.printCityReport(cities)
        );
    }

    @Test
    void testPrintCityList() {
        App app = new App();

        ArrayList<City> cities = new ArrayList<>();

        cities.add(new City(
                458, "Glasgow", "GBR",
                "Scotland", 619680
        ));

        cities.add(new City(
                459, "Liverpool", "GBR",
                "England", 461000
        ));

        assertDoesNotThrow(
                () -> app.printCityReport(cities)
        );
    }

    @Test
    void testPrintSingleCity() {
        App app = new App();

        City city = new City(
                458, "Glasgow", "GBR",
                "Scotland", 619680
        );

        assertDoesNotThrow(
                () -> app.printCityReport(city)
        );
    }

    @Test
    void testGetCityWithoutConnection() {
        App app = new App();

        assertThrows(
                IllegalStateException.class,
                () -> app.getCity(1)
        );
    }

    @Test
    void testDisconnectWithoutConnection() {
        App app = new App();

        assertDoesNotThrow(app::disconnect);
    }
}
