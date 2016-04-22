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
    private String valueString = "";
    private String currency;
    private boolean continuedValue = false;
    private RateFetcher rateFetcher;
    private String[] abbreviations;

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
        input.setText(input_Buffer.toString());
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

}
