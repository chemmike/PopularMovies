package com.sundermannchemistry.popularmovies;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */

// This is the grid view of Movie versions
public class MainActivityFragment extends Fragment
{
    public static final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    private MovieIconsAdapter iconsAdapter;

    // Will contain the raw JSON response as a string.
    String movieJsonStr = null;
    int movieJsonStringLength;

    public MainActivityFragment() {
        // Required empty public constructor
    }

    MovieIcons[] currentMovieIcons = {
            new MovieIcons (R.drawable.cupcake_movie) ,
            new MovieIcons (R.drawable.donut_movie) ,
            new MovieIcons (R.drawable.eclair_movie) ,
            new MovieIcons (R.drawable.froyo_movie) ,
            new MovieIcons (R.drawable.gingerbread_movie) ,
            new MovieIcons (R.drawable.honeycomb_movie) ,
            };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        iconsAdapter = new MovieIconsAdapter (getActivity(), Arrays.asList(currentMovieIcons));

        // get a reference to the GridView and attach this adapter to it
        GridView gridView = (GridView) rootView.findViewById(R.id.movie_icons_grid);
        gridView.setAdapter(iconsAdapter);

        Log.i(LOG_TAG, "I got to position 1");

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
                String firstMessageText = "The Movie";
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(DetailActivity.FIRST_MESSAGE, firstMessageText);
                startActivity(intent);

            }
        });

        return rootView;
    }

    private void UpdateMovie()
    {
        FetchMovieTask obtainMovieTask = new FetchMovieTask();
        obtainMovieTask.execute();
    }

    private void ManipulateMovieString()
    {
        Log.i(LOG_TAG, "MANIPULATE" + movieJsonStr);
        int websiteFirstIndex = 0;
        int websiteLastIndex = 0;
        int websiteTracker = 1;
        ArrayList<String> individualMovies = new ArrayList<String>();

        while ((websiteTracker >= 0) && (movieJsonStr != null))
        {
            websiteFirstIndex = movieJsonStr.indexOf("\"poster_path\":\"", websiteTracker);
            Log.i(LOG_TAG, "WEBSITE LOOP");
            if (websiteFirstIndex < 0)
            {
                break;
            }
            websiteLastIndex = movieJsonStr.indexOf(".jpg", websiteTracker);
            if (websiteFirstIndex >= 0) {
                String sub = movieJsonStr.substring(websiteFirstIndex + 16, websiteLastIndex + 4);
                Log.i(LOG_TAG, sub);
                individualMovies.add(sub);
                websiteTracker = websiteLastIndex + 5;
            }
            else
            {
                websiteTracker = -1;
            }
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        UpdateMovie();
        ManipulateMovieString();
    }

    public class FetchMovieTask extends AsyncTask<Void, Void, Void> {

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        @Override
        protected Void doInBackground(Void... params) {

            Log.i(LOG_TAG, "I got to position 2");

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;



            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are available at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                URL url = new URL("https://api.themoviedb.org/3/movie/popular?api_key=[my key]");

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
        protected String onPostExecute (){
            return movieJsonStr;
        }
    }
}
