package com.andela.gkuti.kakulator.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.andela.gkuti.kakulator.R;
import com.andela.gkuti.kakulator.dal.DataStore;
import com.andela.gkuti.kakulator.util.Constants;
import com.andela.gkuti.kakulator.util.SnackBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * RateFetcher class
 */
public class RateFetcher extends AsyncTask<URL, String, String> {
    private HttpURLConnection connection;
    private BufferedReader reader;
    private InputStream stream;
    private URL url;
    private ProgressDialog progressDialog;
    private Activity activity;
    private boolean running;
    private Double exchangeRate;
    private DataStore dataStore;
    private String[] rate;
    private StringBuffer buffer = new StringBuffer();
    private String line = "";

    /**
     * Constructor for RateFetcher class
     */
    public RateFetcher(Activity activity) {
        this.activity = activity;
    }

    /**
     * Runs on the UI thread before doInBackground, it pops out the progress dialog
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        rate = activity.getResources().getStringArray(R.array.Abbreviations);
        running = true;
        dataStore = new DataStore(activity);
        progressDialog = ProgressDialog.show(activity, "Updating conversion Rates", "Tap anywhere to quit!");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                running = false;
            }
        });
    }

    /**
     * Override this method to perform a computation on a background thread.
     */
    protected String doInBackground(URL... urls) {
        try {
            url = new URL(Constants.API_URL.getValue() + Constants.APP_TOKEN.getValue());
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            return String.valueOf(parseJson());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "0";
    }

    /**
     * Runs on the UI thread after doInBackground and dismiss the dialog.
     */
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(result.equals("1.0")){
            SnackBar snackBar = new SnackBar(activity);
            snackBar.show("Oops Network error! check your network and try again");
        }
        dataStore.saveData("appState", 1);
        progressDialog.dismiss();
    }

    /**
     * It parses the json object and sends it to the data store
     *
     * @return
     */
    private double parseJson() {
        int value = 0;
        try {
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            if (isJSONValid(finalJson)) {
                JSONObject parentObject = new JSONObject(finalJson);
                JSONObject rates = parentObject.getJSONObject("rates");
                for (String country : rate) {
                    exchangeRate = rates.getDouble(country);
                    dataStore.saveRateData(country, exchangeRate.floatValue());
                }
            } else {
                value = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * method checks whether a text is a json or a json parsable text
     * @param text String to test
     * @return
     */
    public boolean isJSONValid(String text) {
        try {
            new JSONObject(text);
        } catch (JSONException ex) {
            try {
                new JSONArray(text);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
}
