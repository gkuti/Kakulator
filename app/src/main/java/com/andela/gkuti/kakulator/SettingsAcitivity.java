package com.andela.gkuti.kakulator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsAcitivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private String newBaseCurrency, baseCurrency;
    private DataStore dataStore;
    private TextView textView;
    private String[] countries;
    private Switch update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        intializeComponents();
    }

    public void initializeSpinner() {
        final String[] abbreviations = getResources().getStringArray(R.array.Abbreviations);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(SettingsAcitivity.this,
                android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.spinner2);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        textView.setText(countries[i]);
        saveChanges(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void saveChanges(int currency) {
        dataStore.saveData("baseCurrency", currency);
    }

    private void intializeComponents() {
        textView = (TextView) findViewById(R.id.settings_currency);
        dataStore = new DataStore(this);
        countries = getResources().getStringArray(R.array.Countries);
        initializeSpinner();
    }
}

