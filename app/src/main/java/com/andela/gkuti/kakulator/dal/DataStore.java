package com.andela.gkuti.kakulator.dal;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.andela.gkuti.kakulator.util.Constants;

/**
 * DataStore class
 */
public class DataStore {
    private SharedPreferences sharedPreferences;
    private Editor editor;

    /**
     * Constructor for DataStore class
     *
     * @param context activity context to be referenced
     */
    public DataStore(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.DATA_FILENAME.getValue(), 0);
        editor = sharedPreferences.edit();
    }

    /**
     * it stores float data to the Sharedpreferences data location
     *
     * @param key   the key for the data
     * @param value the value of the key
     */
    public void saveRateData(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    /**
     * returns the value for the specified key
     *
     * @param key the key to search for
     * @return float value or 0 if not found
     */
    public float getRateData(String key) {
        return sharedPreferences.getFloat(key, 0);
    }

    /**
     * it stores int data to the Sharedpreferences data location
     *
     * @param key   the key for the data
     * @param value the value of the key
     */
    public void saveData(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * returns the value for the specified key
     *
     * @param key the key to search for
     * @return String value or 0 if not found
     */
    public int getData(String key) {
        return sharedPreferences.getInt(key, 0);
    }
}
