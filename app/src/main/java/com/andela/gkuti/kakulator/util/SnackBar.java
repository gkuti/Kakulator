package com.andela.gkuti.kakulator.util;

import android.app.Activity;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.andela.gkuti.kakulator.R;

public class SnackBar {
    Activity activity;

    public SnackBar(Activity activity) {
        this.activity = activity;
    }

    public void show(String message) {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) activity.findViewById(R.id.calculator_layout);
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("EXIT", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activity.finish();
                    }
                });
        snackbar.show();
    }
}

