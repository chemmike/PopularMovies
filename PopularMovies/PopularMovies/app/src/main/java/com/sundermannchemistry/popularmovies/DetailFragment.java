package com.sundermannchemistry.popularmovies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        Log.i(LOG_TAG, "I got here too " + myValue);
        Log.i(LOG_TAG, "I got here again " + myNextValue);

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        TextView oneMovie = (TextView) rootView.findViewById(R.id.firstMessage);
        oneMovie.setText(myValue);
        // ImageView imageView = (ImageView) view.findViewById(R.id.my_image);

        return rootView;
    }

}
