package com.andela.gkuti.kakulator.util;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.andela.gkuti.kakulator.R;

public class SnackBar {
    Activity activity;

    public SnackBar(Activity activity) {
        this.activity = activity;
    }

    public void show(String message) {
        RelativeLayout relativeLayout = (RelativeLayout) activity.findViewById(R.id.main_activity);
        Snackbar snackbar = Snackbar.make(relativeLayout, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("EXIT", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activity.finish();
                    }
                });
        snackbar.show();
    }
}

