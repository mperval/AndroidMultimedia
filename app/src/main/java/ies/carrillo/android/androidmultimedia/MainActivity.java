package ies.carrillo.android.androidmultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
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
    private int globalPosition = 0;
    private boolean isPaused;

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

        lvSongs.setOnItemClickListener((parent, view, position, id) ->{
            isPaused = false;
            globalPosition = position;//para el boton de siguiente.

            btnPlay.setImageResource(R.drawable.ic_pause);

            playSong(songs.get(globalPosition));

        });
        //BOTONES
        
        btnNext.setOnClickListener(v ->{
            AssetFileDescriptor descriptor = null;

                if(btnNext.isPressed()){
                    globalPosition += 1;
                }
                if(globalPosition == songs.size()){
                    globalPosition = 0;
                }
                playSong(songs.get(globalPosition));
        });

        btnPlay.setOnClickListener(v -> {
            if (isPaused && mediaPlayer != null) {
                btnPlay.setImageResource(R.drawable.ic_play);
                if (songs.size() > 0) {
                    isPaused = false;
                    mediaPlayer.start();
                }
            } else {
                if (mediaPlayer == null && songs.size() > 0) {
                    isPaused = false;
                    globalPosition = 0;
                    playSong(songs.get(globalPosition));
                } else {
                    isPaused = true;
                    if (mediaPlayer != null) {
                        mediaPlayer.pause();
                    }
                    btnPlay.setImageResource(R.drawable.ic_pause);
                }
            }
        });
        btnPrevius.setOnClickListener(v -> {
            globalPosition -= 1;
            if (globalPosition < 0) {
                globalPosition = songs.size() - 1;
            }
            playSong(songs.get(globalPosition));
        });

        btnStop.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                btnPlay.setImageResource(R.drawable.ic_play);
            }
        });
    }
    public void loadComponents(){
        lvSongs = findViewById(R.id.lvSongs);
        btnNext = findViewById(R.id.btnNext);
        btnStop = findViewById(R.id.btnStop);
        btnPlay = findViewById(R.id.btnPlay);
        btnPrevius = findViewById(R.id.btnPrevius);
        isPaused = true;
        songs = new ArrayList<>();
    }
    public void playSong(Song song){
        AssetFileDescriptor descriptor = null;
        try {
            song = songs.get(globalPosition);

            String filename = song.getArtist() + "-" + song.getTitle();
            descriptor = assetManager.openFd(filename);
            if(mediaPlayer != null){
                mediaPlayer.reset();
            }
            //Param1: nombre del archivo
            //Param2: momento del inicio
            //Param3: longitud del archivo
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();

        }catch(Exception e){
            Log.e("Error reproductor", e.getMessage());
        }
    }
}