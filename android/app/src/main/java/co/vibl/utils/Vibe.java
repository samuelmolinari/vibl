package co.vibl.utils;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
            footprint[(int) position] = (byte) Math.min(Math.max(value, 0), 101);
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
            return Math.min(Math.max(getEstimatedPlaybackPosition() / (float) duration, 0), 100);
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

    public static int computeValueColor(byte value) {
        float percentage = value / 100f;
        int red = (int) Math.floor(29 + (226 * percentage));
        int green = (int) Math.floor(percentage < 0.65 ? (26 + 6 * (percentage / 0.65f)) : (32 + 180 * ((percentage - 0.65f) / 0.35f)));
        int blue = (int) Math.floor(percentage < 0.4 ? (33 + 43 * (percentage / 0.4f)) : (76 - (76 * ((percentage - 0.4f) / 0.6f))));

        return Color.argb(255, red, green, blue);
    }

    public static GradientDrawable computeValueGradient(byte value) {
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[] {computeValueColor((byte) Math.min(value + 20, 100)) ,computeValueColor((byte) Math.min(Math.max(value, 0), 100))});

        return gd;
    }
}
