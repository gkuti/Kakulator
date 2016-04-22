package com.andela.gkuti.kakulator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsAcitivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,CompoundButton.OnCheckedChangeListener {
    private SpinnerHelper spinner;
    private DataStore dataStore;
    private TextView textView;
    private String[] countries;
    private Switch updateSwitch;

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
        spinner = new SpinnerHelper(findViewById(R.id.spinner2));
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        if (checked) {
            dataStore.saveData("update", 0);
        } else {
            dataStore.saveData("update", 1);
        }
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
        updateSwitch = (Switch) findViewById(R.id.switch1);
        updateSwitch.setOnCheckedChangeListener(this);
        int baseCurrency = dataStore.getData("baseCurrency");
        int update = dataStore.getData("update");
        initializeSpinner();
        textView.setText(countries[baseCurrency]);
        saveChanges(baseCurrency);
        if (update == 0) {
            this.updateSwitch.setChecked(true);
        }
        else {
            this.updateSwitch.setChecked(false);
        }
    }
}

