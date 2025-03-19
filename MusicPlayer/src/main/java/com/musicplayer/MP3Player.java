package com.musicplayer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Specialized player for handling MP3 files.
 *
 * Note: Java's built-in sound API (javax.sound) doesn't natively support MP3 format.
 * For a full implementation, you would need to include a library like JAudioTagger or
 * JLayer for MP3 support. This implementation assumes the necessary codec is installed
 * or uses javax.sound's built-in capabilities for supported formats.
 */
public class MP3Player {
    private Clip audioClip;
    private AudioInputStream audioStream;
    private boolean isPlaying;
    private boolean isPaused;
    private long pausePosition;
    private final CountDownLatch playbackCompleted;
    private File currentFile;

    /**
     * Creates a new MP3 player instance.
     */
    public MP3Player() {
        isPlaying = false;
        isPaused = false;
        pausePosition = 0;
        playbackCompleted = new CountDownLatch(1);
    }

    /**
     * Loads an MP3 file for playback.
     *
     * @param file The MP3 file to load
     * @throws IOException If the file cannot be read
     * @throws UnsupportedAudioFileException If the file format is not supported
     * @throws LineUnavailableException If the audio line cannot be opened
     */
    public void load(File file) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        // Close any existing audio resources
        close();

        currentFile = file;
        audioStream = AudioSystem.getAudioInputStream(file);

        // Get clip and open with the audio stream
        audioClip = AudioSystem.getClip();
        audioClip.open(audioStream);

        // Add listener to handle when playback completes
        audioClip.addLineListener(event -> {
            if (event.getType() == LineEvent.Type.STOP) {
                if (!isPaused && audioClip.getMicrosecondPosition() >= audioClip.getMicrosecondLength()) {
                    // End of playback
                    isPlaying = false;
                    playbackCompleted.countDown();
                }
            }
        });
    }

    /**
     * Starts or resumes playback.
     */
    public void play() {
        if (audioClip == null) {
            return;
        }

        if (isPaused) {
            audioClip.setMicrosecondPosition(pausePosition);
            isPaused = false;
        }

        audioClip.start();
        isPlaying = true;
    }

    /**
     * Pauses playback.
     */
    public void pause() {
        if (audioClip != null && audioClip.isRunning()) {
            pausePosition = audioClip.getMicrosecondPosition();
            audioClip.stop();
            isPaused = true;
            isPlaying = false;
        }
    }

    /**
     * Stops playback and resets position to the beginning.
     */
    public void stop() {
        if (audioClip != null) {
            audioClip.stop();
            audioClip.setMicrosecondPosition(0);
            pausePosition = 0;
            isPaused = false;
            isPlaying = false;
        }
    }

    /**
     * Closes audio resources.
     */
    public void close() {
        if (audioClip != null) {
            audioClip.stop();
            audioClip.close();
            audioClip = null;
        }

        if (audioStream != null) {
            try {
                audioStream.close();
                audioStream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        isPlaying = false;
        isPaused = false;
        pausePosition = 0;
    }

    /**
     * Waits for playback to complete.
     *
     * @throws InterruptedException If the thread is interrupted
     */
    public void waitForPlaybackCompletion() throws InterruptedException {
        playbackCompleted.await();
    }

    /**
     * Sets the playback volume.
     *
     * @param volume Volume level (0.0 to 1.0)
     */
    public void setVolume(float volume) {
        if (audioClip == null) {
            return;
        }

        // Get the gain control from the clip
        FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);

        // Convert the volume (0.0 to 1.0) to decibels
        // The conversion formula for decibels is: 20 * log10(volume)
        // Min value is usually -80dB (silent), max is 6dB (very loud)
        float dB = 20f * (float) Math.log10(volume);

        // Ensure the gain is within the control's range
        float min = gainControl.getMinimum();
        float max = gainControl.getMaximum();

        if (dB < min) {
            dB = min;
        } else if (dB > max) {
            dB = max;
        }

        gainControl.setValue(dB);
    }

    /**
     * @return The current playback position in milliseconds
     */
    public long getCurrentPosition() {
        if (audioClip != null) {
            return audioClip.getMicrosecondPosition() / 1000;
        }
        return 0;
    }

    /**
     * @return The total length of the audio in milliseconds
     */
    public long getTotalLength() {
        if (audioClip != null) {
            return audioClip.getMicrosecondLength() / 1000;
        }
        return 0;
    }

    /**
     * Sets the playback position.
     *
     * @param position Position in milliseconds
     */
    public void setPosition(long position) {
        if (audioClip != null) {
            // Convert milliseconds to microseconds
            long microPos = position * 1000;

            // Ensure position is within bounds
            if (microPos < 0) {
                microPos = 0;
            } else if (microPos > audioClip.getMicrosecondLength()) {
                microPos = audioClip.getMicrosecondLength();
            }

            boolean wasPlaying = isPlaying && !isPaused;
            audioClip.stop();
            audioClip.setMicrosecondPosition(microPos);

            if (wasPlaying) {
                audioClip.start();
            }
        }
    }

    /**
     * @return true if audio is currently playing
     */
    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * @return true if audio is paused
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * @return The current audio file
     */
    public File getCurrentFile() {
        return currentFile;
    }
}
