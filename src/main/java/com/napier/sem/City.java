package com.napier.sem;

/**
 * A city
 */
public class City {

  // Properties
  private long id;
  private String name;
  private String countryCode;
  private String district;
  private long population;

  // Constructor
  public City(long id, String name, String countryCode, String district, long population) {
    this.id = id;
    this.name = name;
    this.countryCode = countryCode;
    this.district = district;
    this.population = population;
  }

  // Get id of a city
  public long getId() {
    return id;
  }

  // Set id of a city
  public void setId(long id) {
    this.id = id;
  }

  // Get name of a city
  public String getName() {
    return name;
  }

  // Set name of a city
  public void setName(String name) {
    this.name = name;
  }

  // Get country code of a city
  public String getCountryCode() {
    return countryCode;
  }

  // Set country code of a city
  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  // Get district of a city
  public String getDistrict() {
    return district;
  }

  // Set district of a city
  public void setDistrict(String district) {
    this.district = district;
  }

  // Get population of a city
  public long getPopulation() {
    return population;
  }

  // Set population of a city
  public void setPopulation(long population) {
    this.population = population;
  }

  // Return a neat report on a city's details
  @Override
  public String toString() {
    return "City{" + "id=" + id + ", name='" + name + '\'' + ", countryCode='" + countryCode + '\'' + ", district='"
            + district + '\'' + ", population=" + population + '}';
  }
}
