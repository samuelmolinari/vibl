package co.vibl.vibe.recorder.states;

import android.util.Log;
import android.view.View;

import co.vibl.utils.Music;
import co.vibl.utils.MusicController;
import co.vibl.utils.StatefulActivity;

/**
 * Created by Samuel on 11/09/15.
 */
public class RecordingState extends AbstractVibeState {
    @Override
    public void enter(StatefulActivity activity) {
        super.enter(activity);
        hideResumeButton();
        resumeMusic();
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
            }

            @Override
            public boolean isConsumable(Music music) {
                return music.isPlaying();
            }
        });
    }
}
