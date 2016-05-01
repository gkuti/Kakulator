package com.andela.gkuti.kakulator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class TopTenActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CurrencyAdapter currencyAdapter;
    private ArrayList<Currency> currencyList;
    private TopTenGenerator topTenGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);
        topTenGenerator = new TopTenGenerator(this);
        initializeView();
    }
    private void initializeView() {
        currencyList = topTenGenerator.getTopTen();
        recyclerView = (RecyclerView) findViewById(R.id.top_ten);
        currencyAdapter = new CurrencyAdapter(currencyList, this);
        recyclerView.addItemDecoration(new Decorator(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(currencyAdapter);
    }
}
