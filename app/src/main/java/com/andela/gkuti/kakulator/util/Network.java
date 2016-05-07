package com.andela.gkuti.kakulator.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.andela.gkuti.kakulator.async.RateFetcher;

/**
 * Network class
 */
public class Network {
    private Activity activity;
    private SnackBar snackBar;

    /**
     * Constructor for the Network class
     */
    public Network(Activity activity) {
        this.activity = activity;
        snackBar = new SnackBar(activity);
    }

    /**
     * checks network status and displays an error if data service is off and you are launching app for the first time
     */
    public void checkNetwork(int update, int appState) {
        ConnectivityManager conMan = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State mobile = conMan.getNetworkInfo(0).getState();
        NetworkInfo.State wifi = conMan.getNetworkInfo(1).getState();
        if (mobile == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTED) {
            if (update == 0) {
                new RateFetcher(activity).execute();
            }
        } else {
            if (appState == 0) {
                snackBar.show("Oops! Error fetching conversion rates check your internet connection");
            }
        }
    }
}
