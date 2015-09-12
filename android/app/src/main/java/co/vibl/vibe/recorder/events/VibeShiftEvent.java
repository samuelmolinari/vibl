package co.vibl.vibe.recorder.events;

import android.content.Context;
import android.media.RemoteController;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.os.RemoteException;
import android.service.media.MediaBrowserService;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import co.vibl.utils.MusicController;
import co.vibl.vibe.recorder.VibeActivity;

/**
 * Created by Samuel on 12/09/15.
 */
public class VibeShiftEvent extends AbstractVibeEvent implements View.OnTouchListener {
    public VibeShiftEvent(VibeActivity activity) {
        super(activity);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(getVibeActivity().isRecording()) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_MOVE:
                    vibeShift(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    getVibeActivity().pauseVibe(event);
                    break;
            }
        }
        return true;
    }

    private void vibeShift(float x, float y) {
        int height = getVibeActivity().getVibeSensorAreaView().getMeasuredHeight();
        byte value = (byte) (((height - y) / height) * 100);
        getVibeActivity().getVibe().stamp(value);
    }
}
