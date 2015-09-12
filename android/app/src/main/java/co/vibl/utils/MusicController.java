package co.vibl.utils;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.telecom.Call;
import android.util.Log;
import android.view.KeyEvent;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import co.vibl.ViblApplication;

/**
 * Created by Samuel on 12/09/15.
 */
public class MusicController {
    private static MusicController instance = new MusicController();
    public static MusicController getInstance() {
        return instance;
    }

    private BroadcastReceiver broadcastReceiver;
    private Queue<Callback> callbacks;

    private MusicController() {
        callbacks = new LinkedList();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(!callbacks.isEmpty()) {
                    Music music = new Music(intent);
                    int toConsume = callbacks.size();
                    Iterator<Callback> iterator = callbacks.iterator();

                    while (toConsume > 0 && iterator.hasNext()) {
                        Callback callback = iterator.next();
                        if(callback.isConsumable(music)) {
                            callback.onConsume(music);
                            callbacks.poll();
                        }
                        toConsume--;
                    }
                }
            }
        };

        IntentFilter iF = new IntentFilter();
//        iF.addAction("com.android.music.metachanged");
//        iF.addAction("com.htc.music.metachanged");
//        iF.addAction("fm.last.android.metachanged");
//        iF.addAction("com.sec.android.app.music.metachanged");
//        iF.addAction("com.nullsoft.winamp.metachanged");
//        iF.addAction("com.amazon.mp3.metachanged");
//        iF.addAction("com.miui.player.metachanged");
//        iF.addAction("com.real.IMP.metachanged");
//        iF.addAction("com.sonyericsson.music.metachanged");
//        iF.addAction("com.rdio.android.metachanged");
//        iF.addAction("com.samsung.sec.android.MusicPlayer.metachanged");
//        iF.addAction("com.andrew.apollo.metachanged");
//        iF.addAction("com.android.music.playstatechanged");
//        iF.addAction("com.android.music.playbackcomplete");
//        iF.addAction("com.android.music.queuechanged");
        iF.addAction("com.spotify.music.metadatachanged");
        iF.addAction("com.spotify.music.playbackstatechanged");
        iF.addAction("com.spotify.music.queuechanged");

        ViblApplication.getInstance().registerReceiver(broadcastReceiver, iF);
    }

    public void rewind(Callback callback) {
        mediaButton(KeyEvent.KEYCODE_MEDIA_PREVIOUS);
        if(callback != null) callbacks.add(callback);
    }

    public void pause(Callback callback) {
        mediaButton(KeyEvent.KEYCODE_MEDIA_PAUSE);
        if(callback != null) callbacks.add(callback);
    }

    public void play(Callback callback) {
        mediaButton(KeyEvent.KEYCODE_MEDIA_PLAY);
        if(callback != null) callbacks.add(callback);
    }

    private void mediaButton(int keycode) {
        Intent i = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
        KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP, keycode);
        i.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);

        i.setComponent(getCurrentComponent());

        ViblApplication.getInstance().getApplicationContext().sendBroadcast(i);
    }

    public ComponentName getCurrentComponent() {
        return new ComponentName("com.spotify.music", "com.spotify.music.internal.receiver.MediaButtonReceiver");
    }

//    public static List<ResolveInfo> getMediaReceivers(PackageManager packageManager) {
//        Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
//
//        List<ResolveInfo> mediaReceivers = packageManager.queryBroadcastReceivers(mediaButtonIntent,
//                PackageManager.GET_INTENT_FILTERS | PackageManager.GET_RESOLVED_FILTER);
//
//        for (ResolveInfo info:
//                mediaReceivers) {
//            Log.v("packageName", info.activityInfo.packageName);
//            Log.v("name", info.activityInfo.name);
//        }
//
//        return mediaReceivers;
//    }

    public interface Callback {
        public void onConsume(Music music);
        public boolean isConsumable(Music music);
    }
}
