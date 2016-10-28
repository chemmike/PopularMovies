package com.sundermannchemistry.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    boolean canGetInformation;

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canGetInformation = isNetworkAvailable();
        Bundle sendNetworkInfo = new Bundle();
        sendNetworkInfo.putBoolean("theNetworkInfo", canGetInformation);
        MainActivityFragment netFragInfo = new MainActivityFragment();
        netFragInfo.setArguments(sendNetworkInfo);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, netFragInfo)
                .commit();
    }

    // inflate the menu and add items to action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // handle action bar item clicks
    // make sure the parent activity is specified in the manifest so that the Home/Up button works
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
