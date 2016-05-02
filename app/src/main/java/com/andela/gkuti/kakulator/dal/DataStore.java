package com.andela.gkuti.kakulator.dal;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.andela.gkuti.kakulator.util.Constants;

public class DataStore {
    private SharedPreferences sharedPreferences;
    private Editor editor;

    public DataStore(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.DATA_FILENAME.getValue(), 0);
        editor = sharedPreferences.edit();
    }

    public void saveRateData(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public float getRateData(String key) {
        return sharedPreferences.getFloat(key, 0);
    }

    public void saveData(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public int getData(String key) {
        return sharedPreferences.getInt(key, 0);
    }
}
