package co.vibl.vibe.recorder.states;

import android.util.Log;
import android.view.View;

import co.vibl.utils.Music;
import co.vibl.utils.MusicController;
import co.vibl.utils.State;
import co.vibl.utils.StatefulActivity;

/**
 * Created by Samuel on 11/09/15.
 */
public class StartState extends AbstractVibeState {
    public void enter(StatefulActivity activity) {
        super.enter(activity);
        rewindMusic();
        hideStartButton();
    }

    private void hideStartButton() {
        getVibeActivity().getStartVibeButton().setVisibility(View.INVISIBLE);
    }

    private void rewindMusic() {
        MusicController.getInstance().pause(null);
        MusicController.getInstance().rewind(new MusicController.Callback() {
            @Override
            public void onConsume(Music music) {
                getVibeActivity().getVibe().setDuration(music.getDuration());
                getVibeActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        transitToRecordingState();
                    }
                });
            }

            @Override
            public boolean isConsumable(Music music) {
                return music.getPlaybackPosition() == 0;
            }
        });
    }

    private void transitToRecordingState() {
        recordingState().enter(getVibeActivity());
    }

    private State recordingState() {
        return getVibeActivity().getRecordingState();
    }
}
