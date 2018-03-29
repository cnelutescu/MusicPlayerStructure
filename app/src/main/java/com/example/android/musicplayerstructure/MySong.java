package com.example.android.musicplayerstructure;

/**
 * Created by Cristi on 21.03.2018.
 */

/**
 * {@link MySong} represents info for a song
 */
public class MySong {

    private String mAlbumName;
    private String mTitle;
    private String mArtist;
    private String mGenre;
    private String mYear;
    private String mDuration;
    private String mBitRate;
    private String mAbsolutePath;
    private String mTrackNumber;
    private String mAlbumArtist;
    private String mType;
    // Drawable resource ID
    private int mImageResourceId;

    /**
     * Create a new MySong object.
     *
     * @param title is song title
     * @param artist is song artist
     */
    // public MySong(String title, String artist, int imageResourceId) {
    public MySong(String albumName, String title, String artist, String genre, String year, String duration,
                  String bitRate, String absolutePath, String trackNumber, String albumArtist, String type, int imageResourceId) {
        mAlbumName = albumName;             //
        mTitle = title;                     //
        mArtist = artist;
        mGenre = genre;
        mYear = year;
        mDuration = duration;
        mBitRate = bitRate;
        mAbsolutePath = absolutePath;
        mTrackNumber = trackNumber;
        mAlbumArtist = albumArtist;         //
        mType = type;
        mImageResourceId = imageResourceId;
    }


//----------------
    /**
     * Get the Album Name
     */
    public String getAlbumName() {
        return mAlbumName;
    }
    /**
     * Get the song Title
     */
    public String getTitle() {
        return mTitle;
    }
    /**
     * Get the song Artist
     */
    public String getArtist() {
        return mArtist;
    }
    /**
     * Get the song Genre
     */
    public String getGenre() {
        return mGenre;
    }
    /**
     * Get the song Year
     */
    public String getYear() {
        return mYear;
    }
    /**
     * Get the song Duration
     */
    public String getDuration() {
        return mDuration;
    }
    /**
     * Get the song  File Name
     */
    public String getBitRate() {
        return mBitRate;
    }
    /**
     * Get the song Absolute Path
     */
    public String getAbsolutePath() {
        return mAbsolutePath;
    }
    /**
     * Get the song Track Number
     */
    public String getTrackNumber() {
        return mTrackNumber;
    }
    /**
     * Get the album artist
     */
    public String getAlbumArtist() {
        return mAlbumArtist;
    }
    /**
     * Get the song Type
     */
    public String getType() {
        return mType;
    }
    /**
     * Get the image resource ID
     */
    public int getImageResourceId() {
        return mImageResourceId;
    }


}