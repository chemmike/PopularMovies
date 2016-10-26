package com.sundermannchemistry.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class DetailActivity extends ActionBarActivity {

    public static final String LOG_TAG = DetailActivity.class.getSimpleName();
    public static final String OVERVIEW_CHOSEN = "overview chosen";
    public static final String RELEASE_DATE_CHOSEN = "release date chosen";
    public static final String TITLE_CHOSEN = "title chosen";
    public static final String VOTE_AVERAGE_CHOSEN = "vote average chosen";
    public static final String ICON_CHOSEN = "icon chosen";
    public static final String GRID_POSITION = "grid position";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // this adds the fragment to the activity
        if (savedInstanceState == null) {

         }

            Intent intent = getIntent();
            String sendingOverview = intent.getStringExtra(OVERVIEW_CHOSEN);
            String sendingReleaseDate = intent.getStringExtra(RELEASE_DATE_CHOSEN);
            String sendingTitle = intent.getStringExtra(TITLE_CHOSEN);
            String sendingVoteAverage = intent.getStringExtra(VOTE_AVERAGE_CHOSEN);
            String sendingIcon = intent.getStringExtra(ICON_CHOSEN);
            int sendingPosition = intent.getIntExtra(GRID_POSITION, 0);

            Bundle args = new Bundle();
            args.putString("theOverview", sendingOverview);
            args.putString("theReleaseDate", sendingReleaseDate);
            args.putString("theTitle", sendingTitle);
            args.putString("theVoteAverage", sendingVoteAverage);
            args.putString("theIcon", sendingIcon);
            args.putInt("theChosenMovie", sendingPosition);
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
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}