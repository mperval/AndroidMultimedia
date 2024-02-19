package ies.carrillo.android.androidmultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ies.carrillo.android.androidmultimedia.adapters.SongAdapter;
import ies.carrillo.android.androidmultimedia.models.Song;

public class MainActivity extends AppCompatActivity {
    private ListView lvSongs;
    private ImageButton btnPrevius, btnNext, btnStop, btnPlay;
    private SongAdapter songAdapter;
    private List<Song> songs;
    private AssetManager assetManager;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadComponents();

        // Cargar la lista de canciones del directorio assets
        assetManager = getAssets();
        try {
            for(String file : assetManager.list("")){
                Log.i("cancion", file);

                String[] filename = file.split("-");
                if(filename.length == 2){
                    Song song = new Song();
                    song.setArtist(filename[0]);
                    song.setTitle(filename[1]);

                    songs.add(song);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        songAdapter = new SongAdapter(this, songs);
        lvSongs.setAdapter(songAdapter);
    }
    public void loadComponents(){
        lvSongs = findViewById(R.id.lvSongs);
        btnNext = findViewById(R.id.btnNext);
        btnStop = findViewById(R.id.btnStop);
        btnPlay = findViewById(R.id.btnPlay);
        btnPrevius = findViewById(R.id.btnPrevius);
        songs = new ArrayList<>();
    }
}