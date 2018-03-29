package com.example.android.musicplayerstructure;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * {@link SongsActivity} is the activity to display the songs list found in folder
 * and to launch the player activity to play the selected song
 */
public class SongsActivity extends AppCompatActivity {

    MediaMetadataRetriever mediaMetadataRetriever;
    private String mFolderName = "";
    // Create a list of songs found in the folder given by user
    private ArrayList<MySong> songs = new ArrayList<MySong>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String folderPath = "";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        // get the music folder name given by user
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mFolderName = extras.getString("EXTRA_FOLDER_NAME");
            String rootFolder = Environment.getExternalStorageDirectory().getPath() + "/";
            folderPath = rootFolder + mFolderName + "/";  // get folder path from MainActivity
        } else {
            Toast.makeText(this, R.string.miss_folder_name, Toast.LENGTH_LONG).show();
        }
        // create meta data retriever object --> must be done here only one time not each time in addSong()!
        mediaMetadataRetriever = new MediaMetadataRetriever();
        // get all mp3 files from music folder and its subfolders and populate the songs array list
        File musicFolder = new File(folderPath);
        File[] files = musicFolder.listFiles();
        if (null != files && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    searchFolder(file);     // search in subfolder
                } else {
                    addSong(file);          // add song to list
                }
            }
        }
        // Create an {@link MySongAdapter}, whose data source is a list of {@link MySong}s. The
        // adapter knows how to create list items for each item in the list.
        MySongAdapter songsAdapter = new MySongAdapter(this, songs);
        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // activity_songs.xml file.
        ListView listView = (ListView) findViewById(R.id.list);
        // Make the {@link ListView} use the {@link SongAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Song} in the list.
        listView.setAdapter(songsAdapter);

        // Set on click listener for each song in list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Create a new intent to open the {@link PlayerActivity}
                Intent playSong = new Intent(SongsActivity.this, PlayerActivity.class);
                MySong currentSong = songs.get(position);
                // Pass song data to Player Activity
                Bundle extras = new Bundle();
                extras.putString("EXTRA_FOLDER_NAME", mFolderName);
                extras.putString("EXTRA_SONG_TITLE", currentSong.getTitle());
                extras.putString("EXTRA_SONG_ALBUM_ARTIST", currentSong.getAlbumArtist());
                extras.putString("EXTRA_SONG_ALBUM_NAME", currentSong.getAlbumName());
                extras.putString("EXTRA_SONG_ARTIST", currentSong.getArtist());
                extras.putString("EXTRA_SONG_GENRE", currentSong.getGenre());
                extras.putString("EXTRA_SONG_YEAR", currentSong.getYear());
                extras.putString("EXTRA_SONG_DURATION", currentSong.getDuration());
                extras.putString("EXTRA_SONG_BIT_RATE", currentSong.getBitRate());
                extras.putString("EXTRA_SONG_ABSOLUTE_PATH", currentSong.getAbsolutePath());
                extras.putString("EXTRA_SONG_TRACK_NUMBER", currentSong.getTrackNumber());
                extras.putString("EXTRA_SONG_TYPE", currentSong.getType());
                extras.putLong("EXTRA_SONG_ALBUM_IMAGE", currentSong.getImageResourceId());
                extras.putLong("EXTRA_SONG_POSITION", position);
                playSong.putExtras(extras);
                // start PlayerActivity and finish the current activity SongsActivity
                playSong.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(playSong);    // Start the Player Activity
                songs = null;
                finish();
            }
        });

        // Set on click listener for Back button
        ImageButton imgBackButton = findViewById(R.id.back_button);
        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start MainActivity and finish the current activity SongsActivity
                Intent intent = new Intent(SongsActivity.this, MainActivity.class);
                startActivity(intent);
                songs = null;
                finish();
            }
        });
    }

    /**
     * Get all files from the folder (and all subfolders) given by user and add them to songs list
     *
     * @param folder The folder to search for songs
     */
    private void searchFolder(File folder) {
        if (folder != null) {        // if is not folder get all file list
            File[] listFiles = folder.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file.isDirectory()) {
                        searchFolder(file); // if is subfolder --> search subfolder
                    } else {
                        addSong(file);      // if is not subfolder (is a file) --> add file to list
                    }
                }
            }
        }
    }

    /**
     * Retrieve meta data for each song and add the song to songs list
     *
     * @param song The song (file) to retrieve meta data
     */
    private void addSong(File song) {

    String [] fileExtension = {     // all audio file extensions
            ".3gp",
            ".aa",
            ".aac",
            ".aax",
            ".act",
            ".aiff",
            ".amr",
            ".ape",
            ".au",
            ".awb",
            ".dct",
            ".dss",
            ".dvf",
            ".flac",
            ".gsm",
            ".iklax",
            ".ivs",
            ".m4a",
            ".m4b",
            ".m4p",
            ".mmf",
            ".mp3",
            ".mpc",
            ".msv",
            ".ogg",
            ".oga",
            ".mogg",
            ".opus",
            ".ra",
            ".rm",
            ".raw",
            ".sln",
            ".tta",
            ".vox",
            ".wav",
            ".wma",
            ".wv",
            ".webm",
            ".8svx"
    };

        String fileName = song.getName().toLowerCase();

        for(int i = 0; i < fileExtension.length; i++) {
            if (fileName.endsWith(fileExtension[i])) {
                // set meta data retriever object  for current song
                mediaMetadataRetriever.setDataSource(song.getAbsolutePath());
                // retrieve meta data for each song and add the song to songs list
                String mAbsolutePath = song.getAbsolutePath();
                if (mAbsolutePath == null) mAbsolutePath = getString(R.string.absolute_path_NA);
                String mAlbumName = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
                if (mAlbumName == null) mAlbumName = getString(R.string.album_name_NA);
                String mTitle = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                if (mTitle == null) mTitle = getString(R.string.title_NA);
                String mArtist = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                if (mArtist == null) mArtist = getString(R.string.artist_NA);
                String mGenre = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
                if (mGenre == null) mGenre = getString(R.string.genre_NA);
                String mYear = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR);
                if (mYear == null) mYear = getString(R.string.year_NA);
                String mDuration = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                if (mDuration == null) mDuration = getString(R.string.duration_NA);
                String mBitRate = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE);
                if (mBitRate == null) {
                    mBitRate = getString(R.string.bit_rate_NA);
                } else {
                    mBitRate += " bps";
                }
                String mTrackNumber = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CD_TRACK_NUMBER);
                if (mTrackNumber == null) mTrackNumber = getString(R.string.track_number_NA);
                String mType = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_AUDIO);
                if (mType == null) mType = getString(R.string.type_NA);
                String mAlbumArtist = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST);
                if (mAlbumArtist == null) mAlbumArtist = getString(R.string.album_artist_NA);
                // Adding each song to SongList
                songs.add(new MySong(mAlbumName, mTitle, mArtist, mGenre, mYear, mDuration, mBitRate,
                        mAbsolutePath, mTrackNumber, mAlbumArtist, mType, R.drawable.no_cover));
            }
        }
    }

}
