package co.vibl.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Iterator;
import java.util.Queue;

/**
 * Created by Samuel on 12/09/15.
 */
public class MusicBroadcastReceiver extends BroadcastReceiver {
    private Queue<MusicController.Callback> callbacks;

    public MusicBroadcastReceiver(Queue<MusicController.Callback> callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(!callbacks.isEmpty()) {
            Music music = new Music(intent);
            Log.v("Music", music.toString());
            consumeCallbacks(music);
        }
    }

    private void consumeCallbacks(Music music) {
        int toConsume = callbacks.size();
        Iterator<MusicController.Callback> iterator = callbacks.iterator();

        while (toConsume > 0 && iterator.hasNext()) {
            consumeCallback(iterator.next(), music);
            toConsume--;
        }
    }

    private void consumeCallback(MusicController.Callback callback, Music music) {
        if(callback.isConsumable(music)) {
            callback.onConsume(music);
            callbacks.poll();
        }
    }
}
