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
        String myValue = args.getString("myFragmentMessage");
        String myNextValue = args.getString("myNextFragmentMessage");
        long moviePosition = args.getInt("theChosenMovie");

        Log.i(LOG_TAG, "Position Info Made It! = " + moviePosition);
        Log.i(LOG_TAG, "I got here too " + myValue);
        Log.i(LOG_TAG, "I got here again " + myNextValue);

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        TextView oneMovie = (TextView) rootView.findViewById(R.id.firstMessage);
        oneMovie.setText(myValue);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.detailMovieIcon);
        String trialImage = "http://image.tmdb.org/t/p/w185//9O7gLzmreU0nGkIB6K3BsJbzvNv.jpg";
        Picasso.with(imageView.getContext()).load(trialImage).into(imageView);



        return rootView;
    }

}
