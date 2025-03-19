package com.musicplayer;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayer extends JFrame {
    private ArrayList<Song> playlist;
    private int currentSongIndex;
    private Clip audioClip;
    private boolean isPlaying;

    private JList<String> playlistView;
    private DefaultListModel<String> playlistModel;
    private JButton playButton, pauseButton, stopButton, nextButton, prevButton, addButton, removeButton;
    private JLabel currentSongLabel;
    private JProgressBar songProgressBar;
    private JTextField searchField;
    private JComboBox<String> sortByComboBox;
    private JComboBox<String> filterByComboBox;
    private JCheckBox ascendingCheckBox;

    // PlaylistManager instance to handle playlist operations
    private PlaylistManager playlistManager;

    public MusicPlayer() {
        super("Java Music Player");

        // Initialize playlist
        playlistManager = new PlaylistManager();
        playlist = playlistManager.getPlaylist();
        currentSongIndex = -1;
        isPlaying = false;

        // Set up the GUI
        setupUI();

        // Set up event listeners
        setupEventListeners();

        // Add some test songs for demonstration
        addTestSongs();

        // Configure window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Adds test songs to the playlist for demonstration purposes
     */
    private void addTestSongs() {
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

        // Update the playlist view to show the test songs
        updatePlaylistView();

        // Output test message
        System.out.println("Added 10 test songs to playlist");
        System.out.println("You can now test sorting and filtering functionality");
    }

    private void setupUI() {
        // Main layout
        setLayout(new BorderLayout());

        // Playlist panel
        JPanel playlistPanel = new JPanel(new BorderLayout());
        playlistModel = new DefaultListModel<>();
        playlistView = new JList<>(playlistModel);
        JScrollPane scrollPane = new JScrollPane(playlistView);

        // Playlist header panel with search and sort options
        JPanel playlistHeaderPanel = new JPanel(new BorderLayout());
        playlistHeaderPanel.setBorder(BorderFactory.createTitledBorder("Playlist"));

        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        searchField.setToolTipText("Search in playlist");
        JButton searchButton = new JButton("Search");
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        playlistHeaderPanel.add(searchPanel, BorderLayout.NORTH);

        // Sort and filter panel
        JPanel sortFilterPanel = new JPanel(new GridLayout(2, 1));

        // Sort options panel
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sortPanel.add(new JLabel("Sort by:"));
        String[] sortOptions = {"Title", "Artist", "Album", "Duration"};
        sortByComboBox = new JComboBox<>(sortOptions);
        sortPanel.add(sortByComboBox);
        ascendingCheckBox = new JCheckBox("Ascending", true);
        sortPanel.add(ascendingCheckBox);
        JButton sortButton = new JButton("Sort");
        sortPanel.add(sortButton);
        sortFilterPanel.add(sortPanel);

        // Filter options panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Filter by:"));
        String[] filterOptions = {"All", "Title", "Artist", "Album"};
        filterByComboBox = new JComboBox<>(filterOptions);
        filterPanel.add(filterByComboBox);
        JButton clearFilterButton = new JButton("Clear Filter");
        filterPanel.add(clearFilterButton);
        sortFilterPanel.add(filterPanel);

        playlistHeaderPanel.add(sortFilterPanel, BorderLayout.CENTER);

        playlistPanel.add(playlistHeaderPanel, BorderLayout.NORTH);
        playlistPanel.add(scrollPane, BorderLayout.CENTER);

        // Playlist control panel
        JPanel playlistControlPanel = new JPanel();
        addButton = new JButton("Add Song");
        removeButton = new JButton("Remove Song");
        playlistControlPanel.add(addButton);
        playlistControlPanel.add(removeButton);
        playlistPanel.add(playlistControlPanel, BorderLayout.SOUTH);

        // Player panel
        JPanel playerPanel = new JPanel(new BorderLayout());

        // Current song info
        currentSongLabel = new JLabel("No song playing");
        playerPanel.add(currentSongLabel, BorderLayout.NORTH);

        // Progress bar
        songProgressBar = new JProgressBar(0, 100);
        songProgressBar.setValue(0);
        songProgressBar.setStringPainted(true);
        playerPanel.add(songProgressBar, BorderLayout.CENTER);

        // Playback controls
        JPanel controlPanel = new JPanel();
        prevButton = new JButton("Previous");
        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        stopButton = new JButton("Stop");
        nextButton = new JButton("Next");

        controlPanel.add(prevButton);
        controlPanel.add(playButton);
        controlPanel.add(pauseButton);
        controlPanel.add(stopButton);
        controlPanel.add(nextButton);
        playerPanel.add(controlPanel, BorderLayout.SOUTH);

        // Add components to main layout
        add(playlistPanel, BorderLayout.WEST);
        add(playerPanel, BorderLayout.CENTER);

        // Set up the sort button action
        sortButton.addActionListener(e -> sortPlaylist());

        // Set up the search button action
        searchButton.addActionListener(e -> searchPlaylist());

        // Clear filter button action
        clearFilterButton.addActionListener(e -> clearFilter());

        // Enter key in search field triggers search
        searchField.addActionListener(e -> searchPlaylist());
    }

    private void setupEventListeners() {
        addButton.addActionListener(e -> addSong());
        removeButton.addActionListener(e -> removeSong());
        playButton.addActionListener(e -> playSong());
        pauseButton.addActionListener(e -> pauseSong());
        stopButton.addActionListener(e -> stopSong());
        nextButton.addActionListener(e -> nextSong());
        prevButton.addActionListener(e -> previousSong());

        playlistView.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = playlistView.getSelectedIndex();
                    if (index >= 0) {
                        currentSongIndex = index;
                        stopSong();
                        playSong();
                    }
                }
            }
        });

        // Add popup menu for playlist
        JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem playSongItem = new JMenuItem("Play");
        JMenuItem removeSongItem = new JMenuItem("Remove");
        JMenuItem moveUpItem = new JMenuItem("Move Up");
        JMenuItem moveDownItem = new JMenuItem("Move Down");
        JMenuItem propertiesItem = new JMenuItem("Properties");

        playSongItem.addActionListener(e -> {
            int index = playlistView.getSelectedIndex();
            if (index >= 0) {
                currentSongIndex = index;
                stopSong();
                playSong();
            }
        });

        removeSongItem.addActionListener(e -> removeSong());

        moveUpItem.addActionListener(e -> {
            int index = playlistView.getSelectedIndex();
            if (index > 0) {
                playlistManager.moveSong(index, index - 1);
                updatePlaylistView();
                playlistView.setSelectedIndex(index - 1);
            }
        });

        moveDownItem.addActionListener(e -> {
            int index = playlistView.getSelectedIndex();
            if (index >= 0 && index < playlistManager.size() - 1) {
                playlistManager.moveSong(index, index + 1);
                updatePlaylistView();
                playlistView.setSelectedIndex(index + 1);
            }
        });

        propertiesItem.addActionListener(e -> {
            int index = playlistView.getSelectedIndex();
            if (index >= 0) {
                Song song = playlistManager.getSong(index);
                JOptionPane.showMessageDialog(this,
                    "Title: " + song.getTitle() +
                    "\nArtist: " + song.getArtist() +
                    "\nAlbum: " + song.getAlbum() +
                    "\nDuration: " + song.getFormattedDuration() +
                    "\nFile: " + song.getFilePath(),
                    "Song Properties",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });

        contextMenu.add(playSongItem);
        contextMenu.add(removeSongItem);
        contextMenu.addSeparator();
        contextMenu.add(moveUpItem);
        contextMenu.add(moveDownItem);
        contextMenu.addSeparator();
        contextMenu.add(propertiesItem);

        playlistView.setComponentPopupMenu(contextMenu);
    }

    private void updatePlaylistView() {
        playlistModel.clear();
        for (Song song : playlist) {
            playlistModel.addElement(song.getTitle() + " - " + song.getArtist());
        }
    }

    private void sortPlaylist() {
        String sortBy = (String) sortByComboBox.getSelectedItem();
        boolean ascending = ascendingCheckBox.isSelected();

        if (sortBy == null) return;

        System.out.println("Sorting playlist by: " + sortBy + " in " + (ascending ? "ascending" : "descending") + " order");

        switch (sortBy) {
            case "Title":
                if (ascending) {
                    playlistManager.sortByTitle();
                } else {
                    playlistManager.sortByTitleDesc();
                }
                break;
            case "Artist":
                if (ascending) {
                    playlistManager.sortByArtist();
                } else {
                    playlistManager.sortByArtistDesc();
                }
                break;
            case "Album":
                if (ascending) {
                    playlistManager.sortByAlbum();
                } else {
                    playlistManager.sortByAlbumDesc();
                }
                break;
            case "Duration":
                if (ascending) {
                    playlistManager.sortByDuration();
                } else {
                    playlistManager.sortByDurationDesc();
                }
                break;
        }

        updatePlaylistView();
    }

    private void searchPlaylist() {
        String searchTerm = searchField.getText().trim();
        String filterBy = (String) filterByComboBox.getSelectedItem();

        if (searchTerm.isEmpty()) {
            clearFilter();
            return;
        }

        System.out.println("Searching for: '" + searchTerm + "' in field: " + filterBy);

        List<Song> filteredList;

        if (filterBy == null || filterBy.equals("All")) {
            filteredList = playlistManager.search(searchTerm);
        } else {
            switch (filterBy) {
                case "Title":
                    filteredList = playlistManager.filterByTitle(searchTerm);
                    break;
                case "Artist":
                    filteredList = playlistManager.filterByArtist(searchTerm);
                    break;
                case "Album":
                    filteredList = playlistManager.filterByAlbum(searchTerm);
                    break;
                default:
                    filteredList = playlistManager.search(searchTerm);
            }
        }

        // Update view with filtered results
        playlistModel.clear();
        for (Song song : filteredList) {
            playlistModel.addElement(song.getTitle() + " - " + song.getArtist());
        }

        System.out.println("Found " + filteredList.size() + " matching songs");
    }

    private void clearFilter() {
        searchField.setText("");
        updatePlaylistView();
        System.out.println("Filter cleared");
    }

    private void addSong() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                if (f.isDirectory()) return true;
                String name = f.getName().toLowerCase();
                return name.endsWith(".wav") || name.endsWith(".mp3") || name.endsWith(".aiff");
            }

            public String getDescription() {
                return "Audio Files (*.wav, *.mp3, *.aiff)";
            }
        });

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            Song song = new Song(file.getName(), file.getAbsolutePath());
            playlistManager.addSong(song);
            playlistModel.addElement(song.getTitle());

            // If this is the first song, set it as current
            if (playlistManager.size() == 1) {
                currentSongIndex = 0;
            }
        }
    }

    private void removeSong() {
        int index = playlistView.getSelectedIndex();
        if (index < 0) return;

        if (index == currentSongIndex) {
            stopSong();
            currentSongIndex = -1;
        } else if (index < currentSongIndex) {
            currentSongIndex--;
        }

        playlistManager.removeSong(index);
        playlistModel.remove(index);
    }

    private void playSong() {
        if (playlistManager.size() == 0 || currentSongIndex < 0 || currentSongIndex >= playlistManager.size()) {
            JOptionPane.showMessageDialog(this, "No song selected to play.");
            return;
        }

        if (isPlaying && audioClip != null) {
            audioClip.start();
            return;
        }

        try {
            Song song = playlistManager.getSong(currentSongIndex);
            currentSongLabel.setText("Now playing: " + song.getTitle() + " - " + song.getArtist());

            // For demonstration purposes, we'll simulate playback without actually playing audio
            System.out.println("Playing: " + song.getTitle() + " by " + song.getArtist() + " [Simulated]");
            isPlaying = true;

            // Set up progress bar update for demonstration
            final int duration = song.getDuration();
            if (duration > 0) {
                new Thread(() -> {
                    for (int progress = 0; progress <= 100 && isPlaying; progress++) {
                        final int currentProgress = progress;
                        SwingUtilities.invokeLater(() -> songProgressBar.setValue(currentProgress));
                        try {
                            Thread.sleep(duration * 10); // Scale the actual duration for demo purposes
                        } catch (InterruptedException e) {
                            break;
                        }
                    }

                    if (isPlaying) {
                        // Song ended naturally, go to next song
                        SwingUtilities.invokeLater(() -> nextSong());
                    }
                }).start();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error playing file: " + e.getMessage(),
                                         "Playback Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void pauseSong() {
        if (isPlaying) {
            System.out.println("Paused playback [Simulated]");
            isPlaying = false;
        }
    }

    private void stopSong() {
        isPlaying = false;
        songProgressBar.setValue(0);
        currentSongLabel.setText("No song playing");
        System.out.println("Stopped playback [Simulated]");
    }

    private void nextSong() {
        if (playlistManager.size() == 0) return;

        stopSong();

        if (currentSongIndex < playlistManager.size() - 1) {
            currentSongIndex++;
        } else {
            // Loop back to the beginning
            currentSongIndex = 0;
        }

        playSong();
    }

    private void previousSong() {
        if (playlistManager.size() == 0) return;

        stopSong();

        if (currentSongIndex > 0) {
            currentSongIndex--;
        } else {
            // Loop to the end
            currentSongIndex = playlistManager.size() - 1;
        }

        playSong();
    }

    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            MusicPlayer player = new MusicPlayer();
            System.out.println("Music Player initialized with test data");
            System.out.println("Try sorting by different criteria and searching for songs");
        });
    }
}
