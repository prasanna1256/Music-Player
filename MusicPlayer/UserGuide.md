# Java Music Player - User Guide

## Getting Started

### Installation

1. Make sure you have Java 8 or higher installed on your system
2. Download the Music Player application package
3. Extract the files to your preferred location

### Running the Application

#### On Windows
Double-click on the `run.bat` file or open a command prompt in the application directory and type:
```
run.bat
```

#### On Linux/macOS
Open a terminal in the application directory and type:
```
./run.sh
```

Or if you prefer to run it directly with Gradle:
```
gradle run
```

## Using the Music Player

### Main Interface

The Music Player interface consists of:
- A playlist panel on the left side with search, sort, and filter options
- Playback controls in the center
- Song information and progress bar above the controls

### Adding Songs to the Playlist

1. Click the "Add Song" button
2. Browse to locate your audio files (supported formats: WAV, MP3, AIFF, FLAC, OGG)
3. Select the file and click "Open"
4. The song will appear in the playlist

### Importing a Directory of Songs

1. Click the "Add Song" button
2. Navigate to the directory containing your music
3. Select any file in the directory
4. All supported audio files in that directory will be added to the playlist

### Playing Music

- To play a song, select it in the playlist and click the "Play" button or double-click the song
- Use the "Pause" button to pause playback
- Use the "Stop" button to stop playback completely
- Use the "Previous" and "Next" buttons to navigate between songs in the playlist

### Managing the Playlist

- To remove a song, select it in the playlist and click the "Remove Song" button
- The playlist is automatically saved when you exit the application
- To create a new playlist, use the "Clear" option (accessible through right-click on the playlist)
- Right-click on a song to access additional options such as "Move Up", "Move Down", and "Properties"

### Sorting the Playlist

The music player provides multiple ways to sort your playlist:

1. Select a sorting criteria from the "Sort by" dropdown:
   - Title: Sort alphabetically by song title
   - Artist: Sort alphabetically by artist name
   - Album: Sort alphabetically by album name
   - Duration: Sort numerically by song duration

2. Check or uncheck the "Ascending" checkbox to toggle between ascending and descending order

3. Click the "Sort" button to apply the sort

The playlist will be reordered based on your selected criteria.

### Filtering and Searching

#### Basic Search
1. Enter text in the search field above the playlist
2. Click the "Search" button or press Enter
3. The playlist will show only songs that match your search term across title, artist, or album fields

#### Advanced Filtering
1. Select a filter category from the "Filter by" dropdown:
   - All: Search across all fields
   - Title: Search only in the title field
   - Artist: Search only by artist name
   - Album: Search only by album name

2. Enter your search term and press Enter or click "Search"

3. Click "Clear Filter" to return to the full playlist view

### Using the Context Menu

Right-click on any song in the playlist to access additional options:
- Play: Play the selected song
- Remove: Remove the song from the playlist
- Move Up/Down: Change the song's position in the playlist
- Properties: View detailed information about the song

### Keyboard Shortcuts

- Space: Play/Pause
- Ctrl+Right Arrow: Next song
- Ctrl+Left Arrow: Previous song
- Ctrl+A: Add song
- Delete: Remove selected song
- Ctrl+F: Focus on search field

## Console Version

For users who prefer a command-line interface, the Music Player also comes with a console version:

1. Open a terminal or command prompt
2. Navigate to the application directory
3. Run: `java -cp build/libs/MusicPlayer-all-1.0-SNAPSHOT.jar com.musicplayer.examples.SimplePlayerExample`
4. Follow the on-screen menu options

The console version includes all the same features as the GUI version, including playlist sorting and filtering.

## Troubleshooting

### Common Issues

**Problem**: The application doesn't start.
**Solution**: Make sure you have Java 8 or newer installed. You can check your Java version by running `java -version` in a terminal or command prompt.

**Problem**: MP3 files won't play.
**Solution**: The Java Sound API might not have built-in support for MP3. The application includes the necessary libraries, but if issues persist, you might need to install additional codec packs for your system.

**Problem**: The application runs slowly or crashes with large playlists.
**Solution**: The application uses memory to store the playlist. If you have a very large music collection, try working with smaller playlists or increasing the Java heap size:
```
java -Xmx512m -jar MusicPlayer-all-1.0-SNAPSHOT.jar
```

**Problem**: Search or sort operations are slow with large playlists.
**Solution**: Searching and sorting operations may take longer with very large playlists. Consider organizing your music into smaller, more manageable playlists.

## Advanced Features

### Creating Custom Playlists

You can programmatically create and manage playlists using the built-in PlaylistManager API:

```java
// Create a playlist from a directory
PlaylistManager playlistManager = new PlaylistManager();
playlistManager.setPlaylistName("My Playlist");
playlistManager.createFromDirectory(new File("/path/to/music"), true); // true for recursive search

// Sort the playlist by title
playlistManager.sortByTitle();

// Filter songs by artist
List<Song> beatlesSongs = playlistManager.filterByArtist("Beatles");
```

### Extending the Player

The Music Player is designed to be extensible. You can add new features by extending the base classes:

- Implement a new player for a specific format by extending the MP3Player class
- Create a custom playlist manager by extending the PlaylistManager class
- Design your own UI by using the core components and creating a new main class
- Add custom sorting and filtering algorithms by implementing your own comparators
