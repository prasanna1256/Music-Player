# Java Music Player

A Java-based music player application that provides playlist management and audio playback functionality.

## Features

- Play, pause, and stop audio files
- Create and manage playlists using ArrayList
- Navigate between tracks (next/previous)
- Volume control
- Progress bar showing playback position
- Support for various audio formats (WAV, MP3, AIFF, etc.)
- Save and load playlists
- Import music from directories

## Architecture

The application is built with a modular design:

- **MusicPlayer**: Main application class with UI and core playback functionality
- **PlaylistManager**: Handles playlist operations using ArrayList data structure
- **Song**: Represents a single song with metadata
- **MP3Player**: Specialized class for handling MP3 playback

## Technical Details

- Built using Java Swing for the user interface
- Uses the Java Sound API (javax.sound) for audio playback
- Employs ArrayList data structure for dynamic playlist management
- Includes external libraries for MP3 support and audio metadata extraction

## Building the Project

This project uses Gradle for dependency management and building.

```bash
# Clone the repository
git clone https://github.com/prasanna1256/java-music-player.git
cd java-music-player

# Build with Gradle
./gradlew build

# Run the application
./gradlew run
```
## System Requirements

- Java 8 or higher
- Audio codecs for MP3 playback (if not included with your Java installation)

## License

This project is licensed under the MIT License - see the LICENSE file for details.
