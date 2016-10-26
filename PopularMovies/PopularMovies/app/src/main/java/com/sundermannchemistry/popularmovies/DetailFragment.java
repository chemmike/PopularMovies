package com.sundermannchemistry.popularmovies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    public static final String LOG_TAG = DetailActivity.class.getSimpleName();

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();

        String overviewInformation = args.getString("theOverview");
        String releaseDateInformation = args.getString("theReleaseDate");
        String titleInformation = args.getString("theTitle");
        String voteAverageInformation = args.getString("theVoteAverage");
        String iconInformation = args.getString("theIcon");
        int moviePosition = args.getInt("theChosenMovie");

        Log.i(LOG_TAG, "Position Info Made It! = " + moviePosition);
        Log.i(LOG_TAG, "The Title is " + titleInformation);

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        TextView movieTitleText = (TextView) rootView.findViewById(R.id.titleOfMovie);
        movieTitleText.setText(titleInformation);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.detailMovieIcon);
        String movieIconUrl = "http://image.tmdb.org/t/p/w185/" + iconInformation;
        Picasso.with(imageView.getContext()).load(movieIconUrl).into(imageView);
        TextView movieReleaseDateText = (TextView) rootView.findViewById(R.id.releaseDateOfMovie);
        movieReleaseDateText.setText(releaseDateInformation);
        TextView movieVoteAverageText = (TextView) rootView.findViewById(R.id.voteAverageOfMovie);
        movieVoteAverageText.setText(voteAverageInformation + " / 10");
        TextView movieOverviewText = (TextView) rootView.findViewById(R.id.overviewOfMovie);
        movieOverviewText.setText(overviewInformation);

        return rootView;
    }

}
