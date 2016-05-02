package com.andela.gkuti.kakulator.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.andela.gkuti.kakulator.dal.DataStore;
import com.andela.gkuti.kakulator.R;
import com.andela.gkuti.kakulator.http.RateFetcher;
import com.andela.gkuti.kakulator.generator.ExpressionEvaluator;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private Button add, minus, times, equals, point, clear, divide;
    private TextView input, result;
    private StringBuffer inputBuffer;
    private StringBuffer expressionBuffer;
    private String valueString = "", lastResult = "";
    private String currency, operator;
    private boolean continuedValue = false;
    private RateFetcher rateFetcher;
    private String[] abbreviations;
    private Double exchangedValue, finalResult = 0.0;
    private boolean firstOperation = true;
    private DataStore dataStore;
    private int update;
    private boolean isInputEntered = false;
    private int appState;
    private CoordinatorLayout coordinatorLayout;
    private DecimalFormat decimalFormat;
    private int mode = 1;
    private ImageButton modeButton;
    private boolean hasCurrency = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponent();
        checkNetwork();
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        allClear();
//    }
//    public void allClear(){
//        inputBuffer = new StringBuffer();
//        input.setText("0");
//        result.setText("");
//        expressionBuffer = new StringBuffer();
//        lastResult = "";
//        valueString = "";
//        finalResult = 0.0;
//        continuedValue = false;
//        hasCurrency = false;
//        isInputEntered = false;
//        firstOperation = true;
//    }
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
        modeButton = (ImageButton) findViewById(R.id.change_mode);
        inputBuffer = new StringBuffer();
        expressionBuffer = new StringBuffer();
        rateFetcher = new RateFetcher(this);
        currency = "USD";
        dataStore = new DataStore(this);
        update = dataStore.getData("update");
        appState = dataStore.getData("appState");
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.calculator_layout);
        decimalFormat = new DecimalFormat("#.00");

    }

    private void initializeSpinner() {
        String[] countries = getResources().getStringArray(R.array.Countries);
        abbreviations = getResources().getStringArray(R.array.Abbreviations);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.getBackground().setColorFilter((Color.WHITE), PorterDuff.Mode.SRC_ATOP);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        currency = abbreviations[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private void checkNetwork() {
        ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        State mobile = conMan.getNetworkInfo(0).getState();
        State wifi = conMan.getNetworkInfo(1).getState();
        if (mobile == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTED) {
            if (update == 0) {
                rateFetcher.execute();
            }
        } else {
            if (appState == 0) {
                displayError();
            }
        }
    }

    private void displayError() {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Oops! Convertion rates need to be Downloaded for the first time", Snackbar.LENGTH_INDEFINITE)
                .setAction("EXIT", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
        snackbar.show();
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
            Intent intent = new Intent(this, SettingsActivity.class);
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

    public void numClick(View view) {
        if (R.id.operation_point != view.getId() || continuedValue) {
            TextView textView = (TextView) view;
            isInputEntered = true;
            if (!continuedValue && mode == 1) {
                inputBuffer.append(currency + textView.getText());
                continuedValue = true;
                hasCurrency = true;
            } else {
                inputBuffer.append(textView.getText());
            }
            valueString += textView.getText();
            lastResult += textView.getText();
            displayText();
        }
    }

    public void displayText() {
        input.setText(inputBuffer.toString());
        if (input.getLineCount() == 2) {
            input.setTextSize(20);
            input.setGravity(View.TEXT_ALIGNMENT_TEXT_START);
        }
    }

    public void operationClick(View view) {
        parseValue();
        int viewId = view.getId();
        switch (viewId) {
            case R.id.operation_add:
                setOperator("+");
                displayOperation(operator);
                break;
            case R.id.operation_divide:
                setOperator("/");
                displayOperation(operator);
                break;
            case R.id.operation_minus:
                setOperator("-");
                displayOperation(operator);
                break;
            case R.id.operation_times:
                setOperator("*");
                displayOperation(operator);
                break;
            case R.id.operation_clear:
                break;
        }
        if (viewId == R.id.operation_equals) {
            performCalculation();
        }
    }
    private void setOperator(String operator) {
        this.operator = operator;
        continuedValue = false;
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

    private void parseValue() {
        if (!valueString.equals("")) {
            double value = Double.parseDouble(valueString);
            if (mode == 1) {
                exchangedValue = (value / dataStore.getRateData(currency));
            } else {
                exchangedValue = value;
            }
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

    private void performCalculation() {
        if (isExpression(expressionBuffer.toString())) {
            finalResult = ExpressionEvaluator.evaluate(expressionBuffer.toString());
        }
        if (hasCurrency) {
            currencyCalculation();
        } else {
            arithmeticCalculation();
        }
        expressionBuffer = new StringBuffer();
        expressionBuffer.append(finalResult.toString());
    }


    private void performLastOperation() {
        switch (operator) {
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

    private boolean isExpression(String buffer) {
        String items[] = buffer.split(" ");
        if (items.length > 3) {
            return true;
        }
        return false;
    }

    public void changeMode(View view) {
        if (mode == 1) {
            times.setEnabled(true);
            divide.setEnabled(true);
            mode = 0;
            modeButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_calculator));
        } else {
            times.setEnabled(false);
            divide.setEnabled(false);
            mode = 1;
            modeButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_sales_performance));
        }
    }

    private void currencyCalculation() {
        int baseCurrency = dataStore.getData("baseCurrency");
        float baseCurrencyValue = dataStore.getRateData(abbreviations[baseCurrency]);
        result.setText(String.valueOf(decimalFormat.format(finalResult * baseCurrencyValue)));
        lastResult = currency + finalResult.toString();
    }

    private void arithmeticCalculation() {
        result.setText(String.valueOf(decimalFormat.format(finalResult)));
        lastResult = finalResult.toString();
    }
    public void allClear(View view){
        inputBuffer = new StringBuffer();
        input.setText("0");
        result.setText("");
        expressionBuffer = new StringBuffer();
        lastResult = "";
        valueString = "";
        finalResult = 0.0;
        continuedValue = false;
        hasCurrency = false;
        isInputEntered = false;
        firstOperation = true;
    }
}
