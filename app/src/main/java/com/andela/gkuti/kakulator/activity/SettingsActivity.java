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

/**
 * SettingsActivity class
 */
public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {
    private DataStore dataStore;
    private TextView textView;
    private String[] countries;
    private Switch updateSwitch;
    private int baseCurrency;

    /**
     * method called when the activity is started
     *
     * @param
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        intializeComponents();
    }

    /**
     * called to initialize spinner and set data source for its adapter
     */
    public void initializeSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(SettingsActivity.this,
                android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerHelper spinner = new SpinnerHelper(findViewById(R.id.settings_spinner));
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(baseCurrency);
    }

    /**
     * method triggered when a check action happen on the switch
     *
     * @param compoundButton the switch that was checked
     * @param checked        the boolean value for the switch
     */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        if (checked) {
            dataStore.saveData("update", 0);
        } else {
            dataStore.saveData("update", 1);
        }
    }

    /**
     * called when a new item has been selected from the spinner
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        textView.setText(countries[i]);
        saveChanges(i);
    }

    /**
     * actions to carry out when no item was selected can be done here
     */
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * method to save user base currency to the data store
     *
     * @param currency the currency selected by the user
     */
    private void saveChanges(int currency) {
        dataStore.saveData("baseCurrency", currency);
    }

    /**
     * called to instantiate fields
     */
    private void intializeComponents() {
        textView = (TextView) findViewById(R.id.settings_currency);
        countries = getResources().getStringArray(R.array.Countries);
        updateSwitch = (Switch) findViewById(R.id.switch1);
        updateSwitch.setOnCheckedChangeListener(this);
        dataStore = new DataStore(this);
        setUserSettings();
        initializeSpinner();
    }

    /**
     * method to save user settings
     */
    private void setUserSettings() {
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

