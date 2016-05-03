package com.andela.gkuti.kakulator.http;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.andela.gkuti.kakulator.R;
import com.andela.gkuti.kakulator.dal.DataStore;
import com.andela.gkuti.kakulator.util.Constants;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
            url = new URL(Constants.API_URL.getValue()+Constants.APP_TOKEN.getValue());
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            return parseJson();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Runs on the UI thread after doInBackground and dismiss the dialog.
     */
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        dataStore.saveData("appState",1);
        progressDialog.dismiss();
    }

    /**
     * It parses the json object and sends it to the data store
     * @return
     */
    private String parseJson() {
        try {
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            JSONObject parentObject = new JSONObject(finalJson);
            JSONObject rates = parentObject.getJSONObject("rates");
            dataStore = new DataStore(activity);
            for (String country : rate) {
                exchangeRate = rates.getDouble(country);
                dataStore.saveRateData(country, exchangeRate.floatValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exchangeRate.toString();
    }
}
