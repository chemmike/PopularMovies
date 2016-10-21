package com.sundermannchemistry.popularmovies;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by chemm on 10/7/2016.
 */
public class MovieIconsAdapter extends ArrayAdapter {

    private static final String LOG_TAG = MovieIconsAdapter.class.getSimpleName();

    // the context is used to inflate the layout file and the List is the data we want to populate into the lists
    public MovieIconsAdapter(Activity context, ArrayList<String> trialForIndividualMovies)
    {
        //initalize the ArrayAdapter's internal storage for the context and the list
        super(context, 0, trialForIndividualMovies);
    }

    // provide a view for the AdapterView (ListView or GridView)
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        // if this is a new View object, inflate the layout
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_icon_item, parent, false);
        }
        // modify the View widgets

        ImageView iconView = (ImageView) convertView.findViewById(R.id.grid_icon_image);
        Picasso.with(context).load(trialForIndividualMovies.get(position)).into(iconView);
        return convertView;
    }
}
