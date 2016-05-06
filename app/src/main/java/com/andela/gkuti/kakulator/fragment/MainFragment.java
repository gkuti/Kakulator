package com.andela.gkuti.kakulator.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.andela.gkuti.kakulator.R;
import com.andela.gkuti.kakulator.dal.DataStore;
import com.andela.gkuti.kakulator.generator.ExpressionEvaluator;

import java.text.DecimalFormat;

public class MainFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private Activity activity;
    private Spinner spinner;
    private Button add, minus, times, equals, point, clear, divide, num1, num2, num3, num4, num5, num6, num7, num8, num9, num0;
    private TextView input, result, baseCurrency;
    private StringBuffer inputBuffer;
    private StringBuffer expressionBuffer;
    private String valueString = "", lastResult = "";
    private String currency, operator;
    private boolean continuedValue = false;
    private String[] abbreviations;
    private Double exchangedValue, finalResult = 0.0;
    private boolean firstOperation = true;
    private DataStore dataStore;
    private int update;
    private boolean isInputEntered = false;
    private int appState;
    private DecimalFormat decimalFormat;
    private int mode = 1;
    private ImageButton modeButton;
    private boolean hasCurrency = false;

    /**
     * Called to have the fragment instantiate its user interface view.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    /**
     * Called after the app view as been created
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        initializeComponent(view);
    }

    /**
     * Called when the fragment is returning back to its running state.
     */
    @Override
    public void onResume() {
        super.onResume();
        displayBaseCurrency();
    }

    /**
     * called to instantiate fields
     */
    private void initializeComponent(View view) {
        initializeSpinner(view);
        num0 = (Button) view.findViewById(R.id.num0);
        num1 = (Button) view.findViewById(R.id.num1);
        num2 = (Button) view.findViewById(R.id.num2);
        num3 = (Button) view.findViewById(R.id.num3);
        num4 = (Button) view.findViewById(R.id.num4);
        num5 = (Button) view.findViewById(R.id.num5);
        num6 = (Button) view.findViewById(R.id.num6);
        num7 = (Button) view.findViewById(R.id.num7);
        num8 = (Button) view.findViewById(R.id.num8);
        num9 = (Button) view.findViewById(R.id.num9);
        add = (Button) view.findViewById(R.id.operation_add);
        times = (Button) view.findViewById(R.id.operation_times);
        minus = (Button) view.findViewById(R.id.operation_minus);
        clear = (Button) view.findViewById(R.id.operation_clear);
        point = (Button) view.findViewById(R.id.operation_point);
        equals = (Button) view.findViewById(R.id.operation_equals);
        divide = (Button) view.findViewById(R.id.operation_divide);
        input = (TextView) view.findViewById(R.id.tv_input);
        baseCurrency = (TextView) view.findViewById(R.id.base_currency);
        result = (TextView) view.findViewById(R.id.tv_result);
        modeButton = (ImageButton) view.findViewById(R.id.change_mode);
        inputBuffer = new StringBuffer();
        expressionBuffer = new StringBuffer();
        currency = "USD";
        dataStore = new DataStore(getContext());
        update = dataStore.getData("update");
        appState = dataStore.getData("appState");
        decimalFormat = new DecimalFormat("#.00");
        displayBaseCurrency();
        setListenerToButton();
    }

    /**
     * method sets Listener to buttons and handles click events
     */
    private void setListenerToButton() {
        num0.setOnClickListener(this);
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
        add.setOnClickListener(this);
        times.setOnClickListener(this);
        minus.setOnClickListener(this);
        clear.setOnClickListener(this);
        point.setOnClickListener(this);
        equals.setOnClickListener(this);
        divide.setOnClickListener(this);
        input.setOnClickListener(this);
        result.setOnClickListener(this);
        modeButton.setOnClickListener(this);
    }

    /**
     * called to initialize spinner and set data source for its adapter
     */
    private void initializeSpinner(View view) {
        String[] countries = activity.getResources().getStringArray(R.array.Countries);
        abbreviations = activity.getResources().getStringArray(R.array.Abbreviations);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.getBackground().setColorFilter((Color.WHITE), PorterDuff.Mode.SRC_ATOP);
        spinner.setOnItemSelectedListener(this);
    }

    /**
     * called when a new item has been selected from the spinner
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        currency = abbreviations[i];
    }

    /**
     * actions to carry out when no item was selected can be done here
     */
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    /**
     * method that handles number clicks and displays it on screen
     *
     * @param view the button that triggers the event
     */
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

    /**
     * method that displays input on screen
     */
    public void displayText() {
        input.setText(inputBuffer.toString());
        if (input.getLineCount() == 2) {
            input.setTextSize(20);
            input.setGravity(View.TEXT_ALIGNMENT_TEXT_START);
        }
    }

    /**
     * method that handles operation button clicks
     *
     * @param view the button that triggers the event
     */
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

    /**
     * sets the operator to the specified operator passed
     *
     * @param operator the operator to set
     */
    private void setOperator(String operator) {
        this.operator = operator;
        continuedValue = false;
    }

    /**
     * displays the operator clicked by the user
     *
     * @param operation the operator to be displayed
     */
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

    /**
     * method converts strings values from input to double and exchanged its rate
     */
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

    /**
     * method to perform calculations from user input
     */
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

    /**
     * method to perform the last operation the user does entered before the equal sign
     */
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

    /**
     * method clears input after the equal sign was click
     */
    private void restartOperation() {
        valueString = "";
        lastResult = "";
    }

    /**
     * checks if the user the buffer for the input is an expression
     *
     * @param buffer the buffer input to check
     * @return returns false otherwise true when the it is an expression
     */
    private boolean isExpression(String buffer) {
        String items[] = buffer.split(" ");
        if (items.length > 3) {
            return true;
        }
        return false;
    }

    /**
     * method that handles change mode button clicks
     *
     * @param view the button that triggers the event
     */
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

    /**
     * method used for calculation involving currency
     */
    private void currencyCalculation() {
        int baseCurrency = dataStore.getData("baseCurrency");
        float baseCurrencyValue = dataStore.getRateData(abbreviations[baseCurrency]);
        result.setText(String.valueOf(decimalFormat.format(finalResult * baseCurrencyValue)));
        lastResult = currency + finalResult.toString();
    }

    /**
     * method used for calculation involving arithmetic only
     */
    private void arithmeticCalculation() {
        result.setText(String.valueOf(decimalFormat.format(finalResult)));
        lastResult = finalResult.toString();
    }

    /**
     * method clears all input and re-instantiate fields when all clear button is clicked
     *
     * @param view the button that triggers the event
     */
    public void allClear(View view) {
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

    /**
     * Called when a button has been clicked.
     */
    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.num0 || viewId == R.id.num1 || viewId == R.id.num2 || viewId == R.id.num3 || viewId == R.id.num4 || viewId == R.id.num5
                || viewId == R.id.num6 || viewId == R.id.num7 || viewId == R.id.num8 || viewId == R.id.num9 || viewId == R.id.operation_point) {
            numClick(view);
        }
        if (viewId == R.id.operation_add || viewId == R.id.operation_clear || viewId == R.id.operation_divide || viewId == R.id.operation_minus
                || viewId == R.id.operation_equals || viewId == R.id.operation_times) {
            operationClick(view);
        }
        if (viewId == R.id.change_mode) {
            changeMode(view);
        }
        if (viewId == R.id.operation_clear) {
            allClear(view);
        }

    }

    /**
     * displays base currency to the screen
     */
    public void displayBaseCurrency() {
        int baseCurrency = dataStore.getData("baseCurrency");
        this.baseCurrency.setText(abbreviations[baseCurrency]);
    }
}
