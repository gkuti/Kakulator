package com.andela.gkuti.kakulator.util;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class SpinnerHelper implements OnItemSelectedListener {
    private final Spinner spinner;

    private int lastPosition = -1;
    private OnItemSelectedListener proxiedItemSelectedListener = null;

    public SpinnerHelper(Object spinner) {
        this.spinner = (spinner != null) ? (Spinner)spinner : null;
    }

    public Spinner getSpinner() {
        return spinner;
    }

    public void setSelection(int position) {
        lastPosition = Math.max(-1, position);
        spinner.setSelection(position);
    }

    public void setSelection(int position, boolean animate) {
        lastPosition = Math.max(-1, position);
        spinner.setSelection(position, animate);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        proxiedItemSelectedListener = listener;
        spinner.setOnItemSelectedListener(listener == null ? null : this);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position != lastPosition) {
            lastPosition = position;
            if (proxiedItemSelectedListener != null) {
                proxiedItemSelectedListener.onItemSelected(
                        parent, view, position, id
                );
            }
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        if (-1 != lastPosition) {
            lastPosition = -1;
            if (proxiedItemSelectedListener != null) {
                proxiedItemSelectedListener.onNothingSelected(
                        parent
                );
            }
        }
    }

    public void setAdapter(SpinnerAdapter adapter) {
        if (adapter.getCount() > 0) {
            lastPosition = 0;
        }
        spinner.setAdapter(adapter);
    }

    public SpinnerAdapter getAdapter() { return spinner.getAdapter(); }
    public int getCount() { return spinner.getCount(); }
    public Object getItemAtPosition(int position) { return spinner.getItemAtPosition(position); }
    public long getItemIdAtPosition(int position) { return spinner.getItemIdAtPosition(position); }
    public Object getSelectedItem() { return spinner.getSelectedItem(); }
    public long getSelectedItemId() { return spinner.getSelectedItemId(); }
    public int getSelectedItemPosition() { return spinner.getSelectedItemPosition(); }
    public void setEnabled(boolean enabled) { spinner.setEnabled(enabled); }
    public boolean isEnabled() { return spinner.isEnabled(); }
}