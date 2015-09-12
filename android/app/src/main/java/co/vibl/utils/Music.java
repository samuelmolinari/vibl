package co.vibl.utils;

import android.content.Intent;

/**
 * Created by Samuel on 12/09/15.
 */
public class Music {
    long duration;
    long playbackPosition;
    boolean playing;
    String artist;
    String album;
    String track;

    public Music(Intent intent) {
        duration = intent.getLongExtra("duration", 0);
        playbackPosition = intent.getIntExtra("playbackPosition", 0);
        playing = intent.getBooleanExtra("playing", false);
        artist = intent.getStringExtra("artist");
        album = intent.getStringExtra("album");
        track = intent.getStringExtra("track");
    }

    public long getDuration() {
        return duration;
    }

    public long getPlaybackPosition() {
        return playbackPosition;
    }

    public boolean isPlaying() {
        return playing;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getTrack() {
        return track;
    }

    public String toString() {
        return  "artist: "              + getArtist()           + " | " +
                "album: "               + getAlbum()            + " | " +
                "track: "               + getTrack()            + " | " +
                "duration: "            + getDuration()         + " | " +
                "playbackPosition: "    + getPlaybackPosition() + " | " +
                "playing: "             + isPlaying();
    }
}
