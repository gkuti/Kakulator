package com.andela.gkuti.kakulator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private Button add, minus, times, equals, point, clear, divide;
    private TextView input, result;
    private StringBuffer input_Buffer;
    private String valueString = "", lastResult = "";
    private String currency, operation;
    private boolean continuedValue = false, previousCalculations = false;
    private RateFetcher rateFetcher;
    private String[] abbreviations;
    private Double value, exchangedValue, finalResult = 0.0;
    private boolean calculations = false, firstOperation = true, pendingOperation = false;
    private DataStore dataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponent();
        rateFetcher.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsAcitivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_renew) {
            new RateFetcher(this).execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeComponent() {
        initializeSpinner();
        add = (Button) findViewById(R.id.operation_add);
        times = (Button) findViewById(R.id.operation_times);
        minus = (Button) findViewById(R.id.operation_minus);
        clear = (Button) findViewById(R.id.operation_clear);
        point = (Button) findViewById(R.id.operation_point);
        equals = (Button) findViewById(R.id.operation_equals);
        divide = (Button) findViewById(R.id.operation_divide);
        input = (TextView) findViewById(R.id.tv_input);
        result = (TextView) findViewById(R.id.tv_result);
        input_Buffer = new StringBuffer();
        rateFetcher = new RateFetcher(this);
        currency = "USD";
        dataStore = new DataStore(this);
    }

    public void numClick(View view) {
        TextView textView = (TextView) view;
        if (!continuedValue) {
            input_Buffer.append(currency + textView.getText());
            continuedValue = true;
        } else {
            input_Buffer.append(textView.getText());
        }
        valueString += textView.getText();
        lastResult += textView.getText();
        displayText();
    }

    public void operationClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.operation_add:
                setOperation("+");
                displayOperation(operation);
                break;
            case R.id.operation_divide:
                setOperation("/");
                displayOperation(operation);
                break;
            case R.id.operation_minus:
                setOperation("-");
                displayOperation(operation);
                break;
            case R.id.operation_times:
                setOperation("*");
                displayOperation(operation);
                break;
            case R.id.operation_clear:
                break;
        }
        parseValue();
        if (viewId == R.id.operation_equals) {
            result.setText(finalResult.toString());
            lastResult = currency + finalResult.toString();
        }
    }

    private void parseValue() {
        if (!valueString.equals("")) {
            value = Double.parseDouble(valueString);
            exchangedValue = value / Double.valueOf(dataStore.getRateData(currency));
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        currency = abbreviations[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void initializeSpinner() {
        String[] countries = getResources().getStringArray(R.array.Countries);
        abbreviations = getResources().getStringArray(R.array.Abbreviations);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void displayText() {
        input.setText(input_Buffer.toString());
        if (input.getLineCount() == 2) {
            input.setTextSize(20);
        }
    }

    private void displayOperation(String operation) {
        if (!lastResult.equals("")) {
            int indexOfLastChar = input_Buffer.length();
            if (String.valueOf(input_Buffer.charAt(indexOfLastChar - 1)).equals(operation) == false) {
                input_Buffer.append(" " + operation + " ");
                displayText();
            }
        }
    }
}
