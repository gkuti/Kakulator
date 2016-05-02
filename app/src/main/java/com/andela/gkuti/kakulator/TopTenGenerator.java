package com.andela.gkuti.kakulator;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;

public class TopTenGenerator {
    private String abbreviations[];
    private DataStore dataStore;
    private ArrayList allRates;
    private ArrayList<String> requiredAbbreviation;
    private ArrayList<Currency> currencyList;
    private ArrayList<Float> requiredRate;
    private ArrayList<Float> requiredExchangeRate;
    private ArrayList<String> requiredCountry;
    private int baseCurrency;
    private float exchangeRate;
    private Context context;

    public TopTenGenerator(Context context) {
        this.context = context;
    }

    public ArrayList<Currency> getTopTen() {
        intializeValues();
        for (String abbreviation : abbreviations) {
            float rate = dataStore.getRateData(abbreviation);
            allRates.add(rate);
        }
        createRequiredRate();
        return currencyList;
    }

    private void createRequiredRate() {
        Collections.sort(allRates);
        requiredRate = new ArrayList();
        for (int i = 0; i < 10; i++) {
            requiredRate.add((Float) allRates.get(i));
        }
        createRequiredAbbreviation(requiredRate);
    }

    private void createRequiredExchangeRate() {
        for (Float rate : requiredRate) {
            requiredExchangeRate.add(exchangeRate / rate);
        }
        prepareCurrency();
    }

    private void createRequiredAbbreviation(ArrayList<Float> requiredRate) {
        for (float rate : requiredRate) {
            for (String abbreviation : abbreviations) {
                if (dataStore.getRateData(abbreviation) == rate) {
                    requiredAbbreviation.add(abbreviation);
                }
            }
        }
        createRequiredCountry();
    }

    private void createRequiredCountry() {
        requiredCountry = new ArrayList();
        for (String abbreviation : requiredAbbreviation) {
            requiredCountry.add(getCountry(abbreviation));
        }
        createRequiredExchangeRate();
    }

    private void prepareCurrency() {
        for (int i = 0; i < 10; i++) {
            Currency currency = new Currency(requiredCountry.get(i), requiredAbbreviation.get(i), String.valueOf(requiredExchangeRate.get(i)));
            currencyList.add(currency);
        }
    }

    private String getCountry(String abbreviation) {
        String[] countries = context.getResources().getStringArray(R.array.Countries);
        for (int i = 0; i < countries.length; i++) {
            if (abbreviation == abbreviations[i]) {
                return countries[i];
            }
        }
        return null;
    }

    private void intializeValues() {
        abbreviations = context.getResources().getStringArray(R.array.Abbreviations);
        dataStore = new DataStore(context);
        allRates = new ArrayList();
        requiredExchangeRate = new ArrayList<>();
        requiredAbbreviation = new ArrayList<String>();
        currencyList = new ArrayList<Currency>();
        baseCurrency = dataStore.getData("baseCurrency");
        exchangeRate = dataStore.getRateData(abbreviations[baseCurrency]);
    }

}
