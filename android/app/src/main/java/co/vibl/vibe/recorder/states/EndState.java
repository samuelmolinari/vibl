package co.vibl.vibe.recorder.states;

import android.view.View;

import co.vibl.utils.MusicController;
import co.vibl.utils.StatefulActivity;

/**
 * Created by Samuel on 12/09/15.
 */
public class EndState extends AbstractVibeState {
    @Override
    public void enter(StatefulActivity activity) {
        super.enter(activity);
        pauseMusic();
        hideProgressBar();
    }

    private void pauseMusic() {
        MusicController.getInstance().pause(null);
    }

    private void hideProgressBar() {
        getVibeActivity().getTrackPositionView().setVisibility(View.INVISIBLE);
    }
}
