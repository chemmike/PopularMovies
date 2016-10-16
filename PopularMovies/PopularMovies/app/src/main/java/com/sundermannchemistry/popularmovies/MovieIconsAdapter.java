package com.sundermannchemistry.popularmovies;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by chemm on 10/7/2016.
 */
public class MovieIconsAdapter extends ArrayAdapter {

    private static final String LOG_TAG = MovieIconsAdapter.class.getSimpleName();

    // the context is used to inflate the layout file and the List is the data we want to populate into the lists
    public MovieIconsAdapter(Activity context, List<MovieIcons> currentMovieIcons)
    {
        //initalize the ArrayAdapter's internal storage for the context and the list
        super(context, 0, currentMovieIcons);
    }

    // provide a view for the AdapterView (ListView or GridView)
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // gets the MovieIcons object from the ArrayAdapter at the appropriate position
        // let the compiler know that the Object that is returned is actually a MovieIcons object
        MovieIcons movieIcon = (MovieIcons)getItem(position);

        // if this is a new View object, inflate the layout
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_icon_item, parent, false);
        }
        // modify the View widgets
        ImageView iconView = (ImageView) convertView.findViewById(R.id.grid_icon_image);
        iconView.setImageResource(movieIcon.image);
        return convertView;
    }
}
