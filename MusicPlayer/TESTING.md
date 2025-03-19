# Testing the Java Music Player

This document provides instructions on how to test the Java Music Player, particularly the sorting and filtering functionality.

## Prerequisites

- Java 8 or higher installed
- Gradle (optional, for building with the included Gradle scripts)

## Building the Application

### Using Gradle
```bash
cd MusicPlayer
gradle build
```

### Using JavaC directly
```bash
cd MusicPlayer
mkdir -p build/classes
javac -d build/classes/java/main src/main/java/com/musicplayer/*.java src/main/java/com/musicplayer/examples/*.java
```

## Running the Tests

The project includes two ways to test the application:

### 1. GUI Test with Sample Data

When you run the MusicPlayer class, it will automatically populate the playlist with 10 sample songs for testing purposes.

```bash
# If using Gradle:
gradle run

# If using Java directly:
java -cp build/classes/java/main com.musicplayer.MusicPlayer
```

When the application starts, you'll see:
- The main music player window with a playlist containing 10 songs
- Console output indicating the application is ready for testing

#### Testing Sorting

1. Select a sorting criteria from the "Sort by" dropdown (Title, Artist, Album, Duration)
2. Check or uncheck "Ascending" to control sort direction
3. Click the "Sort" button
4. The playlist will reorganize based on your sort settings
5. Console output will confirm the sorting operation

**Expected Results:**
- Sorting by Title: Songs will be arranged alphabetically by title
- Sorting by Artist: Songs will be grouped by artist name
- Sorting by Album: Songs will be grouped by album name
- Sorting by Duration: Songs will be arranged from shortest to longest (or longest to shortest)

#### Testing Filtering

1. Enter a search term in the search field (e.g., "Beatles", "Thriller", "le")
2. Select a filter field from the dropdown (All, Title, Artist, Album)
3. Click "Search" or press Enter
4. The playlist will show only songs matching your search criteria
5. Click "Clear Filter" to return to the full playlist

**Expected Results:**
- Searching for "Beatles" with Artist filter: Should show "Yesterday" and "Let It Be"
- Searching for "Thriller" with Album filter: Should show "Thriller" and "Billie Jean"
- Searching for "le" with All filter: Should show multiple songs containing "le" in title, artist, or album

### 2. Console Test

The project includes a TestConsolePlayer class that runs several sorting and filtering operations and displays the results.

```bash
# If using Gradle:
gradle run --args="com.musicplayer.examples.TestConsolePlayer"

# If using Java directly:
java -cp build/classes/java/main com.musicplayer.examples.TestConsolePlayer
```

**Expected Output:**
- Initial playlist of 10 songs
- Playlist sorted by title (ascending)
- Playlist sorted by title (descending)
- Playlist sorted by artist (ascending)
- Playlist sorted by duration (descending)
- Results of filtering by artist "The Beatles" (should show 2 songs)
- Results of filtering by album "Thriller" (should show 2 songs)
- Results of searching for "le" across all fields (should show multiple songs)
- Results of filtering by duration between 3 and 5 minutes

## Expected Test Results

### Sorting Tests

1. **Sort by Title (Ascending):**
   - "Billie Jean" should be near the beginning
   - "Yesterday" should be near the end

2. **Sort by Title (Descending):**
   - "Yesterday" should be near the beginning
   - "Billie Jean" should be near the end

3. **Sort by Artist (Ascending):**
   - "Bob Dylan" should be near the beginning
   - "The Beatles" and "Queen" should be later in the list

4. **Sort by Duration (Descending):**
   - "Stairway to Heaven" (482 seconds) should be first
   - "Yesterday" (125 seconds) should be last

### Filtering Tests

1. **Filter by Artist "Beatles":**
   - Should show only "Yesterday" and "Let It Be"

2. **Filter by Album "Thriller":**
   - Should show only "Thriller" and "Billie Jean"

3. **Search for "le":**
   - Should include "Beatles" songs, "Hotel California", and possibly others

4. **Filter by Duration (180-300 seconds):**
   - Should include songs between 3:00 and 5:00 minutes
   - Should include "Imagine", "Let It Be", and "Billie Jean"

## Troubleshooting

If you encounter any issues during testing:

1. Check the console output for error messages
2. Verify that the Java version is compatible (Java 8+)
3. Make sure all source files are compiled and included in the classpath
4. If using Gradle, ensure Gradle is installed and configured correctly
