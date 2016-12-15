package com.sundermannchemistry.popularmovies;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */

// This is the grid view of Movie versions
public class MainActivityFragment extends Fragment
{
    public static final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    private MovieIconsAdapter iconsAdapter;
    private MovieIconsAdapter newIconsAdapter;
    Boolean networkIsOn;
    static boolean useLargeLayout;

    ArrayList<String> individualMovies = new ArrayList<String>();
    ArrayList<String> movieOverview = new ArrayList<String>();
    ArrayList<String> movieReleaseDate = new ArrayList<String>();
    ArrayList<String> movieTitle = new ArrayList<String>();
    ArrayList<String> movieVoteAverage = new ArrayList<String>();



    public MainActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        iconsAdapter = new MovieIconsAdapter (getActivity(), individualMovies);

        // get a reference to the GridView and attach this adapter to it
        GridView gridView = (GridView) rootView.findViewById(R.id.movie_icons_grid);
        gridView.setAdapter(iconsAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            // AdapterView<> adapterView refers to the parent view that was clicked
            // View: The view within the AdapterView that was clicked (this will be a view provided by the adapter
            // position: The position of the view in the adapter
            // l: The row id of the item that was clicked
            // Implementers can call getItemAtPosition(position) if they need to access the data associated with the selected item
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                String chosenOverview = movieOverview.get(position);
                String chosenReleaseDate = movieReleaseDate.get(position);
                String chosenTitle = movieTitle.get(position);
                String chosenVoteAverage = movieVoteAverage.get(position);
                String chosenMovieIcon = individualMovies.get(position);


                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra(DetailActivity.OVERVIEW_CHOSEN, chosenOverview);
                    intent.putExtra(DetailActivity.RELEASE_DATE_CHOSEN, chosenReleaseDate);
                    intent.putExtra(DetailActivity.TITLE_CHOSEN, chosenTitle);
                    intent.putExtra(DetailActivity.VOTE_AVERAGE_CHOSEN, chosenVoteAverage);
                    intent.putExtra(DetailActivity.ICON_CHOSEN, chosenMovieIcon);
                    intent.putExtra(DetailActivity.GRID_POSITION, position);
                    String positionDisplayer = String.valueOf(position);
                    Log.i(LOG_TAG, "GridView Position = " + positionDisplayer);
                    startActivity(intent);

            }
        });

        return rootView;
    }

    public static void setUseLargeLayout(boolean infoFromMainActivity)
    {
        useLargeLayout = infoFromMainActivity;
        if (useLargeLayout)
        {
            Log.w(LOG_TAG, "USE LARGE LAYOUT true");
        }
        else
        {
            Log.w(LOG_TAG, "USE LARGE LAYOUT false");
        }
    }

    private void UpdateMovie()
    {
        // get information from MovieIconsAdapter
        Bundle sendNetworkInfo = getArguments();
        networkIsOn = sendNetworkInfo.getBoolean("theNetworkInfo");
        boolean canRunMovieTask = networkIsOn.booleanValue();

        if (canRunMovieTask)
        {
            Log.w(LOG_TAG, "MAIN ACTIVITY FRAGMENT true");
        }
        else
        {
            Log.w(LOG_TAG, "MAIN ACTIVITY FRAGMENT false");
        }

        if (canRunMovieTask)
        {
            FetchMovieTask obtainMovieTask = new FetchMovieTask();
            obtainMovieTask.execute();
        }
        else
        {
            Log.i(LOG_TAG, "could not get a network connection");
        }
    }



    @Override
    public void onStart()
    {
        super.onStart();
        UpdateMovie();
    }

    public class FetchMovieTask extends AsyncTask<Void, Void, Boolean> {

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();
        // Will contain the raw JSON response as a string.
        String movieJsonStr = null;
        int movieJsonStringLength;

        @Override
        protected Boolean doInBackground(Void... params) {

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                // get preferences from settings menu
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String movieSorter = prefs.getString(getString(R.string.pref_sort_by_key),
                        getString(R.string.pref_sort_by_popular));
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are available at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                URL url;
                if (movieSorter.equals(getString(R.string.pref_sort_by_popular)))
                {
                    url = new URL("https://api.themoviedb.org/3/movie/popular?api_key=[MY_KEY]");
                }
                else
                {
                    url = new URL("https://api.themoviedb.org/3/movie/top_rated?api_key=[MY_KEY]");
                }

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    movieJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    movieJsonStr = null;
                }
                movieJsonStr = buffer.toString();


            } catch (IOException e) {
                Log.e("MainActivityFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                movieJsonStr = null;

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("MainActivityFragment", "Error closing stream", e);
                    }
                }
            }
            return null;
        }
        protected void onPostExecute (Boolean runThePostCode){
            Log.i(LOG_TAG, "ORIGINAL STRING = " + movieJsonStr);
            int originalMovieStringLength = movieJsonStr.length();
            int movieStringFirstIndex = 0;
            int movieStringSecondIndex = 0;
            int movieStringFirstJpgIndex = 0;
            int movieStringSecondJpgIndex = 0;
            int movieStringFirstOverviewIndex = 0;
            int movieStringFirstReleaseDateIndex = 0;
            int movieStringFirstTitleIndex = 0;
            int movieStringFirstLanguageIndex = 0;
            int movieStringFirstVoteAverageIndex = 0;
            int websiteTracker = 1;
            boolean atLastMovieString;

            individualMovies.clear();
            movieOverview.clear();
            movieReleaseDate.clear();
            movieTitle.clear();
            movieVoteAverage.clear();
            
            int originalStringLastMovieIndex = movieJsonStr.lastIndexOf("\"poster_path\":\"");

            while ((websiteTracker >= 0) && (movieJsonStr != null))
            {
                movieStringFirstIndex = movieJsonStr.indexOf("\"poster_path\":\"", websiteTracker);
                if (movieStringFirstIndex < 0)
                {
                    break;
                }
                movieStringFirstJpgIndex = movieJsonStr.indexOf(".jpg", websiteTracker);
                movieStringFirstOverviewIndex = movieJsonStr.indexOf("\"overview\"", websiteTracker);
                movieStringFirstReleaseDateIndex = movieJsonStr.indexOf("\"release_date\"", websiteTracker);
                movieStringFirstTitleIndex = movieJsonStr.indexOf("\"original_title\"", websiteTracker);
                movieStringFirstLanguageIndex = movieJsonStr.indexOf("\"original_language\"", websiteTracker);
                movieStringFirstVoteAverageIndex = movieJsonStr.indexOf("\"vote_average\"", websiteTracker);

                movieJsonStringLength = movieJsonStr.length();
                //String displayLength = String.valueOf(movieJsonStringLength);
                //Log.i(LOG_TAG, "Overall length = " + displayLength);
                //String displayTracker = String.valueOf(websiteTracker);
                //Log.i(LOG_TAG, "website tracker = " + displayTracker);
                //String displayFirstPoster = String.valueOf(movieStringFirstIndex);
                //Log.i(LOG_TAG, "poster first index = " + displayFirstPoster);
                //String displayFirstJpg = String.valueOf(movieStringFirstJpgIndex);
                //Log.i(LOG_TAG, "jpg = " + displayFirstJpg);
                //String displayFirstOverview = String.valueOf(movieStringFirstOverviewIndex);
                //Log.i(LOG_TAG, "overview = " + displayFirstOverview);
                //String displayFirstDate = String.valueOf(movieStringFirstReleaseDateIndex);
                //Log.i(LOG_TAG, "release date = " + displayFirstDate);
                //String displayFirstTitle = String.valueOf(movieStringFirstTitleIndex);
                //Log.i(LOG_TAG, "title = " + displayFirstTitle);
                //String displayFirstLang = String.valueOf(movieStringFirstLanguageIndex);
                //Log.i(LOG_TAG, "language = " + displayFirstLang);
                //String displayFirstVote = String.valueOf(movieStringFirstVoteAverageIndex);
                //Log.i(LOG_TAG, "vote average = " + displayFirstVote);

                String nextOverview = movieJsonStr.substring(movieStringFirstOverviewIndex + 12,movieStringFirstReleaseDateIndex - 2);
                movieOverview.add(nextOverview);
                String nextReleaseDate = movieJsonStr.substring(movieStringFirstReleaseDateIndex + 16, movieStringFirstReleaseDateIndex + 26);
                movieReleaseDate.add(nextReleaseDate);
                String nextTitle = movieJsonStr.substring(movieStringFirstTitleIndex + 18, movieStringFirstLanguageIndex - 2);
                movieTitle.add(nextTitle);
                String nextVoteAverage;
                if (movieJsonStr.substring(movieStringFirstVoteAverageIndex + 16, movieStringFirstVoteAverageIndex + 17).equals(".")) {
                    nextVoteAverage = movieJsonStr.substring(movieStringFirstVoteAverageIndex + 15, movieStringFirstVoteAverageIndex + 18);
                }
                else
                {
                    nextVoteAverage = movieJsonStr.substring(movieStringFirstVoteAverageIndex + 15, movieStringFirstVoteAverageIndex + 16) + ".0";
                }
                movieVoteAverage.add(nextVoteAverage);

                Log.i(LOG_TAG, "Actual Title = " + nextTitle);
                
                atLastMovieString = false;
                if (movieStringFirstIndex == originalStringLastMovieIndex)
                {
                    atLastMovieString = true;
                }

                if (!atLastMovieString) {
                    movieStringSecondIndex = movieJsonStr.indexOf("\"poster_path\":\"", movieStringFirstIndex + 16);
                    movieStringSecondJpgIndex = movieJsonStr.indexOf("\"popularity\"", movieStringFirstJpgIndex + 4);
                }

                String sub = movieJsonStr.substring(movieStringFirstIndex + 16, movieStringFirstJpgIndex + 4);
                individualMovies.add(sub);
                Log.i(LOG_TAG, sub);

                if (atLastMovieString)
                {
                    break;
                }
                else
                {
                        websiteTracker = movieStringSecondJpgIndex + 5;
                }
            }
            // reinitialize adapter
            iconsAdapter.updateData(individualMovies);

        }
    }
}
