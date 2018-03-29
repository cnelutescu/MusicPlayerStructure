package com.example.android.musicplayerstructure;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * {@link PlayerActivity} is the activity to display the selected song info by user and play the song
 *
 */
public class PlayerActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener,
        SeekBar.OnSeekBarChangeListener {

    boolean isPlaying = true;
    ImageView imgPlayButton;
    ImageView imgPreviousButton;
    ImageView imgNextButton;
    private String mFolderName = "";      // store folder name to pass it back to SongsActivity

    /**
     * Function to convert total duration from milliseconds into time hh:mm:ss
     * Timer Format
     * Hours:Minutes:Seconds
     *
     * @param milliseconds the duration in milliseconds
     */
    public static String formatMilliSecond(long milliseconds) {

        String finalTimerString = "";
        String secondsString;
        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }
        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }
        finalTimerString = finalTimerString + minutes + ":" + secondsString;
        // return timer string
        return finalTimerString;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Toast.makeText(this, R.string.playing_song, Toast.LENGTH_SHORT).show();

        // get the music folder name given by user
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            // String str;
            mFolderName = extras.getString("EXTRA_FOLDER_NAME");  // get folder name from SongsActivity
            // str = extras.getString("EXTRA_SONG_TITLE");  // get folder name from SongsActivity
            TextView textView;
            textView = findViewById(R.id.song_Title);
            textView.setText(extras.getString("EXTRA_SONG_TITLE"));
            textView = findViewById(R.id.song_Album_Name);
            textView.setText(extras.getString("EXTRA_SONG_ALBUM_NAME"));
            textView = findViewById(R.id.song_Artist);
            textView.setText(extras.getString("EXTRA_SONG_ARTIST"));
            textView = findViewById(R.id.song_Genre);
            textView.setText(extras.getString("EXTRA_SONG_GENRE"));
            textView = findViewById(R.id.song_Year);
            textView.setText(extras.getString("EXTRA_SONG_YEAR"));

            int milliDuration = Integer.parseInt(extras.getString("EXTRA_SONG_DURATION"));
            String mDuration = formatMilliSecond(milliDuration);
            textView = findViewById(R.id.song_Duration);
            textView.setText(mDuration);
            textView = findViewById(R.id.total_time);
            textView.setText(mDuration);

            textView = findViewById(R.id.song_BitRate);
            textView.setText(extras.getString("EXTRA_SONG_BIT_RATE"));
            textView = findViewById(R.id.song_AbsolutePath);
            textView.setText(extras.getString("EXTRA_SONG_ABSOLUTE_PATH"));
            textView = findViewById(R.id.song_TrackNumber);
            textView.setText(extras.getString("EXTRA_SONG_TRACK_NUMBER"));

/*  TODO: in new version of app will set the album cover, test song type, use album artist and position in the song list
            textView = findViewById(R.id.song_Album_Artist);
            textView.setText(extras.getString("EXTRA_SONG_ALBUM_ARTIST"));
            textView = findViewById(R.id.song_Type);
            textView.setText(extras.getString("EXTRA_SONG_TYPE"));
            ImageView imageView;
            imageView = findViewById(R.id.song_cover);
            imageView.setImageResource(extras.getInt("EXTRA_SONG_ALBUM_IMAGE"));
            int position = extras.getInt("EXTRA_SONG_POSITION");
*/

        } else {
            Toast.makeText(this, R.string.miss_folder_name, Toast.LENGTH_LONG).show();
        }

        // Set on click listener for Back button
        ImageButton imgBackButton = findViewById(R.id.back_button);
        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayerActivity.this, SongsActivity.class);
                Bundle extras = new Bundle();
                extras.putString("EXTRA_FOLDER_NAME", mFolderName); //pass folder name back to SongsActivity
                intent.putExtras(extras);
                startActivity(intent);
                finish();
            }
        });

        // Set on click listener for Play button
        imgPlayButton = findViewById(R.id.play_button);
        imgPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    imgPlayButton.setImageResource(R.drawable.btn_play);
                    isPlaying = false;
                    Toast.makeText(PlayerActivity.this, R.string.pause_song, Toast.LENGTH_LONG).show();
                } else {
                    imgPlayButton.setImageResource(R.drawable.btn_pause);
                    isPlaying = true;
                    Toast.makeText(PlayerActivity.this, R.string.start_play_song, Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set on click listener for Previous button
        imgPreviousButton = findViewById(R.id.previous_button);
        imgPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayerActivity.this, R.string.go_previous, Toast.LENGTH_SHORT).show();
            }
        });

        // Set on click listener for Next button
        imgNextButton = findViewById(R.id.next_button);
        imgNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayerActivity.this, R.string.go_next, Toast.LENGTH_SHORT).show();
            }
        });
    }

//      TODO: in new version of app will implement MediaPlayer to play the songs:
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }
    /**
     * When user starts moving the progress handler
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }
    /**
     * When user stops moving the progress hanlder
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
