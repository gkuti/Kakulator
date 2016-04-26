package com.andela.gkuti.kakulator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class TopTenActivity extends AppCompatActivity {
    private String abbreviations[];
    private DataStore dataStore;
    private ArrayList allRates;
    private ArrayList<String> requiredAbbreviation;
    private ArrayList<Currency> currencyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);
        intializeResource();
    }

    private void intializeResource() {
        abbreviations = getResources().getStringArray(R.array.Abbreviations);
        dataStore = new DataStore(this);
        allRates = new ArrayList();
        requiredAbbreviation = new ArrayList<String>();
        currencyList = new ArrayList<Currency>();

    }
}
