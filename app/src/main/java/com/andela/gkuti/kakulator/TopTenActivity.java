package com.andela.gkuti.kakulator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class TopTenActivity extends AppCompatActivity {
    private String abbreviations[];
    private DataStore dataStore;
    private ArrayList allRates;
    private ArrayList<String> requiredAbbreviation;
    private ArrayList<Currency> currencyList;
    private ArrayList<Float> requiredRate;
    private ArrayList<String> requiredCountry;
    private RecyclerView recyclerView;
    private CurrencyAdapter currencyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);
        intializeResource();
        createRequiredRate();
        initializeView();
    }

    private void intializeResource() {
        abbreviations = getResources().getStringArray(R.array.Abbreviations);
        dataStore = new DataStore(this);
        allRates = new ArrayList();
        requiredAbbreviation = new ArrayList<String>();
        currencyList = new ArrayList<Currency>();
        for (String abbreviation : abbreviations) {
            float rate = dataStore.getRateData(abbreviation);
            allRates.add(rate);
        }

    }

    private void createRequiredRate() {
        Collections.sort(allRates);
        requiredRate = new ArrayList();
        for (int i = 0; i < 10; i++) {
            requiredRate.add((Float) allRates.get(i));
        }
        createRequiredAbbreviation(requiredRate);
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
        prepareCurrency();
    }

    private void prepareCurrency() {
        for (int i = 0; i < 10; i++) {
            Currency currency = new Currency(requiredCountry.get(i), requiredAbbreviation.get(i), String.valueOf(requiredRate.get(i)));
            currencyList.add(currency);
        }
    }

    private String getCountry(String abbreviation) {
        String[] countries = getResources().getStringArray(R.array.Countries);
        for (int i = 0; i < countries.length; i++) {
            if (abbreviation == abbreviations[i]) {
                return countries[i];
            }
        }
        return null;
    }

    private void initializeView() {
        recyclerView = (RecyclerView) findViewById(R.id.top_ten);
        currencyAdapter = new CurrencyAdapter(currencyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(currencyAdapter);
    }

}
