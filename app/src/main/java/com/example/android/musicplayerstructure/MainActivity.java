package com.example.android.musicplayerstructure;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * {@link MainActivity} is the activity that ask the user to give the folder and launch SongsActivity
 * to display the list of songs found.
 * If app has no permissions to read/write external storage a toast with info is displayed
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set on click listener for Search button
        Button searchButton = findViewById(R.id.btn_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchSongs();
            }
        });
    }

    /**
     * Function to verify read/write permissions, input of music folder name and start SongsActivity
     */
    public void searchSongs() {
        // Check if app has read/write permissions to external storage
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck == 0) {
            EditText folderNameEditText = findViewById(R.id.folder_name);
            String folderName = folderNameEditText.getText().toString().trim();
            // Check if user enter the music folder name
            if (folderName.equals("")) {
                Toast.makeText(this, R.string.msg_missing_folder_name, Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(this, SongsActivity.class);
                Bundle extras = new Bundle();
                extras.putString("EXTRA_FOLDER_NAME", folderName);
                intent.putExtras(extras);
                startActivity(intent);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, R.string.activity_not_resolved, Toast.LENGTH_LONG).show();
                }
            }
        } else {
            // if app has not read/write permissions to external storage display toast info
            Toast.makeText(this, R.string.message_storage_permissions, Toast.LENGTH_LONG).show();
        }
    }

}
