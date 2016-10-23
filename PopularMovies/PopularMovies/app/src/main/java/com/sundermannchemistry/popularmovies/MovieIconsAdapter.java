package com.sundermannchemistry.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by chemm on 10/7/2016.
 */
public class MovieIconsAdapter extends BaseAdapter {

    private static final String LOG_TAG = MovieIconsAdapter.class.getSimpleName();
    private Context context;
    private ArrayList<String> individualMovies;
    ArrayList<String> newData;

    // the context is used to inflate the layout file and the List is the data we want to populate into the lists
    public MovieIconsAdapter(Activity context, ArrayList<String> individualMovies)
    {
        // need to create a reference to context and Movies so that the GetView method can use them
        this.context = context;
        this.individualMovies = individualMovies;
    }

    public void updateData(ArrayList<String> individualMovies){
        newData = individualMovies;
        Log.i(LOG_TAG, newData.get(1));
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return individualMovies.size();
    }

    @Override
    public Object getItem(int position) {
        return individualMovies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // provide a view for the AdapterView (ListView or GridView)
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        // if this is a new View object, inflate the layout
        if (convertView == null)
        {
            LayoutInflater layoutInflater = ((MainActivity)context).getLayoutInflater();
            convertView = layoutInflater.inflate(R.layout.grid_icon_item, parent, false);
        }
        // modify the View widgets
        Log.i(LOG_TAG, "Got into getView in GridView");
        ImageView iconView = (ImageView) convertView.findViewById(R.id.grid_icon_image);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185/" + newData.get(position)).into(iconView);
        return convertView;
    }
}
