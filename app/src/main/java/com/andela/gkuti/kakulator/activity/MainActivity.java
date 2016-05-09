package com.andela.gkuti.kakulator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.andela.gkuti.kakulator.R;
import com.andela.gkuti.kakulator.dal.DataStore;
import com.andela.gkuti.kakulator.util.Network;

/**
 * MainActivity class
 */
public class MainActivity extends AppCompatActivity {
    private int update;
    private int appState;
    private Network network;

    /**
     * method called when the activity is started
     *
     * @param
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponent();
    }

    /**
     * called to instantiate fields
     */
    private void initializeComponent() {
        DataStore dataStore = new DataStore(this);
        update = dataStore.getData("update");
        appState = dataStore.getData("appState");
        network = new Network(this);
        network.checkNetwork(update,appState);
    }

    /**
     * method for inflating menu from xml to java object
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * method that handles click event of icons on the action bar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_renew) {
            network.checkNetwork(update,appState);
            return true;
        }
        if (id == R.id.action_top_ten) {
            Intent intent = new Intent(this, TopTenActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
