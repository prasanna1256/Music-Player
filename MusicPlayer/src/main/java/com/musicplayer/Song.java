package com.musicplayer;

import java.io.Serializable;

/**
 * Represents a single song in the music player's playlist.
 */
public class Song implements Serializable {
    // Add serialVersionUID for version control
    private static final long serialVersionUID = 1L;

    private String title;
    private String filePath;
    private String artist;
    private String album;
    private int duration; // duration in seconds

    /**
     * Creates a new Song with the given title and file path.
     *
     * @param title The display title of the song
     * @param filePath The absolute path to the audio file
     */
    public Song(String title, String filePath) {
        this.title = title;
        this.filePath = filePath;
        this.artist = "Unknown";
        this.album = "Unknown";
        this.duration = 0;
    }

    /**
     * Creates a new Song with the given details.
     *
     * @param title The display title of the song
     * @param filePath The absolute path to the audio file
     * @param artist The artist name
     * @param album The album name
     * @param duration The duration in seconds
     */
    public Song(String title, String filePath, String artist, String album, int duration) {
        this.title = title;
        this.filePath = filePath;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
    }

    /**
     * @return The song title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The song title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The absolute file path
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath The file path to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return The artist name
     */
    public String getArtist() {
        return artist;
    }

    /**
     * @param artist The artist name to set
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * @return The album name
     */
    public String getAlbum() {
        return album;
    }

    /**
     * @param album The album name to set
     */
    public void setAlbum(String album) {
        this.album = album;
    }

    /**
     * @return The duration in seconds
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration The duration to set in seconds
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Returns a formatted string of the song duration in MM:SS format
     *
     * @return Formatted duration string
     */
    public String getFormattedDuration() {
        int minutes = duration / 60;
        int seconds = duration % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    public String toString() {
        return title + " - " + artist;
    }
}
