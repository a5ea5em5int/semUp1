package com.napier.sem;

public class Country {
    private String name;
    private String continent;
    private String region;
    private String capital;
    private float population;

    public Country(String name, String continent, String region, String capital, float population) {
        this.name = name;
        this.continent = continent;
        this.region = region;
        this.capital = capital;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public String getContinent() {
        return continent;
    }

    public String getRegion() {
        return region;
    }

    public String getCapital() {
        return capital;
    }

    public float getPopulation() {
        return population;
    }
}
