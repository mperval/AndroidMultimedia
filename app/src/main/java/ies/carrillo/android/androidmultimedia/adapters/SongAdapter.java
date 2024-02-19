package ies.carrillo.android.androidmultimedia.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ies.carrillo.android.androidmultimedia.R;
import ies.carrillo.android.androidmultimedia.models.Song;

public class SongAdapter extends ArrayAdapter<Song> {
TextView tvTitle, tvAlert;
    private List<Song> songs;
    private Context context;

    public SongAdapter(Context context, List<Song> songs){
        super(context, 0, songs);
        this.context = context;
        this.songs = songs;
    }

    @Override
    public int getCount(){
        return songs.size();
    }

    public View getView(int position, View convertView, ViewGroup parent){
        convertView = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false);

        Song song = getItem(position);

        tvTitle = convertView.findViewById(R.id.tvTitle);
        tvAlert = convertView.findViewById(R.id.tvAlert);

        tvTitle.setText(song.getTitle());
        tvAlert.setText(song.getArtist());

        return convertView;
    }
}
