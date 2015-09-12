package co.vibl.vibe.recorder.states;

import android.util.Log;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import co.vibl.utils.Music;
import co.vibl.utils.MusicController;
import co.vibl.utils.StatefulActivity;
import co.vibl.vibe.recorder.events.DrawFootprintEvent;

/**
 * Created by Samuel on 11/09/15.
 */
public class RecordingState extends AbstractVibeState {
    Timer timer;

    @Override
    public void enter(StatefulActivity activity) {
        super.enter(activity);
        hideResumeButton();
        resumeMusic();
        timer = new Timer();
    }

    @Override
    public void exit() {
        super.exit();
        timer.cancel();
    }

    private void hideResumeButton() {
        getVibeActivity().getResumeVibeButton().setVisibility(View.INVISIBLE);
    }

    private void resumeMusic() {
        MusicController.getInstance().play(new MusicController.Callback() {
            @Override
            public void onConsume(Music music) {
                getVibeActivity().getVibe().setPlaybackPosition(music.getPlaybackPosition());
                getVibeActivity().getVibe().activate();

                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        getVibeActivity().runOnUiThread(new DrawFootprintEvent(getVibeActivity()));
                    }
                }, 0, 250);
            }

            @Override
            public boolean isConsumable(Music music) {
                return music.isPlaying();
            }
        });
    }
}
