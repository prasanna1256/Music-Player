package com.musicplayer.examples;

import com.musicplayer.Song;
import com.musicplayer.PlaylistManager;

/**
 * A test class that demonstrates the sorting and filtering capabilities
 * of the Music Player using the console.
 */
public class TestConsolePlayer {

    public static void main(String[] args) {
        System.out.println("Music Player Console Demo - Sorting and Filtering Test");
        System.out.println("====================================================");

        // Create a playlist manager and populate with test songs
        PlaylistManager playlistManager = new PlaylistManager();
        playlistManager.setPlaylistName("Test Playlist");

        // Add sample songs with different attributes to test sorting and filtering
        playlistManager.addSong(new Song("Bohemian Rhapsody", "/path/to/bohemian.mp3", "Queen", "A Night at the Opera", 355));
        playlistManager.addSong(new Song("Yesterday", "/path/to/yesterday.mp3", "The Beatles", "Help!", 125));
        playlistManager.addSong(new Song("Stairway to Heaven", "/path/to/stairway.mp3", "Led Zeppelin", "Led Zeppelin IV", 482));
        playlistManager.addSong(new Song("Imagine", "/path/to/imagine.mp3", "John Lennon", "Imagine", 183));
        playlistManager.addSong(new Song("Thriller", "/path/to/thriller.mp3", "Michael Jackson", "Thriller", 357));
        playlistManager.addSong(new Song("Hotel California", "/path/to/hotel.mp3", "Eagles", "Hotel California", 391));
        playlistManager.addSong(new Song("Let It Be", "/path/to/letitbe.mp3", "The Beatles", "Let It Be", 243));
        playlistManager.addSong(new Song("Sweet Child O' Mine", "/path/to/sweet.mp3", "Guns N' Roses", "Appetite for Destruction", 356));
        playlistManager.addSong(new Song("Billie Jean", "/path/to/billie.mp3", "Michael Jackson", "Thriller", 294));
        playlistManager.addSong(new Song("Like a Rolling Stone", "/path/to/rolling.mp3", "Bob Dylan", "Highway 61 Revisited", 373));

        System.out.println("Added 10 songs to the playlist.");

        // Print original playlist
        printPlaylist(playlistManager, "Original Playlist");

        // Test sorting by title
        System.out.println("\n--- Sorting Tests ---");

        playlistManager.sortByTitle();
        printPlaylist(playlistManager, "Playlist Sorted by Title (Ascending)");

        playlistManager.sortByTitleDesc();
        printPlaylist(playlistManager, "Playlist Sorted by Title (Descending)");

        playlistManager.sortByArtist();
        printPlaylist(playlistManager, "Playlist Sorted by Artist (Ascending)");

        playlistManager.sortByDurationDesc();
        printPlaylist(playlistManager, "Playlist Sorted by Duration (Descending)");

        // Test filtering
        System.out.println("\n--- Filtering Tests ---");

        // Filter by artist
        System.out.println("\nFiltering by Artist: \"The Beatles\"");
        var filteredByArtist = playlistManager.filterByArtist("Beatles");
        printSongList(filteredByArtist, "Songs by The Beatles");

        // Filter by album
        System.out.println("\nFiltering by Album: \"Thriller\"");
        var filteredByAlbum = playlistManager.filterByAlbum("Thriller");
        printSongList(filteredByAlbum, "Songs from Thriller Album");

        // Search across all fields
        System.out.println("\nSearching for: \"le\"");
        var searchResults = playlistManager.search("le");
        printSongList(searchResults, "Search Results for 'le'");

        // Filter by duration range
        System.out.println("\nFiltering by Duration: Between 180 and 300 seconds");
        var filteredByDuration = playlistManager.filterByDuration(180, 300);
        printSongList(filteredByDuration, "Songs Between 3:00 and 5:00 Minutes");

        System.out.println("\nDemo completed. All sorting and filtering features tested successfully.");
    }

    /**
     * Prints the contents of a PlaylistManager
     */
    private static void printPlaylist(PlaylistManager manager, String title) {
        System.out.println("\n" + title);
        System.out.println("-----------------------------------------------------");
        for (int i = 0; i < manager.size(); i++) {
            Song song = manager.getSong(i);
            System.out.printf("%2d. %-25s %-15s %-20s [%s]%n",
                    i + 1,
                    song.getTitle(),
                    song.getArtist(),
                    song.getAlbum(),
                    song.getFormattedDuration());
        }
    }

    /**
     * Prints a list of songs
     */
    private static void printSongList(java.util.List<Song> songs, String title) {
        System.out.println("\n" + title);
        System.out.println("-----------------------------------------------------");
        if (songs.isEmpty()) {
            System.out.println("No songs found.");
            return;
        }

        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            System.out.printf("%2d. %-25s %-15s %-20s [%s]%n",
                    i + 1,
                    song.getTitle(),
                    song.getArtist(),
                    song.getAlbum(),
                    song.getFormattedDuration());
        }
    }
}
