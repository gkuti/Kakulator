package com.andela.gkuti.kakulator;

public class Currency {
    String country;
    String abbreviation;
    String rate;

    public Currency(String country, String abbreviation, String rate) {
        this.country = country;
        this.abbreviation = abbreviation;
        this.rate = rate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
