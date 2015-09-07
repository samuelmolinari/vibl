package co.vibl;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.v4.view.KeyEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class VibeRecorderActivity extends AppCompatActivity {
    AudioManager.OnAudioFocusChangeListener listener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibe_recorder);

        restartSong();

        IntentFilter iF = new IntentFilter();
        iF.addAction("com.android.music.metachanged");
        iF.addAction("com.htc.music.metachanged");
        iF.addAction("fm.last.android.metachanged");
        iF.addAction("com.sec.android.app.music.metachanged");
        iF.addAction("com.nullsoft.winamp.metachanged");
        iF.addAction("com.amazon.mp3.metachanged");
        iF.addAction("com.miui.player.metachanged");
        iF.addAction("com.real.IMP.metachanged");
        iF.addAction("com.sonyericsson.music.metachanged");
        iF.addAction("com.rdio.android.metachanged");
        iF.addAction("com.samsung.sec.android.MusicPlayer.metachanged");
        iF.addAction("com.andrew.apollo.metachanged");
        iF.addAction("com.android.music.playstatechanged");
        iF.addAction("com.android.music.playbackcomplete");
        iF.addAction("com.android.music.queuechanged");
        iF.addAction("com.spotify.music.metadatachanged");
        iF.addAction("com.spotify.music.playbackstatechanged");
        iF.addAction("com.spotify.music.queuechanged");

        registerReceiver(mReceiver, iF);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pauseMusic();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                resumeMusic();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.v("y", String.valueOf(event.getY()));
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                pauseMusic();
                break;
        }
        return true;
    }

    public void restartSong() {
        Intent i = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
        KeyEvent downEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PREVIOUS);
        i.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
        i.setComponent(new ComponentName("com.spotify.music", "com.spotify.music.internal.receiver.MediaButtonReceiver"));
        getApplicationContext().sendBroadcast(i);
    }

    public void pauseMusic() {
        Intent i = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
        KeyEvent downEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PAUSE);
        i.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
        i.setComponent(new ComponentName("com.spotify.music", "com.spotify.music.internal.receiver.MediaButtonReceiver"));
        getApplicationContext().sendBroadcast(i);
        //((AudioManager)getSystemService(Context.AUDIO_SERVICE)).requestAudioFocus(listener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
    }

    public static List<ResolveInfo> getMediaReceivers(PackageManager packageManager) {
        Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);

        List<ResolveInfo> mediaReceivers = packageManager.queryBroadcastReceivers(mediaButtonIntent,
                PackageManager.GET_INTENT_FILTERS | PackageManager.GET_RESOLVED_FILTER);

        for (ResolveInfo info:
             mediaReceivers) {
            Log.v("packageName", info.activityInfo.packageName);
            Log.v("name", info.activityInfo.name);
        }

        return mediaReceivers;
    }

    public void resumeMusic() {
        Intent i = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
        KeyEvent downEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PLAY);
        i.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
        i.setComponent(new ComponentName("com.spotify.music", "com.spotify.music.internal.receiver.MediaButtonReceiver"));
        getApplicationContext().sendBroadcast(i);
        // Intent i = new Intent("com.spotify.mobile.android.ui.widget.PLAY");
        // getApplicationContext().sendBroadcast(i);
        // ((AudioManager)getSystemService(Context.AUDIO_SERVICE)).abandonAudioFocus(listener);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String cmd = intent.getStringExtra("command");
            Log.v("tag ", action + " / " + cmd);
            String artist = intent.getStringExtra("artist");
            String album = intent.getStringExtra("album");
            String track = intent.getStringExtra("track");
            if(intent.hasExtra("duration") && intent.hasExtra("playbackPosition")) {
                Log.v("ALL", intent.getExtras().toString());
                Long duration = intent.getLongExtra("duration", 0);
                Integer playbackPosition = intent.getIntExtra("playbackPosition", 0);
                Log.v("Percentage", Float.toString((playbackPosition / (float)duration) * 100));
            }
            Log.v("tag", artist + ":" + album + ":" + track);
            Log.v("ALL", intent.getExtras().toString());
        }
    };
}
