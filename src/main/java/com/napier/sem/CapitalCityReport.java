package com.napier.sem;

public class CapitalCityReport {

    //Name.            Country.            Population.

    private String name;
    private String country;
    private String population;

    public CapitalCityReport(String name, String country, String population) {
        this.name = name;
        this.country = country;
        this.population = population;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getPopulation() {
        return population;
    }
}
