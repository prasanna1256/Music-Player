package com.musicplayer;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages playlists for the music player application.
 * Provides functionality for creating, loading, saving, and manipulating playlists.
 */
public class PlaylistManager {
    private ArrayList<Song> currentPlaylist;
    private String playlistName;
    private String playlistFilePath;

    /**
     * Creates a new empty playlist manager.
     */
    public PlaylistManager() {
        this.currentPlaylist = new ArrayList<>();
        this.playlistName = "New Playlist";
        this.playlistFilePath = null;
    }

    /**
     * Creates a playlist manager with an existing list of songs.
     *
     * @param songs List of songs to initialize the playlist with
     * @param name Name of the playlist
     */
    public PlaylistManager(List<Song> songs, String name) {
        this.currentPlaylist = new ArrayList<>(songs);
        this.playlistName = name;
        this.playlistFilePath = null;
    }

    /**
     * @return The current playlist
     */
    public ArrayList<Song> getPlaylist() {
        return currentPlaylist;
    }

    /**
     * @return The name of the current playlist
     */
    public String getPlaylistName() {
        return playlistName;
    }

    /**
     * @param name The new name for the playlist
     */
    public void setPlaylistName(String name) {
        this.playlistName = name;
    }

    /**
     * Adds a song to the playlist.
     *
     * @param song The song to add
     * @return true if the song was added successfully
     */
    public boolean addSong(Song song) {
        return currentPlaylist.add(song);
    }

    /**
     * Removes a song from the playlist.
     *
     * @param index The index of the song to remove
     * @return The removed song, or null if the index is invalid
     */
    public Song removeSong(int index) {
        if (index >= 0 && index < currentPlaylist.size()) {
            return currentPlaylist.remove(index);
        }
        return null;
    }

    /**
     * Removes a specific song from the playlist.
     *
     * @param song The song to remove
     * @return true if the song was found and removed
     */
    public boolean removeSong(Song song) {
        return currentPlaylist.remove(song);
    }

    /**
     * @return The number of songs in the playlist
     */
    public int size() {
        return currentPlaylist.size();
    }

    /**
     * Clears all songs from the playlist.
     */
    public void clear() {
        currentPlaylist.clear();
    }

    /**
     * Gets a song at a specific index.
     *
     * @param index The index of the song to retrieve
     * @return The song at the specified index, or null if the index is invalid
     */
    public Song getSong(int index) {
        if (index >= 0 && index < currentPlaylist.size()) {
            return currentPlaylist.get(index);
        }
        return null;
    }

    /**
     * Shuffles the playlist randomly.
     */
    public void shuffle() {
        Collections.shuffle(currentPlaylist);
    }

    /**
     * Moves a song from one position to another in the playlist.
     *
     * @param fromIndex The current index of the song
     * @param toIndex The new index for the song
     * @return true if the move was successful
     */
    public boolean moveSong(int fromIndex, int toIndex) {
        if (fromIndex < 0 || fromIndex >= currentPlaylist.size() ||
            toIndex < 0 || toIndex >= currentPlaylist.size()) {
            return false;
        }

        Song song = currentPlaylist.remove(fromIndex);
        currentPlaylist.add(toIndex, song);
        return true;
    }

    /**
     * Sorts the playlist by the song title in ascending order.
     */
    public void sortByTitle() {
        Collections.sort(currentPlaylist, Comparator.comparing(Song::getTitle));
    }

    /**
     * Sorts the playlist by the song title in descending order.
     */
    public void sortByTitleDesc() {
        Collections.sort(currentPlaylist, Comparator.comparing(Song::getTitle).reversed());
    }

    /**
     * Sorts the playlist by the artist name in ascending order.
     */
    public void sortByArtist() {
        Collections.sort(currentPlaylist, Comparator.comparing(Song::getArtist));
    }

    /**
     * Sorts the playlist by the artist name in descending order.
     */
    public void sortByArtistDesc() {
        Collections.sort(currentPlaylist, Comparator.comparing(Song::getArtist).reversed());
    }

    /**
     * Sorts the playlist by the album name in ascending order.
     */
    public void sortByAlbum() {
        Collections.sort(currentPlaylist, Comparator.comparing(Song::getAlbum));
    }

    /**
     * Sorts the playlist by the album name in descending order.
     */
    public void sortByAlbumDesc() {
        Collections.sort(currentPlaylist, Comparator.comparing(Song::getAlbum).reversed());
    }

    /**
     * Sorts the playlist by the song duration in ascending order.
     */
    public void sortByDuration() {
        Collections.sort(currentPlaylist, Comparator.comparingInt(Song::getDuration));
    }

    /**
     * Sorts the playlist by the song duration in descending order.
     */
    public void sortByDurationDesc() {
        Collections.sort(currentPlaylist, Comparator.comparingInt(Song::getDuration).reversed());
    }

    /**
     * Provides a way to sort the playlist with a custom comparator.
     *
     * @param comparator The comparator to use for sorting
     */
    public void sort(Comparator<Song> comparator) {
        Collections.sort(currentPlaylist, comparator);
    }

    /**
     * Filters the playlist by a substring in the song title.
     *
     * @param titleSubstring The substring to search for in the title
     * @return A new list of songs that match the filter
     */
    public List<Song> filterByTitle(String titleSubstring) {
        String searchTerm = titleSubstring.toLowerCase();
        return currentPlaylist.stream()
                .filter(song -> song.getTitle().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    /**
     * Filters the playlist by an artist name.
     *
     * @param artistName The artist name to filter by
     * @return A new list of songs that match the filter
     */
    public List<Song> filterByArtist(String artistName) {
        String searchTerm = artistName.toLowerCase();
        return currentPlaylist.stream()
                .filter(song -> song.getArtist().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    /**
     * Filters the playlist by an album name.
     *
     * @param albumName The album name to filter by
     * @return A new list of songs that match the filter
     */
    public List<Song> filterByAlbum(String albumName) {
        String searchTerm = albumName.toLowerCase();
        return currentPlaylist.stream()
                .filter(song -> song.getAlbum().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    /**
     * Filters the playlist by a duration range.
     *
     * @param minDuration The minimum duration in seconds
     * @param maxDuration The maximum duration in seconds
     * @return A new list of songs that match the filter
     */
    public List<Song> filterByDuration(int minDuration, int maxDuration) {
        return currentPlaylist.stream()
                .filter(song -> song.getDuration() >= minDuration && song.getDuration() <= maxDuration)
                .collect(Collectors.toList());
    }

    /**
     * Generic filter method that accepts a predicate function.
     *
     * @param predicate A function that tests each song
     * @return A new PlaylistManager containing only the songs that pass the filter
     */
    public PlaylistManager filter(java.util.function.Predicate<Song> predicate) {
        List<Song> filteredSongs = currentPlaylist.stream()
                .filter(predicate)
                .collect(Collectors.toList());
        return new PlaylistManager(filteredSongs, playlistName + " (Filtered)");
    }

    /**
     * Applies a filter to the current playlist and returns a new filtered list.
     *
     * @param searchTerm The term to search for across all song fields
     * @return A new list of songs that match the search term in any field
     */
    public List<Song> search(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return new ArrayList<>(currentPlaylist);
        }

        String term = searchTerm.toLowerCase().trim();
        return currentPlaylist.stream()
                .filter(song ->
                    song.getTitle().toLowerCase().contains(term) ||
                    song.getArtist().toLowerCase().contains(term) ||
                    song.getAlbum().toLowerCase().contains(term))
                .collect(Collectors.toList());
    }

    /**
     * Saves the current playlist to a file.
     *
     * @param filePath The path to save the playlist to
     * @return true if the save was successful
     */
    public boolean savePlaylist(String filePath) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(playlistName);
            out.writeObject(currentPlaylist);
            this.playlistFilePath = filePath;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Loads a playlist from a file.
     *
     * @param filePath The path to load the playlist from
     * @return true if the load was successful
     */
    @SuppressWarnings("unchecked")
    public boolean loadPlaylist(String filePath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            this.playlistName = (String) in.readObject();
            this.currentPlaylist = (ArrayList<Song>) in.readObject();
            this.playlistFilePath = filePath;
            return true;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Saves the playlist to its previous file location.
     *
     * @return true if the save was successful, false if no file path is set or save failed
     */
    public boolean savePlaylist() {
        if (playlistFilePath == null) {
            return false;
        }
        return savePlaylist(playlistFilePath);
    }

    /**
     * Creates a new playlist from a directory of audio files.
     *
     * @param directory The directory to scan for audio files
     * @param recursive Whether to scan subdirectories
     * @return true if at least one song was added
     */
    public boolean createFromDirectory(File directory, boolean recursive) {
        if (!directory.isDirectory()) {
            return false;
        }

        int initialSize = currentPlaylist.size();
        scanDirectory(directory, recursive);

        return currentPlaylist.size() > initialSize;
    }

    /**
     * Recursively scans a directory for audio files and adds them to the playlist.
     *
     * @param directory The directory to scan
     * @param recursive Whether to scan subdirectories
     */
    private void scanDirectory(File directory, boolean recursive) {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory() && recursive) {
                scanDirectory(file, true);
            } else if (isAudioFile(file)) {
                Song song = new Song(file.getName(), file.getAbsolutePath());
                addSong(song);
            }
        }
    }

    /**
     * Checks if a file is an audio file based on its extension.
     *
     * @param file The file to check
     * @return true if the file appears to be an audio file
     */
    private boolean isAudioFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".wav") || name.endsWith(".mp3") ||
               name.endsWith(".aiff") || name.endsWith(".aif") ||
               name.endsWith(".flac") || name.endsWith(".ogg");
    }
}
