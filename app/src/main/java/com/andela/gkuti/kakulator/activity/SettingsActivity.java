package com.andela.gkuti.kakulator.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.andela.gkuti.kakulator.dal.DataStore;
import com.andela.gkuti.kakulator.R;
import com.andela.gkuti.kakulator.util.SpinnerHelper;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {
    private SpinnerHelper spinner;
    private DataStore dataStore;
    private TextView textView;
    private String[] countries;
    private Switch updateSwitch;
    private int baseCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        intializeComponents();
    }

    public void initializeSpinner() {
        final String[] abbreviations = getResources().getStringArray(R.array.Abbreviations);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(SettingsActivity.this,
                android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = new SpinnerHelper(findViewById(R.id.settings_spinner));
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(baseCurrency);
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
        countries = getResources().getStringArray(R.array.Countries);
        updateSwitch = (Switch) findViewById(R.id.switch1);
        updateSwitch.setOnCheckedChangeListener(this);
        dataStore = new DataStore(this);
        setUserSettings();
        initializeSpinner();
    }
    private void setUserSettings(){
        baseCurrency = dataStore.getData("baseCurrency");
        int update = dataStore.getData("update");
        textView.setText(countries[baseCurrency]);
        if (update == 0) {
            this.updateSwitch.setChecked(true);
        } else {
            this.updateSwitch.setChecked(false);
        }
    }
}

