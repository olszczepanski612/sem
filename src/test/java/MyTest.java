package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import  org.junit.jupiter.api.Assertions;

import java.util.ArrayList;

public class MyTest {
    static App app;

    // Initialise the app
    @BeforeAll
    static void init() {
        app = new App();
    }

    // Print out a list of cities
    @Test
    void printCitiesTestEmpty() {
        ArrayList<City> cities = new ArrayList<City>();
        app.printCityReport(cities);
    }

    // Print out a report on Glasgow, Liverpool and Edinburgh
    @Test
    void printCitiesTestNormal() {
        ArrayList<City> cities = new ArrayList<>();
        City city = new City(458, "Glasgow", "GBR", "Scotland", 619680);
        cities.add(city);
        city = new City(459, "Liverpool", "GBR", "England", 461000);
        cities.add(city);
        city = new City(460, "Edinburgh", "GBR", "Scotland", 450180);
        cities.add(city);
        app.printCityReport(cities);
    }

    // Verify Glasgow details
    @Test
    void getCityId() {
        City city = new City(458, "Glasgow", "GBR", "Scotland", 619680);
        Assertions.assertEquals(458, city.getId());
    }
}