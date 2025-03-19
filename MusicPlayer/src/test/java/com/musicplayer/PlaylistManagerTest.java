package com.musicplayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the PlaylistManager functionality.
 */
public class PlaylistManagerTest {
    private PlaylistManager playlistManager;
    private Song testSong1;
    private Song testSong2;

    @BeforeEach
    public void setUp() {
        playlistManager = new PlaylistManager();
        playlistManager.setPlaylistName("Test Playlist");

        // Create test songs
        testSong1 = new Song("Test Song 1", "/path/to/nonexistent1.mp3", "Test Artist", "Test Album", 180);
        testSong2 = new Song("Test Song 2", "/path/to/nonexistent2.mp3", "Test Artist 2", "Test Album 2", 240);
    }

    @Test
    public void testAddSong() {
        // Add a song to the playlist
        boolean result = playlistManager.addSong(testSong1);

        // Verify song was added
        assertTrue(result);
        assertEquals(1, playlistManager.size());
        assertEquals(testSong1, playlistManager.getSong(0));
    }

    @Test
    public void testRemoveSongByIndex() {
        // Add songs to the playlist
        playlistManager.addSong(testSong1);
        playlistManager.addSong(testSong2);

        // Remove song by index
        Song removedSong = playlistManager.removeSong(0);

        // Verify song was removed
        assertEquals(testSong1, removedSong);
        assertEquals(1, playlistManager.size());
        assertEquals(testSong2, playlistManager.getSong(0));
    }

    @Test
    public void testRemoveSongByObject() {
        // Add songs to the playlist
        playlistManager.addSong(testSong1);
        playlistManager.addSong(testSong2);

        // Remove song by object
        boolean result = playlistManager.removeSong(testSong1);

        // Verify song was removed
        assertTrue(result);
        assertEquals(1, playlistManager.size());
        assertEquals(testSong2, playlistManager.getSong(0));
    }

    @Test
    public void testPlaylistManipulation() {
        // Test clear
        playlistManager.addSong(testSong1);
        playlistManager.addSong(testSong2);
        playlistManager.clear();
        assertEquals(0, playlistManager.size());

        // Test get song with invalid index
        assertNull(playlistManager.getSong(-1));
        assertNull(playlistManager.getSong(0));

        // Test add songs again and shuffle
        playlistManager.addSong(testSong1);
        playlistManager.addSong(testSong2);

        // Since shuffle is random, we can't assert the exact order,
        // but we can verify the songs are still there
        playlistManager.shuffle();
        assertEquals(2, playlistManager.size());

        // Test moving songs
        playlistManager.clear();
        playlistManager.addSong(testSong1);
        playlistManager.addSong(testSong2);

        assertTrue(playlistManager.moveSong(0, 1));
        assertEquals(testSong2, playlistManager.getSong(0));
        assertEquals(testSong1, playlistManager.getSong(1));
    }

    @Test
    public void testInvalidOperations() {
        // Test remove with invalid index
        assertNull(playlistManager.removeSong(-1));
        assertNull(playlistManager.removeSong(0));

        // Test move with invalid indices
        assertFalse(playlistManager.moveSong(-1, 0));
        assertFalse(playlistManager.moveSong(0, -1));
        assertFalse(playlistManager.moveSong(0, 0));
    }

    @Test
    public void testPlaylistCreationFromDirectory() {
        // This test requires a real directory with audio files
        // We'll just test the error case for non-existent directory
        File nonExistentDir = new File("/path/to/nonexistent/directory");
        assertFalse(playlistManager.createFromDirectory(nonExistentDir, true));

        // Test with a file instead of directory
        File tempFile = new File("temp.txt");
        try {
            tempFile.createNewFile();
            assertFalse(playlistManager.createFromDirectory(tempFile, true));
        } catch (Exception e) {
            fail("Exception should not have been thrown");
        } finally {
            tempFile.delete();
        }
    }
}
