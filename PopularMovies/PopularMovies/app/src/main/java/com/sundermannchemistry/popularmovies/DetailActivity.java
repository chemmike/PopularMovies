package com.sundermannchemistry.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class DetailActivity extends ActionBarActivity {

    public static final String LOG_TAG = DetailActivity.class.getSimpleName();
    public static final String FIRST_MESSAGE = "firstMessage";
    public static final String GRID_POSITION = "grid position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // this adds the fragment to the activity
        if (savedInstanceState == null) {

         }


            Intent intent = getIntent();
            String firstMessageText = intent.getStringExtra(FIRST_MESSAGE);
            int chosenGrid = intent.getIntExtra(GRID_POSITION, 0);
            String secondMessageText = "Movie info";
            Log.i(LOG_TAG, "I got here " + firstMessageText);

            Bundle args = new Bundle();
            args.putString("myFragmentMessage", firstMessageText);
            args.putString("myNextFragmentMessage", secondMessageText);
            args.putInt("theChosenMovie", chosenGrid);
            DetailFragment fragInfo = new DetailFragment();
            fragInfo.setArguments(args);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragInfo)
                    .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}