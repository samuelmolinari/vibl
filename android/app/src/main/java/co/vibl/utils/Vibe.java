package co.vibl.utils;

import android.util.Log;

/**
 * Created by Samuel on 12/09/15.
 */
public class Vibe {
    private Music music;
    private long duration;
    private long position;
    private byte[] footprint;
    private long activatedAt;

    public Vibe() {
        duration = 0;
        position = 0;
    }

    public byte[] getFootprint() {
        return footprint;
    }

    public void stamp(long position, byte value) {
        if(isReady()) {
            footprint[(int) position] = value;
        }
    }

    public void stamp(byte value) {
        stamp(getEstimatedPlaybackPosition(), value);
    }

    public boolean isReady() {
        return duration > 0 && activatedAt > 0 && getEstimatedPlaybackPosition() >= 0;
    }

    public float percentage() {
        if(duration > 0) {
            return getEstimatedPlaybackPosition() / (float) duration;
        }
        return 0;
    }

    public void setDuration(long d) {
        if(duration == 0) {
            duration = d;
            footprint = new byte[(int) duration];
        }
    }

    public void activate() {
        activatedAt = System.currentTimeMillis();
    }

    public long getEstimatedPlaybackPosition() {
        return Math.min(Math.max(0, position + (System.currentTimeMillis() - activatedAt)), Integer.MAX_VALUE);
    }

    public void setPlaybackPosition(long playbackPosition) {
        position = playbackPosition;
        activate();
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }
}
