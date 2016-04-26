package com.andela.gkuti.kakulator;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {
    private List<Currency> currencyList;

    public CurrencyAdapter(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.currency_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Currency currency = currencyList.get(position);
        holder.Country.setText(currency.getCountry());
        holder.Abbreviation.setText(currency.getAbbreviation());
        holder.Rate.setText(currency.getRate());
    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Country, Abbreviation, Rate;

        public ViewHolder(View itemView) {
            super(itemView);
            Country = (TextView) itemView.findViewById(R.id.country);
            Abbreviation = (TextView) itemView.findViewById(R.id.abbreviation);
            Rate = (TextView) itemView.findViewById(R.id.rate);
        }
    }
}
