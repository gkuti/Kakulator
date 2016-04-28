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
    private StringBuffer inputBuffer, expressionBuffer;
    private String valueString = "", lastResult = "";
    private String currency, operation;
    private boolean continuedValue = false;
    private RateFetcher rateFetcher;
    private String[] abbreviations;
    private Double value, exchangedValue, finalResult = 0.0;
    private boolean firstOperation = true;
    private DataStore dataStore;
    private int baseCurrency, update;
    private boolean isInputEntered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponent();
        if (update == 0) {
            rateFetcher.execute();
        }
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
        if (id == R.id.action_top_ten) {
            Intent intent = new Intent(this, TopTenActivity.class);
            startActivity(intent);
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
        inputBuffer = new StringBuffer();
        expressionBuffer = new StringBuffer();
        rateFetcher = new RateFetcher(this);
        currency = "USD";
        dataStore = new DataStore(this);
        baseCurrency = dataStore.getData("baseCurrency");
        update = dataStore.getData("update");
    }

    public void numClick(View view) {
        TextView textView = (TextView) view;
        isInputEntered = true;
        if (!continuedValue) {
            inputBuffer.append(currency + textView.getText());
            continuedValue = true;
        } else {
            inputBuffer.append(textView.getText());
        }
        valueString += textView.getText();
        lastResult += textView.getText();
        displayText();
    }

    public void operationClick(View view) {
        parseValue();
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
        if (viewId == R.id.operation_equals) {
            if (isExpression(expressionBuffer.toString())) {
                finalResult = ExpressionEvaluator.evaluate(expressionBuffer.toString());
            }
            result.setText(String.valueOf(dataStore.getRateData(abbreviations[baseCurrency]) * finalResult));
            lastResult = currency + finalResult.toString();
            expressionBuffer = new StringBuffer();
            expressionBuffer.append(finalResult.toString());
        }
    }

    private void parseValue() {
        if (!valueString.equals("")) {
            value = Double.parseDouble(valueString);
            exchangedValue = (value / dataStore.getRateData(currency));
            expressionBuffer.append(exchangedValue.toString());
            if (firstOperation) {
                finalResult = exchangedValue;
                restartOperation();
                firstOperation = false;
            } else {
                performLastOperation();
            }
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

    public void performLastOperation() {
        switch (operation) {
            case "+":
                finalResult += exchangedValue;
                break;
            case "-":
                finalResult -= exchangedValue;
                break;
            case "*":
                finalResult *= exchangedValue;
                break;
            case "/":
                finalResult /= exchangedValue;
                break;
        }
        restartOperation();
    }

    private void restartOperation() {
        valueString = "";
        lastResult = "";
    }

    private void setOperation(String operation) {
        this.operation = operation;
        continuedValue = false;
    }

    public void displayText() {
        input.setText(inputBuffer.toString());
        if (input.getLineCount() == 2) {
            input.setTextSize(20);
        }
    }

    private void displayOperation(String operation) {
        if (isInputEntered) {
            int indexOfLastChar = inputBuffer.length();
            if (String.valueOf(inputBuffer.charAt(indexOfLastChar - 1)).equals(" ")) {
                inputBuffer.replace(indexOfLastChar - 2, indexOfLastChar - 1, operation);
            } else {
                inputBuffer.append(" " + operation + " ");
                expressionBuffer.append(" " + operation + " ");
            }
            displayText();
        }
    }

    private boolean isExpression(String buffer) {
        String items[] = buffer.split(" ");
        if (items.length > 3) {
            return true;
        }
        return false;
    }
}
