package com.andela.gkuti.kakulator.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andela.gkuti.kakulator.model.Currency;
import com.andela.gkuti.kakulator.dal.DataStore;
import com.andela.gkuti.kakulator.R;

import java.util.List;

/**
 * CurrencyAdapter class
 */
public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {
    private String[] abbreviations;
    private List<Currency> currencyList;
    private int baseCurrency;
    private DataStore dataStore;

    /**
     * Constructor for CurrencyAdapter class
     *
     * @param currencyList the list of currency
     * @param context      the activity context
     */
    public CurrencyAdapter(List<Currency> currencyList, Context context) {
        this.currencyList = currencyList;
        dataStore = new DataStore(context);
        abbreviations = context.getResources().getStringArray(R.array.Abbreviations);
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.currency_item, parent, false);
        return new ViewHolder(itemView);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Currency currency = currencyList.get(position);
        holder.Country.setText(currency.getCountry());
        holder.Abbreviation.setText(currency.getAbbreviation());
        holder.Rate.setText(currency.getRate());
        baseCurrency = dataStore.getData("baseCurrency");
        holder.BaseCurrency.setText(abbreviations[baseCurrency]);
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     */
    @Override
    public int getItemCount() {
        return currencyList.size();
    }

    /**
     * ViewHolder class
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Country, Abbreviation, Rate, BaseCurrency;

        /**
         * Constructor for ViewHolder class
         */
        public ViewHolder(View itemView) {
            super(itemView);
            Country = (TextView) itemView.findViewById(R.id.country);
            Abbreviation = (TextView) itemView.findViewById(R.id.abbreviation);
            Rate = (TextView) itemView.findViewById(R.id.rate);
            BaseCurrency = (TextView) itemView.findViewById(R.id.base_currency);
        }
    }
}
