package com.musicplayer.examples;

import com.musicplayer.MP3Player;
import com.musicplayer.Song;
import com.musicplayer.PlaylistManager;

import java.io.File;
import java.util.List;
import java.util.Scanner;

/**
 * A simple console-based example showing how to use the Music Player components
 * programmatically without the GUI.
 */
public class SimplePlayerExample {

    private static MP3Player player;
    private static PlaylistManager playlistManager;
    private static boolean isRunning = true;

    public static void main(String[] args) {
        // Initialize player and playlist
        player = new MP3Player();
        playlistManager = new PlaylistManager();
        playlistManager.setPlaylistName("My Playlist");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Java Music Player - Console Example");
        System.out.println("----------------------------------");

        while (isRunning) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addSong(scanner);
                    break;
                case "2":
                    removeSong(scanner);
                    break;
                case "3":
                    listSongs();
                    break;
                case "4":
                    playSong(scanner);
                    break;
                case "5":
                    player.pause();
                    System.out.println("Playback paused.");
                    break;
                case "6":
                    player.stop();
                    System.out.println("Playback stopped.");
                    break;
                case "7":
                    createPlaylistFromDirectory(scanner);
                    break;
                case "8":
                    sortPlaylist(scanner);
                    break;
                case "9":
                    filterPlaylist(scanner);
                    break;
                case "10":
                    searchPlaylist(scanner);
                    break;
                case "11":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        // Clean up resources
        player.close();
        scanner.close();
        System.out.println("Thank you for using Java Music Player!");
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add song");
        System.out.println("2. Remove song");
        System.out.println("3. List songs");
        System.out.println("4. Play song");
        System.out.println("5. Pause playback");
        System.out.println("6. Stop playback");
        System.out.println("7. Create playlist from directory");
        System.out.println("8. Sort playlist");
        System.out.println("9. Filter playlist");
        System.out.println("10. Search playlist");
        System.out.println("11. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addSong(Scanner scanner) {
        System.out.print("Enter the path to the audio file: ");
        String filePath = scanner.nextLine().trim();

        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            System.out.println("The specified file does not exist or is not valid.");
            return;
        }

        System.out.print("Enter song title (press Enter to use filename): ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) {
            title = file.getName();
        }

        System.out.print("Enter artist name (press Enter for 'Unknown'): ");
        String artist = scanner.nextLine().trim();
        if (artist.isEmpty()) {
            artist = "Unknown";
        }

        System.out.print("Enter album name (press Enter for 'Unknown'): ");
        String album = scanner.nextLine().trim();
        if (album.isEmpty()) {
            album = "Unknown";
        }

        System.out.print("Enter duration in seconds (press Enter for 0): ");
        String durationStr = scanner.nextLine().trim();
        int duration = 0;
        if (!durationStr.isEmpty()) {
            try {
                duration = Integer.parseInt(durationStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid duration format. Using 0 seconds.");
            }
        }

        Song song = new Song(title, filePath, artist, album, duration);
        playlistManager.addSong(song);
        System.out.println("Song added to playlist.");
    }

    private static void removeSong(Scanner scanner) {
        if (playlistManager.size() == 0) {
            System.out.println("The playlist is empty.");
            return;
        }

        System.out.println("Current playlist:");
        listSongs();

        System.out.print("Enter the index of the song to remove: ");
        try {
            int index = Integer.parseInt(scanner.nextLine().trim());
            Song removedSong = playlistManager.removeSong(index);

            if (removedSong != null) {
                System.out.println("Removed: " + removedSong.getTitle());
            } else {
                System.out.println("Invalid song index.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private static void listSongs() {
        if (playlistManager.size() == 0) {
            System.out.println("The playlist is empty.");
            return;
        }

        System.out.println("\nPlaylist: " + playlistManager.getPlaylistName());
        System.out.println("----------------------------------------");

        for (int i = 0; i < playlistManager.size(); i++) {
            Song song = playlistManager.getSong(i);
            System.out.printf("%d. %s - %s (%s) [%s]%n",
                    i, song.getTitle(), song.getArtist(), song.getAlbum(), song.getFormattedDuration());
        }
    }

    private static void playSong(Scanner scanner) {
        if (playlistManager.size() == 0) {
            System.out.println("The playlist is empty. Add some songs first.");
            return;
        }

        System.out.println("Current playlist:");
        listSongs();

        System.out.print("Enter the index of the song to play: ");
        try {
            int index = Integer.parseInt(scanner.nextLine().trim());
            if (index >= 0 && index < playlistManager.size()) {
                Song songToPlay = playlistManager.getSong(index);
                player.stop();

                try {
                    player.load(new File(songToPlay.getFilePath()));
                    player.play();
                    System.out.println("Now playing: " + songToPlay.getTitle() + " - " + songToPlay.getArtist());
                } catch (Exception e) {
                    System.out.println("Error playing the file: " + e.getMessage());
                }
            } else {
                System.out.println("Invalid song index.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private static void createPlaylistFromDirectory(Scanner scanner) {
        System.out.print("Enter the directory path: ");
        String dirPath = scanner.nextLine().trim();

        File directory = new File(dirPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("The specified path is not a valid directory.");
            return;
        }

        System.out.print("Include subdirectories? (y/n): ");
        String includeSubDirs = scanner.nextLine().trim().toLowerCase();
        boolean recursive = includeSubDirs.equals("y") || includeSubDirs.equals("yes");

        int initialSize = playlistManager.size();
        boolean success = playlistManager.createFromDirectory(directory, recursive);

        if (success) {
            int newSongs = playlistManager.size() - initialSize;
            System.out.println("Added " + newSongs + " songs to the playlist.");
        } else {
            System.out.println("No audio files were found in the specified directory.");
        }
    }

    private static void sortPlaylist(Scanner scanner) {
        if (playlistManager.size() < 2) {
            System.out.println("The playlist is empty or has only one song. No need to sort.");
            return;
        }

        System.out.println("Sort by:");
        System.out.println("1. Title");
        System.out.println("2. Artist");
        System.out.println("3. Album");
        System.out.println("4. Duration");
        System.out.print("Enter your choice: ");

        try {
            int sortChoice = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Sort in ascending order? (y/n): ");
            String ascendingOrder = scanner.nextLine().trim().toLowerCase();
            boolean ascending = ascendingOrder.equals("y") || ascendingOrder.equals("yes");

            switch (sortChoice) {
                case 1: // Title
                    if (ascending) {
                        playlistManager.sortByTitle();
                    } else {
                        playlistManager.sortByTitleDesc();
                    }
                    break;
                case 2: // Artist
                    if (ascending) {
                        playlistManager.sortByArtist();
                    } else {
                        playlistManager.sortByArtistDesc();
                    }
                    break;
                case 3: // Album
                    if (ascending) {
                        playlistManager.sortByAlbum();
                    } else {
                        playlistManager.sortByAlbumDesc();
                    }
                    break;
                case 4: // Duration
                    if (ascending) {
                        playlistManager.sortByDuration();
                    } else {
                        playlistManager.sortByDurationDesc();
                    }
                    break;
                default:
                    System.out.println("Invalid choice. No sorting performed.");
                    return;
            }

            System.out.println("Playlist sorted.");
            listSongs();

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private static void filterPlaylist(Scanner scanner) {
        if (playlistManager.size() == 0) {
            System.out.println("The playlist is empty.");
            return;
        }

        System.out.println("Filter by:");
        System.out.println("1. Title");
        System.out.println("2. Artist");
        System.out.println("3. Album");
        System.out.println("4. Duration range");
        System.out.print("Enter your choice: ");

        try {
            int filterChoice = Integer.parseInt(scanner.nextLine().trim());
            List<Song> filteredSongs = null;

            switch (filterChoice) {
                case 1: // Title
                    System.out.print("Enter title to filter by: ");
                    String titleFilter = scanner.nextLine().trim();
                    filteredSongs = playlistManager.filterByTitle(titleFilter);
                    break;
                case 2: // Artist
                    System.out.print("Enter artist to filter by: ");
                    String artistFilter = scanner.nextLine().trim();
                    filteredSongs = playlistManager.filterByArtist(artistFilter);
                    break;
                case 3: // Album
                    System.out.print("Enter album to filter by: ");
                    String albumFilter = scanner.nextLine().trim();
                    filteredSongs = playlistManager.filterByAlbum(albumFilter);
                    break;
                case 4: // Duration range
                    System.out.print("Enter minimum duration in seconds: ");
                    String minDurStr = scanner.nextLine().trim();
                    System.out.print("Enter maximum duration in seconds: ");
                    String maxDurStr = scanner.nextLine().trim();

                    try {
                        int minDuration = Integer.parseInt(minDurStr);
                        int maxDuration = Integer.parseInt(maxDurStr);
                        filteredSongs = playlistManager.filterByDuration(minDuration, maxDuration);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid duration format.");
                        return;
                    }
                    break;
                default:
                    System.out.println("Invalid choice. No filtering performed.");
                    return;
            }

            if (filteredSongs != null) {
                if (filteredSongs.isEmpty()) {
                    System.out.println("No songs match the filter criteria.");
                } else {
                    System.out.println("\nFiltered Playlist:");
                    System.out.println("----------------------------------------");
                    for (int i = 0; i < filteredSongs.size(); i++) {
                        Song song = filteredSongs.get(i);
                        System.out.printf("%d. %s - %s (%s) [%s]%n",
                                i, song.getTitle(), song.getArtist(), song.getAlbum(), song.getFormattedDuration());
                    }
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private static void searchPlaylist(Scanner scanner) {
        if (playlistManager.size() == 0) {
            System.out.println("The playlist is empty.");
            return;
        }

        System.out.print("Enter search term: ");
        String searchTerm = scanner.nextLine().trim();

        if (searchTerm.isEmpty()) {
            System.out.println("Search term cannot be empty.");
            return;
        }

        List<Song> searchResults = playlistManager.search(searchTerm);

        if (searchResults.isEmpty()) {
            System.out.println("No songs match the search term '" + searchTerm + "'.");
        } else {
            System.out.println("\nSearch Results for '" + searchTerm + "':");
            System.out.println("----------------------------------------");
            for (int i = 0; i < searchResults.size(); i++) {
                Song song = searchResults.get(i);
                System.out.printf("%d. %s - %s (%s) [%s]%n",
                        i, song.getTitle(), song.getArtist(), song.getAlbum(), song.getFormattedDuration());
            }
        }
    }
}
