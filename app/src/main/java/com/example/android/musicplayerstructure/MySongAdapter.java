package com.example.android.musicplayerstructure;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Cristi on 21.03.2018.
 */

public class MySongAdapter extends ArrayAdapter<MySong> {

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param songs          A List of MySong objects to display in a list
     */
    public MySongAdapter(Activity context, ArrayList<MySong> songs) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, songs);

    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return T          The View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View songsListItemView = convertView;
        if(songsListItemView == null) {
            // if don't exist a list view that we can reuse we inflates a new list item view from songs_item.xml
            // false  parameter because we don't want to attach the songs list item view to the parent
            songsListItemView = LayoutInflater.from(getContext()).inflate(R.layout.songs_item, parent, false);
        }
        // Get the {@link MySong} object located at this position in the list
        MySong currentMySong = getItem(position);
        // Find the TextView in the songs_item.xml layout with the ID song_list_Title
        TextView songListTitle = (TextView) songsListItemView.findViewById(R.id.song_list_Title);
        // set this text on the title TextView
        songListTitle.setText(currentMySong.getTitle());
        // Find the TextView in the songs_item.xml layout with the ID song_list_Album_Artist
        TextView songListAlbumArtist = (TextView) songsListItemView.findViewById(R.id.song_list_Album_Artist);
        // set this text on the album artist TextView
        songListAlbumArtist.setText(currentMySong.getArtist());
        // Find the TextView in the songs_item.xml layout with the ID song_list_Absolute_Path
        TextView songListAbsolutePath = (TextView) songsListItemView.findViewById(R.id.song_list_Absolute_Path);
        // set this text on the album artist TextView
        songListAbsolutePath.setText(currentMySong.getAbsolutePath());
/*
        // Find the ImageView in the songs_item.xml layout with the ID song_list_item_icon
        ImageView iconView = (ImageView) songsListItemView.findViewById(R.id.song_list_item_icon);
        // Get the image resource ID from the current MySong object and
        // set the image to iconView
        iconView.setImageResource(currentMySong.getImageResourceId());
*/
        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return songsListItemView;
    }




}
