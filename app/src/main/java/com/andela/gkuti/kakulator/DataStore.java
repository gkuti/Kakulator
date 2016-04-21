package com.andela.gkuti.kakulator;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class DataStore {
    SharedPreferences sharedPreferences;
    Editor editor;
    public DataStore(Context context){
        sharedPreferences = context.getSharedPreferences("Kakulator", 0);
        editor = sharedPreferences.edit();
    }
    public void saveRateData(String key, float value){
        editor.putFloat(key, value);
        editor.commit();
    }
}
